package org.slieb.soy.plugins.soyfunctions.extra;

import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import static java.util.Collections.singleton;

public class PrintDateSoyFunction extends AbstractSoyFunction.PlaceHolderSoyFunction {

    public PrintDateSoyFunction() {
        super("printDate", singleton(1));
    }
}


