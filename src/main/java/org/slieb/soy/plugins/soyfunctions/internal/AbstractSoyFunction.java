package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public abstract class AbstractSoyFunction<R extends SoyValue> implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String name;

    private final Set<Integer> argSizes;

    public AbstractSoyFunction(final String name, final Set<Integer> argSizes) {
        this.name = name;
        this.argSizes = unmodifiableSet(argSizes);
    }

    protected <T> Optional<T> getOptional(List<T> t, int argNr) {
        if (t.size() > argNr) {
            return Optional.of(t.get(argNr));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public abstract JsExpr computeForJsSrc(final List<JsExpr> list);

    @Override
    public abstract R computeForJava(final List<SoyValue> list);

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Set<Integer> getValidArgsSizes() {
        return argSizes;
    }
}

