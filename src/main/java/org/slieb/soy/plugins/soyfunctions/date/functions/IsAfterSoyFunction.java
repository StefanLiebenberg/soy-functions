package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.date.models.SoyDate;
import org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.DATE_LIBS;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.assertDateLike;
import static org.slieb.soy.plugins.soyfunctions.internal.SoyFunctionsJSExprUtils.inlineStatements;
import static org.slieb.soy.plugins.soyfunctions.internal.SoyFunctionsJSExprUtils.jsExpr;

public class IsAfterSoyFunction extends AbstractSoyFunction<BooleanData> implements SoyLibraryAssistedJsSrcFunction {

    public IsAfterSoyFunction() {
        super("isAfter", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr current = list.get(0);
        final JsExpr compareTo = list.get(1);
        final JsExpr currentAssertion = assertDateLike(current, "First argument passed to isAfter(date, compareTo) is not a DateLike object");
        final JsExpr compareToAssertion = assertDateLike(compareTo, "Second argument passed to isAfter(date, compareTo) is not a DateLike object");
        final JsExpr compareResult = JsExprDateUtils.compareDatesExpr(current, compareTo);
        final JsExpr comparisonExpression = jsExpr(String.format("%s > 0", compareResult.getText()));
        return inlineStatements(currentAssertion, compareToAssertion, comparisonExpression);
    }

    @Override
    public BooleanData computeForJava(final List<SoyValue> list) {
        final SoyDate current = SoyDate.class.cast(list.get(0));
        final SoyDate compareTo = SoyDate.class.cast(list.get(1));
        return BooleanData.forValue(current.getInstant().isAfter(compareTo.getInstant()));
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.copyOf(DATE_LIBS);
    }
}
