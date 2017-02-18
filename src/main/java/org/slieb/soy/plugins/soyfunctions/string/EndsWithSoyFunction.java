package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callFunction;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class EndsWithSoyFunction extends AbstractSoyPureFunction implements SoyLibraryAssistedJsSrcFunction {

    private static final ImmutableSet<String> REQUIRED_LIBS = ImmutableSet.of("goog.string");

    public EndsWithSoyFunction() {
        super("endsWith", singleton(2));
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return REQUIRED_LIBS;
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return callFunction("goog.strings.endsWith", list.get(0), list.get(1));
    }

    @Override
    public BooleanData computeForJava(final List<SoyValue> list) {
        final String str = list.get(0).stringValue();
        final String prefix = list.get(1).stringValue();
        return BooleanData.forValue(str.endsWith(prefix));
    }
}
