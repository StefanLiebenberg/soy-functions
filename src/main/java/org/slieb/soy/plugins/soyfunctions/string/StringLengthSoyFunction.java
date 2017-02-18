package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.asString;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.getProperty;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class StringLengthSoyFunction extends AbstractSoyPureFunction {

    public StringLengthSoyFunction() {
        super("strLength", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return getProperty(asString(args.get(0)), "length");
    }

    @Override
    public IntegerData computeForJava(final List<SoyValue> args) {
        final String stringValue = args.get(0).coerceToString();
        return IntegerData.forValue(stringValue.length());
    }
}
