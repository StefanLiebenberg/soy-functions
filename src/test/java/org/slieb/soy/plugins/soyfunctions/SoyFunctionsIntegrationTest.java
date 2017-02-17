package org.slieb.soy.plugins.soyfunctions;

import com.google.common.collect.Maps;
import com.google.template.soy.data.SanitizedContent;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@Deprecated
public class SoyFunctionsIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    @Test
    public void testParseJson() {
        String expected = "11";
        Map<String, Object> data = Maps.newHashMap();
        data.put("JsonString", "{\"Property\": 10 }");
        assertEquals(expected, render("templates.integration.fromJson", data));
    }

    @Test
    public void testParseInteger() {
        String expected = "10";
        Map<String, Object> data = Maps.newHashMap();
        data.put("IntString", "1");
        assertEquals(expected, render("templates.integration.parseInt", data));
    }

    @Test
    public void testParseFloat() {
        String expected = "0.05";
        Map<String, Object> data = Maps.newHashMap();
        data.put("FloatString", "0.5");
        assertEquals(expected, render("templates.integration.parseFloat", data));
    }







    @Test
    public void testToJson() {
        String expected = "{\"Property\":100}";
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> object = Maps.newHashMap();
        object.put("Property", 100);
        data.put("Object", object);
        assertEquals(expected, getRenderer("templates.integration.toJson")
                .setContentKind(SanitizedContent.ContentKind.JS)
                .setData(data)
                .render());
    }



    private String doSubstring(final String string, final int start, final int end) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        data.put("Start", start);
        data.put("End", end);
        return getRenderer("templates.integration.substring")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    @Test
    public void testSubstring() {
        assertEquals("the", doSubstring("the car is parked here", 0, 3));
    }

    private String doSplit(final String string, final String separator) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        data.put("Separator", separator);
        return getRenderer("templates.integration.split")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    @Test
    public void testSplit() {
        assertEquals("[a, b, c]", doSplit("a/b/c", "/"));
        assertEquals("[a, b, , c]", doSplit("a/b//c", "/"));
    }

    private String doJoin(String[] string, String separator) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("Strings", Arrays.stream(string).collect(toList()));
        data.put("Separator", separator);
        return getRenderer("templates.integration.join")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    @Test
    public void testJoin() {
        assertEquals("A/B", doJoin(Stream.of("A", "B").toArray(String[]::new), "/"));
        assertEquals("some/things/split", doJoin(Stream.of("some", "things", "split").toArray(String[]::new), "/"));
        assertEquals("a//b", doJoin(Stream.of("a", "", "b").toArray(String[]::new), "/"));
    }

    private String doToFixed(String number, int digits) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("Number", number);
        data.put("Digits", digits);
        return getRenderer("templates.integration.toFixed")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    @Test
    public void testToFixed() {
        assertEquals("10.00", doToFixed("10", 2));
        assertEquals("10.1", doToFixed("10.123", 1));
        assertEquals("0.0124", doToFixed("0.012356789", 4));
    }
}
