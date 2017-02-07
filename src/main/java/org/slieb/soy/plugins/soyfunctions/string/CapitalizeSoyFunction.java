package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.Collections;
import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;

@SoyPureFunction
public class CapitalizeSoyFunction extends AbstractSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    public CapitalizeSoyFunction() {
        super("capitalize", Collections.singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String expression = "goog.string.capitalize(" + list.get(0).getText() + ')';
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(expression, MAX_VALUE));
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        final String stringValue = list.get(0).stringValue();
        return StringData.forValue(stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1).toLowerCase());
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
        return ImmutableSet.of("goog.string");
    }
}
