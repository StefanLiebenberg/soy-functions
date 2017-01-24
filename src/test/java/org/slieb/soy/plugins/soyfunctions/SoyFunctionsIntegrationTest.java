package org.slieb.soy.plugins.soyfunctions;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.tofu.SoyTofu;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class SoyFunctionsIntegrationTest {

    private SoyTofu getTofu() {
        final SoyFileSet.Builder builder = getBuilder();
        final URL resource = SoyFunctionsIntegrationTest.class.getResource("templates/integration.soy");
        final SoyFileSet fileset = builder
                .add(resource)
                .build();

        return fileset
                .compileToTofu();
    }

    private SoyFileSet.Builder getBuilder() {
        return Guice.createInjector(new SoyModule(),
                                    new SoyFunctionsModule())
                    .getInstance(SoyFileSet.Builder.class);
    }

    private SoyTofu.Renderer getRenderer(final String s) {
        return getTofu().newRenderer(s);
    }

    private String render(final String templateName, final Map<String, Object> data) {
        return getRenderer(templateName).setData(data).render();
    }

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
    @Ignore
    public void testPrintDate() {
        String expected = "expected!";
        Map<String, Object> data = Maps.newHashMap();
        assertEquals(expected, render("templates.integration.printDate", data));
    }

    @Test
    public void testToLowerCaseSoyFunction() {
        String value = "Some string That Has Mixed case.";
        String expected = value.toLowerCase();
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", value);
        assertEquals(expected, render("templates.integration.toLowerCase", data));
    }

    @Test
    public void testToUpperCaseSoyFunction() {
        String value = "Some string That Has Mixed case.";
        String expected = value.toUpperCase();
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", value);
        assertEquals(expected, render("templates.integration.toUpperCase", data));
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

    private String doCapitalize(final String string) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        return getRenderer("templates.integration.capitalize")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    @Test
    public void testCapitalize() {
        assertEquals("Name", doCapitalize("name"));
    }

    @Test
    public void testTrim() {
        assertEquals("no space is left", doTrim(" no space is left  "));
    }

    private String doTrim(final String string) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("String", string);
        return getRenderer("templates.integration.trim")
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
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
