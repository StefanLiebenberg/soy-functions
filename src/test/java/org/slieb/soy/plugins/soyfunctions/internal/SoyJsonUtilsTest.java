package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.template.soy.data.SoyValueConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class SoyJsonUtilsTest {

    private Gson gson = new Gson();

    private SoyJsonUtils soyJsonUtils;

    @Before
    public void setUp() throws Exception {
        soyJsonUtils = new SoyJsonUtils(gson, SoyValueConverter.UNCUSTOMIZED_INSTANCE);
    }

    private String toJsonString(Object object) {
        return soyJsonUtils.toJsonFromObject(object).getContent();
    }

    @Test
    public void testFromJsonForFloat() {
        assertEquals("0.5", toJsonString(0.5));
        assertEquals("1.5", toJsonString(1.5));
    }

    @Test
    public void testFromJsonForList() {
        assertEquals("[]", toJsonString(emptyList()));
        assertEquals("[]", toJsonString(emptySet()));
        assertEquals("[1]", toJsonString(singleton(1)));
        assertEquals("[2]", toJsonString(singletonList(2)));
        assertEquals("[1, 2, 3]", toJsonString(newArrayList(1, 2, 3)));
        assertEquals("[1, \"string\", true]", toJsonString(newArrayList(1, "string", true)));
    }

    @Test
    public void testFromJsonForMap() {
        assertEquals("{}", toJsonString(emptyMap()));
        HashMap<Object, Object> object = Maps.newHashMap();
        object.put("key", "value");
        assertEquals("{\"key\":\"value\"}", toJsonString(object));
    }

    @Test
    public void testFromJsonForString() {
        assertEquals("\"string\"", toJsonString("string"));
    }

    @Test
    public void testFromJsonForInteger() {
        assertEquals("100", toJsonString(100));
    }

    @Test
    public void testFromJsonForNull() {
        assertEquals("null", toJsonString(null));
    }
}
