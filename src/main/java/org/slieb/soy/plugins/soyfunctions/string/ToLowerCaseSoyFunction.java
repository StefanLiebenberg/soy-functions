package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.asString;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callOn;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class ToLowerCaseSoyFunction extends AbstractSoyPureFunction {

    public ToLowerCaseSoyFunction() {
        super("toLowerCase", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return callOn(asString(args.get(0)), "toLowerCase");
    }

    @Override
    public StringData computeForJava(final List<SoyValue> args) {
        return StringData.forValue(args.get(0).coerceToString().toLowerCase());
    }
}
