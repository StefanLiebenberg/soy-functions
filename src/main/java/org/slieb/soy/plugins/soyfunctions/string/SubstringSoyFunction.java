package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.Collections;
import java.util.List;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;

@SoyPureFunction
public class SubstringSoyFunction extends AbstractSoyFunction.AbstractSoyPureFunction {

    private static final String SUBSTRING = "%s.substring(%s, %s)";

    public SubstringSoyFunction() {
        super("substring", Collections.singleton(3));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String stringValue = list.get(0).getText();
        final String from = list.get(1).getText();
        final String to = list.get(2).getText();
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(String.format(SUBSTRING, stringValue, from, to), MAX_VALUE));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        return StringData.forValue(list.get(0).stringValue().substring(list.get(1).integerValue(), list.get(2).integerValue()));
    }
}
