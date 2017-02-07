package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableSet;

@SoyPureFunction
public class ParseIntegerSoyFunction extends AbstractSoyFunction {

    public ParseIntegerSoyFunction() {
        super("parseInt", unmodifiableSet(newHashSet(1, 2)));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        JsExpr expr = args.get(0);
        if (args.size() >= 2) {
            JsExpr radix = args.get(1);
            return new JsExpr("parseInt(" + expr.getText() + ", " + radix.getText() + ")", Integer.MAX_VALUE);
        } else {
            return new JsExpr("parseInt(" + expr.getText() + ", 10)", Integer.MAX_VALUE);
        }
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        SoyValue argument = args.get(0);
        String stringValue = argument.coerceToString();
        if (args.size() >= 2) {
            SoyValue radix = args.get(1);
            int radix1 = radix.integerValue();
            return IntegerData.forValue(Integer.parseInt(stringValue, radix1));
        } else {
            return IntegerData.forValue(Integer.parseInt(stringValue));
        }
    }
}
