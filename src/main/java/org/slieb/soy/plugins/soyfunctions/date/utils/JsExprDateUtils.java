package org.slieb.soy.plugins.soyfunctions.date.utils;

import com.google.template.soy.jssrc.restricted.JsExpr;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.*;

@SuppressWarnings("WeakerAccess")
public class JsExprDateUtils {

    public static Set<String> DATE_LIBS = Stream.of("goog.date.Date", "goog.date.DateTime", "goog.date", "goog.asserts").collect(toSet());

    public static JsExpr toDate(JsExpr value) {
        return newCall("Date", value);
    }

    public static JsExpr toDateTime(JsExpr value) {
        return newCall("goog.date.DateTime", toDate(value));
    }

    public static JsExpr compareDatesExpr(JsExpr dateA, JsExpr dateB) {
        return callFunction("goog.date.Date.compare", dateA, dateB);
    }

    public static JsExpr isDateLike(JsExpr value) {
        return callFunction("goog.isDateLike", value);
    }

    public static JsExpr assertDateLike(final JsExpr current, final String message) {
        return callFunction("goog.asserts.assert", isDateLike(current), asLiteral(message));
    }
}
