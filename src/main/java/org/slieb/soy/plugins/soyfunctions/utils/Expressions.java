package org.slieb.soy.plugins.soyfunctions.utils;

import com.google.template.soy.internal.targetexpr.TargetExpr;
import com.google.template.soy.jssrc.restricted.JsExpr;

import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.Collectors.joining;

@SuppressWarnings("WeakerAccess")
public class Expressions {

    /**
     * Turn some expressing into a {@link JsExpr} object.
     *
     * @param expression The expression object. This is turned into a string and passed to {@link JsExpr}.
     * @return A new {@link JsExpr}
     */
    public static JsExpr expression(final Object expression) {
        return new JsExpr(String.valueOf(expression), MAX_VALUE);
    }

    /**
     * @param jsExpr Some expression.
     * @return The original jsExpr object wrapped in parenthesis "(jsExpr)";
     */
    public static JsExpr wrap(JsExpr jsExpr) {
        return expression(String.format("(%s)", jsExpr.getText()));
    }

    public static JsExpr asLiteral(final String value) {
        return expression(String.format("\"%s\"", value));
    }
    
    /**
     * Join an array of {@link JsExpr} together with a separator.
     *
     * @param separator The separator to use when joining.
     * @param arguments The arguments to join.
     * @return A expression where the arguments have been joined together with the given separator.
     */
    public static JsExpr join(String separator, JsExpr... arguments) {
        return expression(Arrays.stream(arguments)
                                  .map(TargetExpr::getText)
                                  .collect(joining(separator)));
    }

    /**
     * @param statements An collection of statements.
     * @return A list of the given statements, separated by a comma and wrapped in parenthesis.
     */
    public static JsExpr parenthesizedList(JsExpr... statements) {
        return wrap(join(", ", statements));
    }

    public static JsExpr and(JsExpr... arguments) {
        return join("&&", arguments);
    }

    public static JsExpr or(JsExpr... arguments) {
        return join("||", arguments);
    }

    public static JsExpr callFunction(String functionName, JsExpr... objects) {
        return expression(functionName + wrap(join(", ", objects)).getText());
    }

    public static JsExpr callOn(JsExpr object, String functionName, JsExpr... objects) {
        return expression(wrap(object).getText() + "." + callFunction(functionName, objects).getText());
    }

    public static JsExpr newCall(final String functionName, final JsExpr... objects) {
        return expression("new " + callFunction(functionName, objects).getText());
    }

    public static JsExpr ternaryIf(final JsExpr condition, final JsExpr ifTrue, final JsExpr ifFalse) {
        return expression(String.format("%s ? %s : %s", condition.getText(), ifTrue.getText(), ifFalse.getText()));
    }

    public static JsExpr parseInt(JsExpr expression, JsExpr radius) {
        return callFunction("parseInt", expression, radius);
    }

    public static JsExpr parseInt(JsExpr expr) {
        return parseInt(expr, expression("10"));
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

    /**
     * Wraps value in {@code String(value)} call.
     *
     * @param value Some expression to wrap as string.
     * @return A {@code String(...)} wrapped expression.
     */
    public static JsExpr asString(final JsExpr value) {
        return callFunction("String", value);
    }
}
