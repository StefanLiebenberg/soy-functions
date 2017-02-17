package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.restricted.PrimitiveData;
import com.google.template.soy.shared.restricted.SoyPureFunction;

import java.util.Set;

@SoyPureFunction
public abstract class AbstractSoyPureFunction extends AbstractSoyFunction<PrimitiveData> {

    public AbstractSoyPureFunction(final String name, final Set<Integer> argSizes) {
        super(name, argSizes);
    }
}
