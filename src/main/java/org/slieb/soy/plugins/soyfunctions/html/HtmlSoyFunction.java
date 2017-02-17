package org.slieb.soy.plugins.soyfunctions.html;

import org.slieb.soy.plugins.soyfunctions.internal.PlaceHolderSoyFunction;

import static java.util.Collections.singleton;

@Deprecated
public class HtmlSoyFunction extends PlaceHolderSoyFunction {

    public HtmlSoyFunction() {
        super("html", singleton(1));
    }
}
