package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.Collections;
import java.util.List;

@SoyPureFunction
public class CapitalizeSoyFunction extends AbstractSoyFunction {

    public CapitalizeSoyFunction() {
        super("capitalize", Collections.singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return new JsExpr(list.get(0).getText() + ".replace(/^[a-z]/, function(c) { return c.toUpperCase() })", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        final String stringValue = list.get(0).stringValue();
        return StringData.forValue(stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1));
    }
}
