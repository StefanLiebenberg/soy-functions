package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.Maps;
import org.junit.Ignore;
import org.junit.Test;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PrintDateSoyFunctionIntegrationTest extends DateSoyFunctionsIntegrationBaseTest {

    @Test
    @Ignore
    public void shouldPrintInstant() throws IOException {
        Instant now = Instant.now();
        String expected = DateTimeFormatter.ISO_INSTANT.format(now);
        assertRenderEquals(expected, "templates.date.printDate",
                           (data) -> {
                               data.put("Date", now);
                           },
                           (data, runtime) -> {
                               data.defineProperty("Date", getJSRuntimeDateFromInstant(now, runtime), ScriptableObject.READONLY);
                           });
    }

    @Test
    @Ignore
    public void testPrintDate() {
        String expected = "expected!";
        Map<String, Object> data = Maps.newHashMap();
        assertEquals(expected, render("templates.integration.printDate", data));
    }
}
