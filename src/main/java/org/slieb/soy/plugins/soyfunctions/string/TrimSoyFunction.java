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
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callFunction;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class TrimSoyFunction extends AbstractSoyPureFunction implements SoyLibraryAssistedJsSrcFunction {

    private static final ImmutableSet<String> REQUIRED_LIBS = ImmutableSet.of("goog.string");

    public TrimSoyFunction() {
        super("trim", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return callFunction("goog.string.trim", Expressions.asString(list.get(0)));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).coerceToString().trim());
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.string");
    }
}
