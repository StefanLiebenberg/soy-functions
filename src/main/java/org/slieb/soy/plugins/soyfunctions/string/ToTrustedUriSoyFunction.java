package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.template.soy.data.SanitizedContent.ContentKind.TRUSTED_RESOURCE_URI;
import static com.google.template.soy.data.UnsafeSanitizedContentOrdainer.ordainAsSafe;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;

public class ToTrustedUriSoyFunction extends AbstractSoyFunction.AbstractSanitizedSoyFunction {

    public ToTrustedUriSoyFunction() {
        super("toTrustedUri", newHashSet(1, 2));
    }

    @Override
    public SanitizedContent computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        return ordainAsSafe(soyValue.coerceToString(), TRUSTED_RESOURCE_URI);
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return maybeWrapAsSanitizedContent(TRUSTED_RESOURCE_URI, args.get(0));
    }
}
