package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;

import java.util.List;
import java.util.Set;

public class PlaceHolderSoyFunction extends AbstractSoyFunction<SoyValue> {

    public PlaceHolderSoyFunction(final String name, final Set<Integer> argSizes) {
        super(name, argSizes);
    }

    public static PlaceHolderSoyFunction placeHolderSoyFunction(String name, Set<Integer> arguments) {
        return new PlaceHolderSoyFunction(name, arguments);
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return throwNotImplementedYet();
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return throwNotImplementedYet();
    }

    private <T> T throwNotImplementedYet() {throw new RuntimeException(getMessage());}

    protected void warnNotImplementedYet() {
        LOGGER.warn(getMessage());
    }

    private String getMessage() {return getName() + " has not been finished implemented yet!";}
}
