package org.slieb.soy.plugins.soyfunctions.html;

import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import static java.util.Collections.singleton;

public class HtmlSoyFunction extends AbstractSoyFunction.PlaceHolderSoyFunction {

    public HtmlSoyFunction() {
        super("html", singleton(1));
    }
}
