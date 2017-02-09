package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;

public class EndsWithSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction implements SoyLibraryAssistedJsSrcFunction {

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
        final String str = list.get(0).getText(); ;
        final String prefix = list.get(1).getText();
        return new JsExpr(String.format("goog.string.endsWith(%s, %s)", str, prefix), Integer.MAX_VALUE);
    }

    @Override
    public BooleanData computeForJava(final List<SoyValue> list) {
        final String str = list.get(0).stringValue();
        final String prefix = list.get(1).stringValue();
        return BooleanData.forValue(str.startsWith(prefix));
    }
}
