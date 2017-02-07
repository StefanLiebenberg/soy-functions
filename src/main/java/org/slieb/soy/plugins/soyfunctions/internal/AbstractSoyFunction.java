package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.PrimitiveData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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

    public static abstract class AbstractSanitizedSoyFunction extends AbstractSoyFunction<SanitizedContent> {

        public AbstractSanitizedSoyFunction(final String name, final Set<Integer> argSizes) {
            super(name, argSizes);
        }
    }

    @SoyPureFunction
    public static abstract class AbstractSoyPureFunction extends AbstractSoyFunction<PrimitiveData> {

        public AbstractSoyPureFunction(final String name, final Set<Integer> argSizes) {
            super(name, argSizes);
        }
    }

    public static abstract class PlaceHolderSoyFunction extends AbstractSoyFunction<SoyValue> {

        public PlaceHolderSoyFunction(final String name, final Set<Integer> argSizes) {
            super(name, argSizes);
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
}
