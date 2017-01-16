package org.slieb.soy.plugins.soyfunctions;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * WIP
 */
class PrintDateSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        throw new RuntimeException(getName() + " is not implemented yet");
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        throw new RuntimeException(getName() + " is not implemented yet");
    }

    @Override
    public String getName() {
        return "printDate";
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


