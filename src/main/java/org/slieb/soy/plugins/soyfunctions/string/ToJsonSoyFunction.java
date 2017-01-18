package org.slieb.soy.plugins.soyfunctions.string;

import com.google.inject.Inject;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.internal.SoyJsonUtils;

import java.util.List;

import static java.util.Collections.singleton;

@SoyPureFunction
public class ToJsonSoyFunction extends AbstractSoyFunction {

    private final SoyJsonUtils soyJsonUtil;

    @Inject
    public ToJsonSoyFunction(final SoyJsonUtils soyJsonUtil) {
        super("toJson", singleton(1));
        this.soyJsonUtil = soyJsonUtil;
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return soyJsonUtil.toJson(list.get(0));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        return new JsExpr("JSON.stringify(" + jsExpr.getText() + ")", Integer.MAX_VALUE);
    }
}
