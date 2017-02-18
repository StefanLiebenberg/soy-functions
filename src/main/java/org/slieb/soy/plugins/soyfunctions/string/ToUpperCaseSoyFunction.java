package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.utils.Expressions;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callOn;

@SoyPureFunction
public class ToUpperCaseSoyFunction extends AbstractSoyPureFunction {

    private static final int VALUE_ARG = 0;

    public ToUpperCaseSoyFunction() {
        super("toUpperCase", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr value = args.get(VALUE_ARG);
        final JsExpr toString = Expressions.asString(value);
        final JsExpr toUpperCase = callOn(value, "toUpperCase");
        return maybeWrapAsSanitizedContent(TEXT, toUpperCase);
    }

    @Override
    public StringData computeForJava(final List<SoyValue> args) {
        final SoyValue value = args.get(VALUE_ARG);
        final String s = value.stringValue();
        return StringData.forValue(s.toUpperCase());
    }
}
