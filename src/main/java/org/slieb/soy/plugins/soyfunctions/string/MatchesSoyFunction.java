package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.restricted.SoyPureFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyPureFunction;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.internal.SoyFunctionsJSExprUtils.*;

@SuppressWarnings("WeakerAccess")
@SoyPureFunction
public class MatchesSoyFunction extends AbstractSoyPureFunction {

    public MatchesSoyFunction() {
        super("matches", singleton(2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final JsExpr string = debugLog("string", callFunction("String", list.get(0)));
        final JsExpr pattern = debugLog("pattern", newCall("RegExp", list.get(1)));
        return debugLog("result", callOn(pattern, "test", string));
    }

    @Override
    public BooleanData computeForJava(final List<SoyValue> list) {
        final String s = list.get(0).coerceToString();
        final String pattern = list.get(1).stringValue();
        final Pattern compile = Pattern.compile(pattern);
        final Matcher matcher = compile.matcher(s);
        return BooleanData.forValue(matcher.find());
    }
}
