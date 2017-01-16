package org.slieb.soy.plugins.soyfunctions;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
import com.google.template.soy.tofu.SoyTofu;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

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
        return Guice.createInjector(new SoyModule(), new SoyFunctionsModule())
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
}
