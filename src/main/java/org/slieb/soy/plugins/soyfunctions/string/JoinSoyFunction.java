package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyList;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.template.soy.data.SanitizedContent.ContentKind.TEXT;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.lang.Integer.MAX_VALUE;

@SoyPureFunction
public class JoinSoyFunction extends AbstractSoyPureFunction {

    private static final String JOIN = "%s.join(%s)";

    public JoinSoyFunction() {
        super("join", Collections.singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr jsExpr = list.get(0);
        final JsExpr separator = list.get(1);
        return maybeWrapAsSanitizedContent(TEXT, new JsExpr(String.format(JOIN, jsExpr.getText(), separator.getText()), MAX_VALUE));
    }

    @Override
    public StringData computeForJava(final List<SoyValue> list) {
        final SoyValue strings = list.get(0);
        final SoyValue separator = list.get(1);
        if (strings instanceof SoyList) {
            final SoyList stringsList = (SoyList) strings;
            return StringData.forValue(stringsList.asResolvedJavaList()
                                               .stream()
                                               .map(SoyValue::stringValue)
                                               .collect(Collectors.joining(separator.stringValue())));
        } else {
            throw new RuntimeException("not a soylist");
        }
    }
}
