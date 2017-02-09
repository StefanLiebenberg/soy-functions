package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.common.collect.Sets;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;
import org.slieb.soy.plugins.soyfunctions.models.InstantSoyValue;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.time.Instant.ofEpochMilli;

public class ToDateTimeSoyFunction extends AbstractSoyFunction<InstantSoyValue> {

    public ToDateTimeSoyFunction() {
        super("toDateTime", Sets.newHashSet(1, 2));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        final String newDateExpr = String.format("new goog.date.DateTime(%s)", list.get(0).getText());
        return new JsExpr(newDateExpr, MAX_VALUE);
    }

    @Override
    public InstantSoyValue computeForJava(final List<SoyValue> list) {
        final SoyValue soyValue = list.get(0);
        if (soyValue instanceof InstantSoyValue) {
            return (InstantSoyValue) soyValue;
        }
        return new InstantSoyValue(ofEpochMilli(soyValue.longValue()));
    }
}
