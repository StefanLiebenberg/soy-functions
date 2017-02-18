package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.expression;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.parseInt;

/**
 * Note, this package cannot be marked as a {@link SoyPureFunction} as the resulting IntegerData really operates
 * on a long, and the pre-evaluation suite when the long has too many bits. In non-pre-evaluated situation, this
 * seems to be better.
 */
@SuppressWarnings("WeakerAccess")
public class ParseIntegerSoyFunction extends AbstractSoyFunction<IntegerData> {

    public static final int VALUE_ARG = 0;

    public static final int RADIX_ARG = 1;

    public static final int DEFAULT_RADIX = 10;

    public ParseIntegerSoyFunction() {
        super("parseInt", newHashSet(1, 2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr value = args.get(VALUE_ARG);
        final JsExpr radix = getOptional(args, RADIX_ARG).orElseGet(() -> expression(DEFAULT_RADIX));
        return parseInt(value, radix);
    }

    @Override
    public IntegerData computeForJava(final List<SoyValue> args) {
        final String value = args.get(VALUE_ARG).coerceToString();
        final Integer radix = getOptional(args, RADIX_ARG).map(SoyValue::integerValue).orElse(DEFAULT_RADIX);
        return IntegerData.forValue(Long.parseLong(value, radix));
    }
}
