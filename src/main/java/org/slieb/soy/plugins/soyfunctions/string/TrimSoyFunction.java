package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class TrimSoyFunction extends AbstractSoyFunction {

    public TrimSoyFunction() {
        super("trim", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return new JsExpr(list.get(0).getText() + ".trim", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().trim());
    }
}
