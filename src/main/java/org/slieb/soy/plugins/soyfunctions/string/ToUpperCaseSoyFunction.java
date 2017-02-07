package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

@SoyPureFunction
public class ToUpperCaseSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction {

    public ToUpperCaseSoyFunction() {
        super("toUpperCase", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr strExpr = args.get(0);
        final JsExpr upperCaseExpr = new JsExpr(strExpr.getText() + ".toUpperCase()", MAX_VALUE);
        return maybeWrapAsSanitizedContent(TEXT, upperCaseExpr);
    }

    @Override
    public StringData computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        final String s = soyValue.stringValue();
        return StringData.forValue(s.toUpperCase());
    }
}
