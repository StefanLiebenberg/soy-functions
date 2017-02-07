package org.slieb.soy.plugins.soyfunctions;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mozilla.javascript.NativeObject;
import org.slieb.runtimes.rhino.EnvJSRuntime;

import java.io.IOException;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class ScriptSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    private static final String LINK_HTML = "<script type=\"text/javascript\" src=\"%s\"></script>";

    private static final String CSS_URL = "http://domain.com/style.js";

    @Test
    @Ignore
    public void testProducesScriptTag() throws IOException {
        assertEquals(String.format(LINK_HTML, CSS_URL), renderScriptWithTofu(CSS_URL));
        assertEquals(String.format(LINK_HTML, CSS_URL), renderScriptWithJs(CSS_URL));
    }

    @Test
    @Ignore
    public void testPrintsScriptTagWithStringAttack() throws IOException {
        String attackString = "url.js\">foo<link";
        String expectedResult = String.format(LINK_HTML, "url.js&quot;&gt;foo&lt;link");
        assertEquals(expectedResult, renderScriptWithTofu(attackString));
        assertEquals(expectedResult, renderScriptWithJs(attackString));
    }

    @Test
    @Ignore
    public void printScript() throws IOException {
        try (EnvJSRuntime envJSRuntime = getEnvJs()) {
            Assert.assertEquals(String.format(LINK_HTML, "google.com/script.min.js"),
                                envJSRuntime.execute("templates.html.script({Value: 'google.com/script/.min.js' })"));
        }
    }

    @Test
    @Ignore
    public void printNaturalScript() throws IOException {
        try (EnvJSRuntime envJSRuntime = getEnvJs()) {
            System.out.println(envJSRuntime.execute("templates.html.naturalScript({Value: 'google.com' }).getContent()"));
        }
    }

    @Test
    @Ignore
    public void printScriptWithToUrl() throws IOException {
        try (EnvJSRuntime envJSRuntime = getEnvJs()) {
            System.out.println(envJSRuntime.execute("templates.html.scriptWithToUrl({Value: 'google.com' }).getContent()"));
        }
    }

    private String renderScriptWithTofu(final Object value) {
        return render("templates.html.script", singletonMap("Value", value));
    }

    private String renderScriptWithJs(final Object value) throws IOException {
        NativeObject nativeObject = new NativeObject();
        nativeObject.defineProperty("Value", value, NativeObject.READONLY);
        return renderWithJs("templates.html.script", nativeObject);
    }
}
