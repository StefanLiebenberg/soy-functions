package org.slieb.soy.plugins.soyfunctions.string;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.io.IOException;
import java.util.Map;

public class ToUpperCaseSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    @Test
    public void testToUpperCaseSoyFunction() throws IOException {
        String value = "Some string That Has Mixed case.";
        String expected = value.toUpperCase();
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", value);
        assertRenders(expected, "templates.integration.toUpperCase", data);
    }
}
