package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule;
import org.slieb.soy.plugins.soyfunctions.date.models.InstantSoyValue;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSanitizedSoyFunction;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.data.SanitizedContents.unsanitizedText;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;

public class PrintDateSoyFunction extends AbstractSanitizedSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    private final Provider<ZoneOffset> defaultZoneProvider;

    private static final String FORMAT_DATETIME = "new goog.i18n.DateTimeFormat(%s).format(%s, %s)";

    private static final String FORMAT_TIMEZONE = "goog.i18n.TimeZone.createTimeZone(%s)";

    private static final String DEFAULT_PATTERN = "yyyy-dd-mm hh:mm:ss";

    private static final Integer DEFAULT_OFFSET = 0;

    private static final ImmutableSet<String> REQUIRED_LIBS = ImmutableSet.of("goog.i18n.DateTimeFormat", "goog.date", "goog.date.DateTime");

    private static final String FUNCTION_INVOKE = "(function(time){ return goog.isDateLike(time) ? time : goog.isString(time) ? goog.date.fromIsoString(time)" +
            " || goog.date.DateTime.fromRfc822String(time) || goog.date.DateTime.fromTimestamp(parseInt(time, 10)) : new goog.date.DateTime" +
            "(new Date(time)); })(%s)";

    @Inject
    public PrintDateSoyFunction(@Named(SoyDateFunctionsModule.DEFAULT_OFFSET) final Provider<ZoneOffset> defaultZoneProvider) {
        super("printDate", newHashSet(1, 2, 3));
        this.defaultZoneProvider = defaultZoneProvider;
    }

    private <T> T getDateArg(List<T> args) {
        return getOptional(args, 0).orElseThrow(() -> new RuntimeException("No date argument passed in."));
    }

    private <T> T getPatternArg(List<T> args, T defaultValue) {
        return getOptional(args, 1).orElse(defaultValue);
    }

    private <T> T getOffsetArg(List<T> args, T defaultValue) {
        return getOptional(args, 2).orElse(defaultValue);
    }

    private Instant getInstant(final SoyValue dateValue) {
        if (dateValue instanceof InstantSoyValue) {
            return ((InstantSoyValue) dateValue).getInstant();
        }
        return Instant.ofEpochMilli(dateValue.longValue());
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String date = String.format(FUNCTION_INVOKE, getDateArg(list).getText());
        final String pattern = getPatternArg(list, new JsExpr("\"" + DEFAULT_PATTERN + "\"", MAX_VALUE)).getText();
        final String zoneOffsetJs = getOffsetArg(list, new JsExpr(DEFAULT_OFFSET.toString(), Integer.MAX_VALUE)).getText();
        final String zoneOffset = String.format(FORMAT_TIMEZONE, zoneOffsetJs);
        final String text = String.format(FORMAT_DATETIME, pattern, date, zoneOffset);
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(text, MAX_VALUE));
    }

    @Override
    public SanitizedContent computeForJava(final List<SoyValue> list) {
        final Instant instant = getInstant(getDateArg(list));
        final String pattern = getPatternArg(list, StringData.forValue(DEFAULT_PATTERN)).coerceToString();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(getOffsetArg(list, IntegerData.forValue(DEFAULT_OFFSET)).integerValue());
        return unsanitizedText(dateTimeFormatter.format(instant.atOffset(zoneOffset)));
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return REQUIRED_LIBS;
    }
}


