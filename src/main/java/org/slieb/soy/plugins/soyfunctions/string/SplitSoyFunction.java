package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.StringData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.utils.Expressions;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callOn;

@SuppressWarnings("WeakerAccess")
public class SplitSoyFunction extends AbstractSoyFunction<SoyListData> {

    public SplitSoyFunction() {
        super("split", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return callOn(Expressions.wrap(list.get(0)), "slit", list.get(1));
    }

    @Override
    public SoyListData computeForJava(final List<SoyValue> list) {
        final SoyValue string = list.get(0);
        final SoyValue separator = list.get(1);
        return new SoyListData(splitIntoList(string, separator));
    }

    private List<StringData> splitIntoList(final SoyValue string, final SoyValue separator) {
        return arrayToList(string.stringValue().split(separator.stringValue()));
    }

    private List<StringData> arrayToList(final String[] split) {
        return Arrays.stream(split).map(StringData::forValue).collect(toList());
    }
}
