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
public class SubstringSoyFunction extends AbstractSoyPureFunction {

    public SubstringSoyFunction() {
        super("substring", singleton(3));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return callOn(asString(list.get(0)), "substring", list.get(1), list.get(2));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().substring(list.get(1).integerValue(), list.get(2).integerValue()));
    }
}
