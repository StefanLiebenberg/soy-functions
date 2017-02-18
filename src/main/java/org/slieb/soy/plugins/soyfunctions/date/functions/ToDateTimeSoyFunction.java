package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.NumberData;
import com.google.template.soy.data.restricted.SoyString;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.date.models.DateTimeSoyValue;
import org.slieb.soy.plugins.soyfunctions.date.models.SoyDate;
import org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;

public class ToDateTimeSoyFunction extends AbstractSoyFunction<DateTimeSoyValue> implements SoyLibraryAssistedJsSrcFunction {

    private static final String JS_DATETIME_CONVERTER_STRING = buildJsDateTimeText();

    public ToDateTimeSoyFunction() {
        super("toDateTime", newHashSet(1, 2));
    }

    private static String buildJsDateTimeText() {
        //noinspection StringBufferReplaceableByString
        final StringBuilder code = new StringBuilder();
        code.append("(function(time){");
        code.append("if(goog.isDateLike(time)) { return time; }");
        code.append("if(goog.isString(time)) {return ")
                .append("goog.date.fromIsoString(time)").append(" || ")
                .append("goog.date.DateTime.fromRfc822String(time)").append(" || ")
                .append("goog.date.DateTime.fromTimestamp(parseInt(time, 10))")
                .append("; }");
        code.append("return goog.date.DateTime(new Date(time));");
        code.append("})(%s)");
        return code.toString();
    }

    @Override
    @Nonnull
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr instantExpr = new JsExpr(String.format(JS_DATETIME_CONVERTER_STRING, list.get(0).getText()), Integer.MAX_VALUE);
        if (list.size() > 1) {
            final JsExpr adjustedExpr = getAdjustedExpression(instantExpr.getText(), list.get(1).getText());
        }
        return instantExpr;
    }

    @Override
    public DateTimeSoyValue computeForJava(final List<SoyValue> list) {
        final SoyValue soyValue = list.get(0);
        if (soyValue instanceof DateTimeSoyValue) {
            return (DateTimeSoyValue) soyValue;
        }
        return new DateTimeSoyValue(toOffsetDateTime(soyValue, ZoneOffset.UTC));
    }

    private JsExpr getAdjustedExpression(final String instantExprText, final String text) {
        return new JsExpr(instantExprText, Integer.MAX_VALUE);
    }

    private OffsetDateTime toOffsetDateTime(final SoyValue soyValue, ZoneOffset zoneOffset) {
        if (soyValue instanceof SoyDate) {
            return toOffsetDateTime(((SoyDate) soyValue).getInstant(), zoneOffset);
        }

        if (soyValue instanceof SoyString) {
            return OffsetDateTime.parse(soyValue.stringValue());
        }

        if (soyValue instanceof NumberData) {
            return toOffsetDateTime(Instant.ofEpochMilli(soyValue.longValue()), zoneOffset);
        }

        throw new RuntimeException();
    }

    private OffsetDateTime toOffsetDateTime(Instant instant, ZoneOffset zoneOffset) {
        return OffsetDateTime.ofInstant(instant, zoneOffset);
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.copyOf(JsExprDateUtils.DATE_LIBS);
    }
}
