package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.restricted.NumberData;
import com.google.template.soy.data.restricted.SoyString;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.date.models.InstantSoyValue;
import org.slieb.soy.plugins.soyfunctions.date.models.SoyDate;

import java.time.Instant;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule.NOW_INSTANT;
import static org.slieb.soy.plugins.soyfunctions.internal.SoyFunctionsJSExprUtils.callFunction;
import static org.slieb.soy.plugins.soyfunctions.internal.SoyFunctionsJSExprUtils.newCall;

/**
 * The {@code toInstant(value)} produces either a {@link InstantSoyValue} or {@code Date} object.
 * <h3>Soy Tofu</h3>
 * <p>When evaluating in the soy tofu renderer, {@code toInstant()} will produce a {@link InstantSoyValue} and will accept any of the following for input:</p>
 * <ul>
 * <li>Any instance of {@link SoyDate}</li>
 * <li>Any instance of {@link SoyString} that can be turned into a long</li>
 * <li>Any instance of {@link NumberData} that can be turned into a long</li>
 * </ul>
 * <h3>JavaScript Runtime</h3>
 * <p>When evaluating in the javascript runtime, {@code toInstant()} will produce a {@code Date} object and will accept any of the following for
 * input:</p>
 * <ul>
 * <li>Any object that is {@code goog.date.DateLike}, it will use {@code goog.isDateLike()} to check for this.</li>
 * <li>Any string that can be parsed into an integer with {@code parseInt(string, 10)}</li>
 * <li>Any number value that can be given to {@code new Date(number)}</li>
 * </ul>
 * <p>Otherwise an exception will be thrown.</p>
 */
public class ToInstantSoyFunction extends AbstractSoyDateFunction<InstantSoyValue> {

    private final Provider<Instant> nowProvider;

    @Inject
    public ToInstantSoyFunction(@Named(NOW_INSTANT) final Provider<Instant> nowProvider) {
        super("toInstant", newHashSet(0, 1));
        this.nowProvider = nowProvider;
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        if (list.isEmpty()) {
            return newCall("Date", callFunction("Date.now"));
        } else {
            return getInstantExpr(list.get(0), badArgumentMessage());
        }
    }

    @Override
    public InstantSoyValue computeForJava(final List<SoyValue> list) {
        return new InstantSoyValue(list.isEmpty() ? nowProvider.get() : getInstant(list.get(0), badArgumentMessage()));
    }
}
