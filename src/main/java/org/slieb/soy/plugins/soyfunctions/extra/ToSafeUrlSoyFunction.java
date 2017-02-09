package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.common.collect.ImmutableSet;
import com.google.common.html.types.SafeUrls;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.singleton;

public class ToSafeUrlSoyFunction extends AbstractSoyFunction.AbstractSanitizedSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    public ToSafeUrlSoyFunction() {
        super("toSafeUrl", singleton(1));
    }


    @Override
    public SanitizedContent computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        if (soyValue instanceof SanitizedContent) {
            switch (((SanitizedContent) soyValue).getContentKind()) {
                case TRUSTED_RESOURCE_URI:
                case URI:
                    return (SanitizedContent) soyValue;
            }
        }
        return SanitizedContents.fromSafeUrl(SafeUrls.sanitize(soyValue.coerceToString()));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return new JsExpr("goog.html.SafeUrl.sanitize(" + args.get(0).getText() + ")", MAX_VALUE);
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.html.SafeUrl");
    }
}
