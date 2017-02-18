package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.asNumber;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callOn;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class ToFixedSoyFunction extends AbstractSoyPureFunction {

    private static final String TO_FIXED = "Number(%s).toFixed(%s)";

    public ToFixedSoyFunction() {
        super("toFixed", singleton(2));
    }

    private static String toFixed(final String n, final int digits) {
        return new BigDecimal(n).setScale(digits, BigDecimal.ROUND_HALF_UP).toString();
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return callOn(asNumber(list.get(0)), "toFixed", list.get(1));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(toFixed(list.get(0).coerceToString(), list.get(1).integerValue()));
    }
}
