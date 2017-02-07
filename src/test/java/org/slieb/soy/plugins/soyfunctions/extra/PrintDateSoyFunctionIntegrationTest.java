package org.slieb.soy.plugins.soyfunctions.extra;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class PrintDateSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    private static final Instant INSTANT = new Date(2016 - 1900, 0, 1, 12, 1).toInstant();

    @Test
    public void shouldPrintInstant() throws Exception {
        assertEquals("2016-01-01T11:01:00", render("templates.date.printDate", singletonMap("Date", INSTANT)));
    }

    @Test
    public void shouldPrintInstantWithFormatAndZoneOffset() throws Exception {
        Map<String, Object> data = Maps.newHashMap();
        data.put("Date", INSTANT.toEpochMilli());
        data.put("Offset", 0);
        data.put("Format", "yyyy-MM-dd");
        assertRenders("2016-01-01", "templates.date.printDateWithFormatAndZoneOffset", data);
    }
}
