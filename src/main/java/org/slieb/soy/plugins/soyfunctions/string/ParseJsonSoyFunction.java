package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.internal.SoyJsonUtils;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callFunction;

@SuppressWarnings("WeakerAccess")
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
        return callFunction("goog.json.parse", args.get(0));
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
