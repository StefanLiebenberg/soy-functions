package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

@SoyPureFunction
public class CapitalizeSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction
        implements SoyLibraryAssistedJsSrcFunction {

    private static final String CAPITALIZE = "goog.string.capitalize(%s)";

    public CapitalizeSoyFunction() {
        super("capitalize", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String expression = String.format(CAPITALIZE, list.get(0).getText());
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(expression, MAX_VALUE));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        final String stringValue = list.get(0).stringValue();
        return StringData.forValue(stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1).toLowerCase());
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.string");
    }
}
