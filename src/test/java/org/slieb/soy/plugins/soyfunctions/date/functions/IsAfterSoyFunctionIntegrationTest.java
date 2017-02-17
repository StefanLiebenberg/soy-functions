package org.slieb.soy.plugins.soyfunctions.date.functions;

import org.junit.Test;
import org.mozilla.javascript.NativeObject;
import org.slieb.runtimes.rhino.RhinoRuntime;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static org.mozilla.javascript.ScriptableObject.READONLY;

public class IsAfterSoyFunctionIntegrationTest extends DateSoyFunctionsIntegrationBaseTest {

    @Test
    public void shouldCalculateIsAfterForInstant() throws Exception {
        final Instant date = Instant.now();
        assertIsAfterRendersEqualWithInstant("true", date, date.minusSeconds(1));
        assertIsAfterRendersEqualWithInstant("false", date, date);
        assertIsAfterRendersEqualWithInstant("false", date, date.plusSeconds(1));
    }

    @Test
    public void shouldCalculateIsAfterForDateTime() throws Exception {
        final OffsetDateTime now = Instant.now().atOffset(ZoneOffset.UTC);
        assertIsAfterRendersEqualWithDateTime("true", now, now.minusSeconds(1));
        assertIsAfterRendersEqualWithDateTime("false", now, now);
        assertIsAfterRendersEqualWithDateTime("false", now, now.plusSeconds(1));
    }

    private void assertIsAfterRendersEqualWithInstant(String expected, Instant date, Instant compareTo) throws IOException {
        assertIsAfterRendersEqual(expected, date, compareTo, this::getJSRuntimeDateFromInstant);
    }

    private void assertIsAfterRendersEqualWithDateTime(String expected, OffsetDateTime date, OffsetDateTime compareTo) throws IOException {
        assertIsAfterRendersEqual(expected, date, compareTo, this::getJSRuntimeDateTimeFromOffsetDataTime);
    }

    private <T> void assertIsAfterRendersEqual(String expected, T date, T compareTo, BiFunction<T, RhinoRuntime, Object> jsFunction) throws IOException {
        assertRenderEquals(expected, "templates.date.isAfter",
                           isAfterFunctionTofuDataPopulate(date, compareTo),
                           isAfterFunctionJSRuntimeDataPopulate(date, compareTo, jsFunction));
    }

    private <T> Consumer<Map<String, T>> isAfterFunctionTofuDataPopulate(T date, T compareTo) {
        return (Map<String, T> data) -> {
            data.put("Date", date);
            data.put("CompareTo", compareTo);
        };
    }

    private <T> BiConsumer<NativeObject, RhinoRuntime> isAfterFunctionJSRuntimeDataPopulate(final T date,
                                                                                            final T compareTo,
                                                                                            final BiFunction<T, RhinoRuntime, Object> toJSRuntimeObject) {
        return (data, runtime) -> {
            data.defineProperty("Date", toJSRuntimeObject.apply(date, runtime), READONLY);
            data.defineProperty("CompareTo", toJSRuntimeObject.apply(compareTo, runtime), READONLY);
        };
    }
}
