package org.slieb.soy.plugins.soyfunctions.html;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.NullData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HtmlSoyFunction implements SoyJsSrcFunction, SoyJavaFunction, SoyFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return args.get(0);
    }

    /**
     * Computes this function on the given arguments.
     *
     * @param args The function arguments.
     * @return The computed result of this function.
     */
    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        return NullData.INSTANCE;
    }

    /**
     * Gets the name of the Soy function.
     *
     * @return The name of the Soy function.
     */
    @Override
    public String getName() {
        return "html";
    }

    /**
     * Gets the set of valid args list sizes. For example, the set {0, 2} would indicate that this
     * function can take 0 or 2 arguments (but not 1).
     *
     * @return The set of valid args list sizes.
     */
    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }
}
