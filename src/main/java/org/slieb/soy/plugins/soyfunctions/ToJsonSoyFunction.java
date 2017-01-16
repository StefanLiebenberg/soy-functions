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

public class ToJsonSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    private final SoyJsonUtils soyJsonUtil;

    @Inject
    public ToJsonSoyFunction(final SoyJsonUtils soyJsonUtil) {this.soyJsonUtil = soyJsonUtil;}

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return soyJsonUtil.toJson(list.get(0));
    }

    @Override
    public String getName() {
        return "toJson";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }
    
    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        return new JsExpr("JSON.stringify(" + jsExpr.getText() + ")", Integer.MAX_VALUE);
    }
}
