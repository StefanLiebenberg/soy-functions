package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

public abstract class AbstractSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    private final String name;

    private final Set<Integer> argSizes;

    public AbstractSoyFunction(final String name, final Set<Integer> argSizes) {
        this.name = name;
        this.argSizes = argSizes;
    }

    @Override
    public abstract JsExpr computeForJsSrc(final List<JsExpr> list);

    @Override
    public abstract SoyValue computeForJava(final List<SoyValue> list);

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Set<Integer> getValidArgsSizes() {
        return argSizes;
    }
}
