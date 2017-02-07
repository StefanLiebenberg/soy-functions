package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.io.IOException;
import java.util.Map;

public class TrimSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    @Test
    public void testTrim() throws IOException {
        doTrim("no space is left", " no space is left  ");
    }

    private void doTrim(final String expected, final String string) throws IOException {
        String templateName = "templates.integration.trim";
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        assertRenders(expected, templateName, data);
    }
}
