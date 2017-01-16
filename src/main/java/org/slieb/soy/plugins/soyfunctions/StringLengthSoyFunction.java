package org.slieb.soy.plugins.soyfunctions;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;

public class StringLengthSoyFunction implements SoyFunction, SoyJsSrcFunction, SoyJavaFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr stringExpr = args.get(0);
        return new JsExpr("(" + stringExpr.getText() + ").length", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        final SoyValue stringArg = args.get(0);
        final String stringValue = stringArg.coerceToString();
        return IntegerData.forValue(stringValue.length());
    }

    @Override
    public String getName() {
        return "strLength";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return singleton(1);
    }
}
