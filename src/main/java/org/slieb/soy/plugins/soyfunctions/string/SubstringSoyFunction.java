package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.Collections;
import java.util.List;

@SoyPureFunction
public class SubstringSoyFunction extends AbstractSoyFunction {

    public SubstringSoyFunction() {
        super("substring", Collections.singleton(3));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return new JsExpr(list.get(0).getText() + ".substring(" + list.get(1).getText() + ", " + list.get(2).getText() + ")", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().substring(list.get(1).integerValue(), list.get(2).integerValue()));
    }
}
