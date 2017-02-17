package org.slieb.soy.plugins.soyfunctions.html;

import com.google.common.html.types.*;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.JsExprUtils;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Deprecated
public class StyleSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        JsExpr jsExpr = args.get(0);
        JsExpr wrap1 = new JsExpr("$$filterNormalizeUri(" + jsExpr.getText() + ")", Integer.MAX_VALUE);
        JsExpr wrap2 = new JsExpr("soy.$$escapeHtmlAttribute(" + wrap1.getText() + ")", Integer.MAX_VALUE);
        return JsExprUtils.maybeWrapAsSanitizedContent(SanitizedContent.ContentKind.HTML, new JsExpr(
                String.format("'<link rel=\"stylesheet\" href=' + %s + '/>'", wrap2.getText()), Integer.MAX_VALUE));
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        if (soyValue instanceof SanitizedContent) {
            SanitizedContent sanitizedContent = (SanitizedContent) soyValue;
            switch (sanitizedContent.getContentKind()) {
                case TRUSTED_RESOURCE_URI:
                    return getSanitizedLinkWithTrustedResourceUrl(TrustedResourceUrls.fromConstant(sanitizedContent.getContent()));
                case URI:
                    return getSanitizedLink(SafeUrls.sanitize(sanitizedContent.getContent()));
            }
        }
        return getSanitizedLink(SafeUrls.sanitize(soyValue.coerceToString()));
    }

    private SanitizedContent getSanitizedLink(final SafeUrl safeUrl) {
        TrustedResourceUrl value = TrustedResourceUrls.fromConstant(safeUrl.getSafeUrlString());
        return getSanitizedLinkWithTrustedResourceUrl(value);
    }

    private SanitizedContent getSanitizedLinkWithTrustedResourceUrl(final TrustedResourceUrl value) {
        return SanitizedContents.fromSafeHtml(
                new SafeHtmlBuilder("link")
                        .setHref(value)
                        .setRel("stylesheet")
                        .useSlashOnVoid()
                        .build());
    }

    /**
     * Gets the name of the Soy function.
     *
     * @return The name of the Soy function.
     */
    @Override
    public String getName() {
        return "style";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }
}
