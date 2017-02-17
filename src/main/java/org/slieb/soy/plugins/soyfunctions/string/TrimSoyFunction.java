package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

@SoyPureFunction
public class TrimSoyFunction extends AbstractSoyPureFunction implements SoyLibraryAssistedJsSrcFunction {

    private static final ImmutableSet<String> REQUIRED_LIBS = ImmutableSet.of("goog.string");

    private static final String TRIM_STRING = "goog.string.trim(%s)";

    public TrimSoyFunction() {
        super("trim", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr jsExpr = list.get(0);
        final String expr = String.format(TRIM_STRING, jsExpr.getText());
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(expr, MAX_VALUE));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().trim());
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return REQUIRED_LIBS;
    }
}
