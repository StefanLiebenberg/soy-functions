package org.slieb.soy.plugins.soyfunctions;

import com.google.common.html.types.TrustedResourceUrls;
import org.junit.Test;
import org.slieb.soy.plugins.soyfunctions.models.TrustedUrlResourceSoyValue;

import static com.google.template.soy.data.SanitizedContents.fromTrustedResourceUrl;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class StyleSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    private static final String LINK_HTML = "<link href=\"%s\" rel=\"stylesheet\"/>";

    private static final String CSS_URL = "http://domain.com/style.css";

    @Test
    public void testPrintsStyleTagWithTrustedUri() {
        assertEquals(String.format(LINK_HTML, CSS_URL), renderStyle(fromTrustedResourceUrl(
                TrustedResourceUrls.fromConstant(CSS_URL))));
    }

    @Test
    public void testPrintsStyleTagWithString() {
        assertEquals(String.format(LINK_HTML, CSS_URL), renderStyle(CSS_URL));
    }

    @Test
    public void testPrintsStyleTagWithToUrl() {
        Object cssUrl = CSS_URL;
        assertEquals(String.format(LINK_HTML, CSS_URL), renderStyleWithToUrl(cssUrl));
    }

    @Test
    public void testPrintsStyleTagWithStringAttack() {
        Object value = "url.css\">foo<link";
        assertEquals(String.format(LINK_HTML, "url.css&quot;&gt;foo&lt;link"), renderStyle(value));
    }

    @Test
    public void testPrintsStyleTagWithCustomStyleNode() {
        TrustedUrlResourceSoyValue value = new TrustedUrlResourceSoyValue(TrustedResourceUrls.fromConstant(CSS_URL));
        assertEquals(String.format(LINK_HTML, CSS_URL), renderStyle(value));
    }

    private String renderStyle(final Object value) {
        return render("templates.html.style", singletonMap("Value", value));
    }

    private String renderStyleWithToUrl(final Object cssUrl) {
        return render("templates.html.styleWithToUrl", singletonMap("Value", cssUrl));
    }
}
