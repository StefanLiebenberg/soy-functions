package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.math.BigDecimal;
import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

// todo, find some formatting stuff in goog lib?
@SoyPureFunction
public class ToFixedSoyFunction extends AbstractSoyPureFunction {

    private static final String TO_FIXED = "Number(%s).toFixed(%s)";

    public ToFixedSoyFunction() {
        super("toFixed", singleton(2));
    }

    private static String toFixed(final String n, final int digits) {
        final BigDecimal bigDecimal = new BigDecimal(n);
        return bigDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP).toString();
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String numberString = list.get(0).getText();
        final String radius = list.get(1).getText();
        final JsExpr fixedExpr = new JsExpr(String.format(TO_FIXED, numberString, radius), MAX_VALUE);
        return maybeWrapAsSanitizedContent(TEXT, fixedExpr);
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(toFixed(list.get(0).coerceToString(), list.get(1).integerValue()));
    }
}
