package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@SoyPureFunction
public class SplitSoyFunction extends AbstractSoyFunction {

    public SplitSoyFunction() {
        super("split", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        JsExpr jsExpr = list.get(0);
        JsExpr separator = list.get(1);
        return new JsExpr(jsExpr.getText() + ".split(" + separator.getText() + ")", Integer.MAX_VALUE);
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> list) {
        final SoyValue string = list.get(0);
        final SoyValue separator = list.get(1);
        return new SoyListData(Arrays.stream(string.stringValue().split(separator.stringValue()))
                                     .map(StringData::forValue).collect(toList()));
    }
}
