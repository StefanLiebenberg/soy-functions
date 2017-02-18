package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.data.restricted.NumberData;
import com.google.template.soy.data.restricted.SoyString;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.date.models.SoyDate;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.time.Instant;
import java.util.Set;

import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.DATE_LIBS;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.isDateLike;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.*;

abstract class AbstractSoyDateFunction<T extends SoyValue> extends AbstractSoyFunction<T> implements SoyLibraryAssistedJsSrcFunction {

    AbstractSoyDateFunction(final String name, final Set<Integer> argSizes) {
        super(name, argSizes);
    }

    String badArgumentMessage() {return getName() + "() called with bad args";}

    JsExpr getInstantExpr(JsExpr expr, String message) {
        final JsExpr otherwiseTryNumber = ternaryIf(isNumber(expr), expr, throwError(asLiteral(message)));
        final JsExpr withNumber = ternaryIf(isString(expr), parseInt(expr), otherwiseTryNumber);
        return ternaryIf(isDateLike(expr), expr, newCall("Date", withNumber));
    }

    Instant getInstant(SoyValue soyValue, String message) {

        if (soyValue instanceof SoyDate) {
            return SoyDate.class.cast(soyValue).getInstant();
        }

        if (soyValue instanceof SoyString) {
            return ofEpochMilli(parseLong(soyValue.stringValue()));
        }

        if (soyValue instanceof IntegerData) {
            return ofEpochMilli(soyValue.longValue());
        }

        if (soyValue instanceof NumberData) {
            return ofEpochMilli(Double.valueOf(soyValue.numberValue()).longValue());
        }

        throw new IllegalArgumentException(message);
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.copyOf(DATE_LIBS);
    }
}
