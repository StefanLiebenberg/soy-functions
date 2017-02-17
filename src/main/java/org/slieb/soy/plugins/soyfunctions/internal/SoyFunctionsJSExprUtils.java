package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.internal.targetexpr.TargetExpr;
import com.google.template.soy.jssrc.restricted.JsExpr;

import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.Collectors.joining;

@SuppressWarnings("WeakerAccess")
public class SoyFunctionsJSExprUtils {

    public static JsExpr jsExpr(final Object expression) {
        return new JsExpr(String.valueOf(expression), MAX_VALUE);
    }

    public static JsExpr wrap(JsExpr jsExpr) {
        return jsExpr(String.format("(%s)", jsExpr.getText()));
    }

    public static JsExpr join(String separator, JsExpr... arguments) {
        return jsExpr(Arrays.stream(arguments)
                              .map(TargetExpr::getText)
                              .collect(joining(separator)));
    }

    public static JsExpr inlineStatements(JsExpr... statements) {
        return wrap(join(", ", statements));
    }

    private static boolean DEBUG = false;

    public static JsExpr debugLog(String prefix, JsExpr expression) {
        if (DEBUG) {
            return inlineStatements(callFunction("console.log", stringLiteral(prefix), expression), expression);
        } else {
            return expression;
        }
    }

    public static JsExpr and(JsExpr... arguments) {
        return join("&&", arguments);
    }

    public static JsExpr or(JsExpr... arguments) {
        return join("||", arguments);
    }

    public static JsExpr callFunction(String functionName, JsExpr... objects) {
        return jsExpr(functionName + wrap(join(", ", objects)).getText());
    }

    public static JsExpr callOn(JsExpr object, String functionName, JsExpr... objects) {
        return jsExpr(wrap(object).getText() + "." + callFunction(functionName, objects).getText());
    }

    public static JsExpr newCall(final String functionName, final JsExpr... objects) {
        return jsExpr("new " + callFunction(functionName, objects).getText());
    }

    public static JsExpr ternaryIf(final JsExpr condition, final JsExpr ifTrue, final JsExpr ifFalse) {
        return jsExpr(String.format("%s ? %s : %s", condition.getText(), ifTrue.getText(), ifFalse.getText()));
    }

    public static JsExpr parseInt(JsExpr expression, JsExpr radius) {
        return callFunction("parseInt", expression, radius);
    }

    public static JsExpr parseInt(JsExpr expr) {
        return parseInt(expr, jsExpr("10"));
    }

    public static JsExpr isString(final JsExpr expr) {
        return callFunction("goog.isString", expr);
    }

    public static JsExpr isNumber(final JsExpr expr) {
        return callFunction("goog.isNumber", expr);
    }

    public static JsExpr throwError(final JsExpr message) {
        return callFunction("goog.asserts.fail", message);
    }

    public static JsExpr stringLiteral(final String value) {
        return jsExpr(String.format("\"%s\"", value));
    }
}
