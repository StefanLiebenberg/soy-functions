package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSanitizedSoyFunction;
import org.slieb.soy.plugins.soyfunctions.internal.SoyJsonUtils;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.JS;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

public class ToJsonSoyFunction extends AbstractSanitizedSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    private static final String JSON_STRINGIFY = "goog.json.serialize(%s)";

    private final SoyJsonUtils soyJsonUtil;

    @Inject
    public ToJsonSoyFunction(final SoyJsonUtils soyJsonUtil) {
        super("toJson", singleton(1));
        this.soyJsonUtil = soyJsonUtil;
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        final JsExpr jsonExpr = new JsExpr(String.format(JSON_STRINGIFY, jsExpr.getText()), MAX_VALUE);
        return maybeWrapAsSanitizedContent(JS, jsonExpr);
    }

    @Override
    public SanitizedContent computeForJava(final List<SoyValue> list) {
        return soyJsonUtil.toJson(list.get(0));
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.json");
    }
}
