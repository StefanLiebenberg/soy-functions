package org.slieb.soy.plugins.soyfunctions.html;

import com.google.common.html.types.SafeHtmls;
import com.google.common.html.types.TrustedResourceUrl;
import com.google.common.html.types.TrustedResourceUrls;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SanitizedContents;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.jssrc.restricted.SoyJsSrcFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyJavaFunction;
import org.slieb.soy.plugins.soyfunctions.models.ImportableNode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.template.soy.data.SanitizedContent.ContentKind.HTML;
import static com.google.template.soy.jssrc.restricted.JsExprUtils.maybeWrapAsSanitizedContent;

public class ScriptSoyFunction implements SoyFunction, SoyJavaFunction, SoyJsSrcFunction {

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> args) {
        final JsExpr jsExpr = args.get(0);
        final String expression = String.format("'<script type=\"text/javascript\" src=\"' + %s(%s(%s)) + '\"></script>'",
                                                "soy.$$escapeHtmlAttribute",
                                                "soy.$$filterNormalizeUri",
                                                jsExpr.getText());
        return maybeWrapAsSanitizedContent(HTML, new JsExpr(expression, Integer.MAX_VALUE));
    }

    @Override
    public SoyValue computeForJava(final List<SoyValue> args) {
        final SoyValue soyValue = args.get(0);
        if (soyValue instanceof ImportableNode) {
            return getSanitizedScriptWithTrustedResourceUrl(((ImportableNode) soyValue).getImportResourceUrl());
        }
        if (soyValue instanceof SanitizedContent) {
            SanitizedContent sanitizedContent = (SanitizedContent) soyValue;
            switch (sanitizedContent.getContentKind()) {
                case TRUSTED_RESOURCE_URI:
                    return getSanitizedScriptWithTrustedResourceUrl(TrustedResourceUrls.fromConstant(sanitizedContent.getContent()));
            }
        }
        throw new RuntimeException("not trusted url!");
    }

    private SoyValue getSanitizedScriptWithTrustedResourceUrl(final TrustedResourceUrl value) {
        return SanitizedContents.fromSafeHtml(SafeHtmls.fromScriptUrl(value));
    }

    /**
     * Gets the name of the Soy function.
     *
     * @return The name of the Soy function.
     */
    @Override
    public String getName() {
        return "script";
    }

    @Override
    public Set<Integer> getValidArgsSizes() {
        return Collections.singleton(1);
    }
}

