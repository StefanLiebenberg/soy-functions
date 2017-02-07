package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.common.collect.ImmutableSet;
import com.google.common.html.types.SafeUrls;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.jssrc.restricted.SoyLibraryAssistedJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ToSafeUrlSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction, SoyLibraryAssistedJsSrcFunction {

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        if (soyValue instanceof SanitizedContent) {
            switch (((SanitizedContent) soyValue).getContentKind()) {
                case TRUSTED_RESOURCE_URI:
                case URI:
                    return soyValue;
            }
        }
        return SanitizedContents.fromSafeUrl(SafeUrls.sanitize(soyValue.coerceToString()));
    }

    /**
     * Gets the name of the Soy function.
     *
     * @return The name of the Soy function.
     */
    @Override
    public String getName() {
        return "toSafeUrl";
    }

    /**
     * Gets the set of valid args list sizes. For example, the set {0, 2} would indicate that this
     * function can take 0 or 2 arguments (but not 1).
     *
     * @return The set of valid args list sizes.
     */
    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }

    /**
     * Computes this function on the given arguments for the JS Source backend.
     *
     * @param args The function arguments.
     * @return The computed result of this function.
     */
    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        return new JsExpr("goog.html.SafeUrl.sanitize(" + args.get(0).getText() + ")", Integer.MAX_VALUE);
    }

    /**
     * Returns a list of Closure library names to require when this function is used.
     * <p>
     * <p> Note: Return the raw Closure library names, Soy will wrap them in goog.require for you.
     *
     * @return A collection of strings representing Closure JS library names
     */
    @Override
    public ImmutableSet<String> getRequiredJsLibNames() {
        return ImmutableSet.of("goog.html.SafeUrl");
    }
}
