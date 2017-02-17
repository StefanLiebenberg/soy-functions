package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.internal.SoyJsonUtils;

import java.util.List;

import static java.util.Collections.singleton;

// todo, test pure function
@SoyPureFunction
public class ParseJsonSoyFunction extends AbstractSoyFunction<SoyValue> implements SoyLibraryAssistedJsSrcFunction {

    private static final String PARSE_JSON = "goog.json.parse(%s)";

    private final SoyJsonUtils soyJsonUtil;

    @Inject
    public ParseJsonSoyFunction(final SoyJsonUtils soyJsonUtil) {
        super("parseJson", singleton(1));
        this.soyJsonUtil = soyJsonUtil;
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        return new JsExpr(String.format(PARSE_JSON, jsExpr.getText()), Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        return soyJsonUtil.fromString(list.get(0).coerceToString());
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.json");
    }
}
