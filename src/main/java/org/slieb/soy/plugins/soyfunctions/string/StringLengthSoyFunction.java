package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class StringLengthSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction {

    public StringLengthSoyFunction() {
        super("strLength", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr stringExpr = args.get(0);
        return new JsExpr("(" + stringExpr.getText() + ").length", Integer.MAX_VALUE);
    }

    @Override
    public IntegerData computeForJava(final List<SoyValue> args) {
        final SoyValue stringArg = args.get(0);
        final String stringValue = stringArg.coerceToString();
        return IntegerData.forValue(stringValue.length());
    }
}
