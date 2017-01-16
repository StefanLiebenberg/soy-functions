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

/**
 * Created by stefan on 1/12/17.
 */
public class ToUpperCaseSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return new JsExpr(args.get(0).getText() + ".toUpperCase()", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        return StringData.forValue(args.get(0).stringValue().toUpperCase());
    }

    @Override
    public String getName() {
        return "toUpperCase";
    }

    /**
     * Gets the set of valid args list sizes. For example, the set {0, 2} would indicate that this
     * function can take 0 or 2 arguments (but not 1).
     *
     * @return The set of valid args list sizes.
     */
    @Override
    public Set<Integer> getValidArgsSizes() {
        return singleton(1);
    }
}
