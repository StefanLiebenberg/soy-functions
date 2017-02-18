package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.jssrc.restricted.JsExpr;

import java.time.Instant;
import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.compareDatesExpr;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.parenthesizedList;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.expression;

public class IsAfterSoyFunction extends AbstractSoyDateFunction<BooleanData> {

    public IsAfterSoyFunction() {
        super("isAfter", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr currentExpr = getInstantExpr(list.get(0), badArgumentMessage());
        final JsExpr compareToExpr = getInstantExpr(list.get(1), badArgumentMessage());
        final JsExpr compareResult = compareDatesExpr(currentExpr, compareToExpr);
        final JsExpr comparisonExpression = expression(String.format("%s > 0", compareResult.getText()));
        return parenthesizedList(currentExpr, compareToExpr, comparisonExpression);
    }

    @Override
    public BooleanData computeForJava(final List<SoyValue> list) {
        final Instant currentInstant = getInstant(list.get(0), badArgumentMessage());
        final Instant compareToInstant = getInstant(list.get(1), badArgumentMessage());
        final boolean after = currentInstant.isAfter(compareToInstant);
        return BooleanData.forValue(after);
    }
}
