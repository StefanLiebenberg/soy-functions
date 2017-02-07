package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.UndefinedData;
import com.google.template.soy.internal.targetexpr.TargetExpr;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.models.SoyDateTime;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;

public class PrintDateSoyFunction extends AbstractSoyFunction.PlaceHolderSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    public static final String FORMAT_DATETIME = "new goog.i18n.DateTimeFormat(%s).format(%s, %s)";

    public PrintDateSoyFunction() {
        super("printDate", newHashSet(1, 2, 3));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String pattern = getPattern(list);
        final String date = getDate(list);
        final String zoneOffsetJs = getZoneOffsetJs(list);
        final String text = String.format(FORMAT_DATETIME, pattern, date, zoneOffsetJs);
        final JsExpr expr = new JsExpr(text, MAX_VALUE);
        return maybeWrapAsSanitizedContent(TEXT, expr);
    }

    private String getDate(final List<JsExpr> list) {
        return String.format("new Date(%s)", list.get(0).getText());
    }

    private String getZoneOffsetJs(final List<JsExpr> list) {
        return getOptional(list, 3).map(TargetExpr::getText).orElse("0");
    }

    private String getPattern(final List<JsExpr> list) {
        return getOptional(list, 2).map(TargetExpr::getText).orElse("goog.i18n.DateTimeFormat.Format.FULL_DATE");
    }

    private <T> Optional<T> getOptional(List<T> t, int argNr) {
        if (t.size() >= argNr) {
            return Optional.of(t.get(argNr - 1));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        final Instant instant = getInstant(list);
        final ZoneOffset zoneOffset = getZoneOffset(list);
        final DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(list);
        final String formatted = dateTimeFormatter.format(instant.atOffset(zoneOffset));
        return SanitizedContents.unsanitizedText(formatted);
    }

    private Instant getInstant(final List<SoyValue> list) {
        final SoyValue dateValue = list.get(0);
        if (dateValue instanceof SoyDateTime) {
            return ((SoyDateTime) dateValue).getInstant();
        }
        return Instant.ofEpochMilli(dateValue.longValue());
    }

    private DateTimeFormatter getDateTimeFormatter(final List<SoyValue> list) {
        if (list.size() >= 2) {
            final SoyValue soyValue = list.get(1);
            if (soyValue != null && !(soyValue instanceof UndefinedData)) {
                final String pattern = soyValue.coerceToString();
                return DateTimeFormatter.ofPattern(pattern);
            }
        }
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    private ZoneOffset getZoneOffset(final List<SoyValue> list) {
        if (list.size() >= 3) {
            return ZoneOffset.ofHours(list.get(2).integerValue());
        }
        return ZoneOffset.UTC;
    }

    /**
     * Returns a list of Closure library names to require when this function is used.
     * <p>
     * <p> Note: Return the raw Closure library names, Soy will wrap them in goog.require for you.
     *
     * @return A collection of strings representing Closure JS library names
     */
    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.i18n.DateTimeFormat");
    }
}


