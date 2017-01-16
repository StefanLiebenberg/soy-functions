package org.slieb.soy.plugins.soyfunctions;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.FloatData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;

class ParseFloatSoyFunction implements SoyFunction, SoyJsSrcFunction, SoyJavaFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        JsExpr expr = args.get(0);
        String text = expr.getText();
        return new JsExpr("parseFloat(" + text + ")", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        SoyValue argument = args.get(0);
        String stringValue = argument.coerceToString();
        float value = Float.parseFloat(stringValue);
        return FloatData.forValue(value);
    }

    @Override
    public String getName() {
        return "parseFloat";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return singleton(1);
    }
}
