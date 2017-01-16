package org.slieb.soy.plugins.soyfunctions;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;

public class ToLowerCaseSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return new JsExpr(args.get(0).getText() + ".toLowerCase()", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        return StringData.forValue(args.get(0).stringValue().toLowerCase());
    }

    @Override
    public String getName() {
        return "toLowerCase";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return singleton(1);
    }
}
