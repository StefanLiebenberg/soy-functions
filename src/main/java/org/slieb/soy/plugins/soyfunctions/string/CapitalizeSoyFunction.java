package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.utils.Expressions;

import java.util.List;

import static java.util.Collections.singleton;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class CapitalizeSoyFunction extends AbstractSoyPureFunction
        implements SoyLibraryAssistedJsSrcFunction {

    public CapitalizeSoyFunction() {
        super("capitalize", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return Expressions.callFunction("goog.string.capitalize", list.get(0));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        final String stringValue = list.get(0).stringValue();
        return StringData.forValue(stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1).toLowerCase());
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.string");
    }
}
