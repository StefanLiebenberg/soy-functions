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
public class ToLowerCaseSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction {

    public ToLowerCaseSoyFunction() {
        super("toLowerCase", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        final JsExpr lowerCaseExpr = new JsExpr(jsExpr.getText() + ".toLowerCase()", MAX_VALUE);
        return maybeWrapAsSanitizedContent(TEXT, lowerCaseExpr);
    }

    @Override
    public StringData computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        return StringData.forValue(soyValue.stringValue().toLowerCase());
    }
}
