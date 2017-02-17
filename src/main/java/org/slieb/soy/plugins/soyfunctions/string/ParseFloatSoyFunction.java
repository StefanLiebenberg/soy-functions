package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.FloatData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class ParseFloatSoyFunction extends AbstractSoyPureFunction {

    public ParseFloatSoyFunction() {
        super("parseFloat", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr expr = args.get(0);
        final String text = expr.getText();
        return new JsExpr("parseFloat(" + text + ")", Integer.MAX_VALUE);
    }

    @Override
    public FloatData computeForJava(final List<SoyValue> args) {
        final SoyValue argument = args.get(0);
        final String stringValue = argument.coerceToString();
        float value = Float.parseFloat(stringValue);
        return FloatData.forValue(value);
    }
}
