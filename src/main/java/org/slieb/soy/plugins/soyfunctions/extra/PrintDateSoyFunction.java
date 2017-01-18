package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class PrintDateSoyFunction extends AbstractSoyFunction {

    public PrintDateSoyFunction() {
        super("printDate", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        throw new RuntimeException(getName() + " is not unimplemented yet");
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        throw new RuntimeException(getName() + " is not unimplemented yet");
    }
}


