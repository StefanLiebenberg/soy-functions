package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.JsExprUtils;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class TrimSoyFunction extends AbstractSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    public TrimSoyFunction() {
        super("trim", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String expr = "goog.string.trim(" + list.get(0).getText() + ")";
        return JsExprUtils.maybeWrapAsSanitizedContent(SanitizedContent.ContentKind.TEXT, new JsExpr(expr, Integer.MAX_VALUE));
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().trim());
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
