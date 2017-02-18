package org.slieb.soy.plugins.soyfunctions.urls;

import com.google.common.collect.ImmutableSet;
import com.google.common.html.types.SafeUrls;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSanitizedSoyFunction;

import java.util.List;

import static java.util.Collections.singleton;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.callFunction;

public class ToSafeUrlSoyFunction extends AbstractSanitizedSoyFunction implements SoyLibraryAssistedJsSrcFunction {

    public ToSafeUrlSoyFunction() {
        super("toSafeUrl", singleton(1));
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return callFunction("goog.html.SafeUrl.sanitize", args.get(0));
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
        return SanitizedContents.fromSafeUrl(SafeUrls.sanitize(soyValue.stringValue()));
    }

    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.html.SafeUrl");
    }
}
