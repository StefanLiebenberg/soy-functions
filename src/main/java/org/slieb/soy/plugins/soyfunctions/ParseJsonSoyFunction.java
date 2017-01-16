package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.Inject;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ParseJsonSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    private final SoyJsonUtils soyJsonUtil;

    @Inject
    public ParseJsonSoyFunction(final SoyJsonUtils soyJsonUtil) {this.soyJsonUtil = soyJsonUtil;}

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return soyJsonUtil.fromString(list.get(0).coerceToString());
    }

    @Override
    public String getName() {
        return "parseJson";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        return new JsExpr("JSON.parse(" + jsExpr.getText() + ")", Integer.MAX_VALUE);
    }
}
