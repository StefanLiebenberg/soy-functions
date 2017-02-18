package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.SanitizedContent;

import java.util.Set;

public abstract class AbstractSanitizedSoyFunction extends AbstractSoyFunction<SanitizedContent> {

    public AbstractSanitizedSoyFunction(final String name, final Set<Integer> argSizes) {
        super(name, argSizes);
    }
}
