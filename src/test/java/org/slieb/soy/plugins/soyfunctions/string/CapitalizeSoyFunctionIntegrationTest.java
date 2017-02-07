package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.Maps;
import com.google.template.soy.data.SanitizedContent;
import org.junit.Test;
import org.mozilla.javascript.NativeObject;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpellCheckingInspection")
public class CapitalizeSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    private String doCapitalize(final String string) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        return getRenderer("templates.integration.capitalize")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    private String doCapitalizeJs(final String string) throws IOException {
        NativeObject nativeObject = new NativeObject();
        nativeObject.defineProperty("String", string, NativeObject.READONLY);
        return renderWithJs("templates.integration.capitalize", nativeObject);
    }

    @Test
    public void testCapitalize() throws IOException {
        assertEquals("Name", doCapitalize("name"));
        assertEquals("Name", doCapitalizeJs("name"));
    }

    @Test
    public void testCapitalizeAndLowercaseTheRest() throws IOException {
        assertEquals("Namewithotherparts", doCapitalize("nameWithOtherParts"));
        assertEquals("Namewithotherparts", doCapitalizeJs("nameWithOtherParts"));
    }
}
