package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ToFixedSoyFunction extends AbstractSoyFunction {

    public ToFixedSoyFunction() {
        super("toFixed", Collections.singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return new JsExpr("Number(" + list.get(0).getText() + ").toFixed(" + list.get(1).getText() + ")", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return StringData.forValue(toFixed(list.get(0).coerceToString(), list.get(1).integerValue()));
    }

    private static String toFixed(String n, int digits) {
        BigDecimal bigDecimal = new BigDecimal(n);
        return bigDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).toString();
    }
}
