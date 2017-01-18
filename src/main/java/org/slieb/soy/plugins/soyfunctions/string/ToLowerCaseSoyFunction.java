package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class ToLowerCaseSoyFunction extends AbstractSoyFunction {

    public ToLowerCaseSoyFunction() {
        super("toLowerCase", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return new JsExpr(args.get(0).getText() + ".toLowerCase()", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        return StringData.forValue(args.get(0).stringValue().toLowerCase());
    }
}
