package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyList;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.*;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class JoinSoyFunction extends AbstractSoyPureFunction {

    public JoinSoyFunction() {
        super("join", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr array = list.get(0);
        final JsExpr assertion = callFunction("goog.asserts.assert", callFunction("goog.isArray", array));
        final JsExpr join = callOn(wrap(array), "join", argStream(list, 1).toArray(JsExpr[]::new));
        return parenthesizedList(assertion, join);
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(stringStream(list.get(0)).collect(getCollector(list)));
    }

    private Stream<String> stringStream(final SoyValue strings) {
        checkArgument(strings instanceof SoyList);
        return SoyList.class.cast(strings).asResolvedJavaList().stream().map(SoyValue::stringValue);
    }

    private Collector<CharSequence, ?, String> getCollector(final List<SoyValue> list) {
        return getOptional(list, 1).map(SoyValue::coerceToString).<Collector<CharSequence, ?, String>>map(Collectors::joining)
                .orElseGet(Collectors::joining);
    }
}
