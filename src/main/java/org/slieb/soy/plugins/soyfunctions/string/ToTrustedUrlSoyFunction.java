package org.slieb.soy.plugins.soyfunctions.string;

import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.template.soy.data.SanitizedContent.ContentKind.TRUSTED_RESOURCE_URI;
import static com.google.template.soy.data.UnsafeSanitizedContentOrdainer.ordainAsSafe;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;
import static java.util.Collections.unmodifiableSet;

public class ToTrustedUrlSoyFunction implements SoyJsSrcFunction, SoyJavaFunction {

    @Override
    public String getName() {
        return "toTrustedUrl";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return unmodifiableSet(newHashSet(1, 2));
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        return ordainAsSafe(args.get(0).coerceToString(), TRUSTED_RESOURCE_URI);
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return maybeWrapAsSanitizedContent(TRUSTED_RESOURCE_URI, args.get(0));
    }
}
