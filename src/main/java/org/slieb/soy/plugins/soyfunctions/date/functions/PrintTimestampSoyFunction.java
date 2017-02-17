package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSanitizedSoyFunction;

import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.JS;
import static com.google.template.soy.data.UnsafeSanitizedContentOrdainer.ordainAsSafe;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

public class PrintTimestampSoyFunction extends AbstractSanitizedSoyFunction {

    public PrintTimestampSoyFunction() {
        super("printTimestamp", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String text = list.get(0).getText();
        final String expression = String.format("String(Number(%s))", text);
        return maybeWrapAsSanitizedContent(JS, new JsExpr(expression, MAX_VALUE));
    }

    @Override
    public SanitizedContent computeForJava(final List<SoyValue> list) {
        return ordainAsSafe(String.valueOf(list.get(0).longValue()), JS);
    }
}
