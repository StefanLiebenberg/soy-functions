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

public class IsBeforeSoyFunctionIntegrationTest extends DateSoyFunctionsIntegrationBaseTest {

    @Test
    public void shouldCalculateIsBeforeForInstant() throws Exception {
        final Instant date = Instant.now();
        assertIsBeforeRendersEqualWithInstant("false", date, date.minusSeconds(1));
        assertIsBeforeRendersEqualWithInstant("false", date, date);
        assertIsBeforeRendersEqualWithInstant("true", date, date.plusSeconds(1));
    }

    @Test
    public void shouldCalculateIsBeforeForInstantEpoch() throws Exception {
        final Instant date = Instant.now();
        assertIsBeforeRendersEqualWithInstantEpoch("false", date, date.minusSeconds(1));
        assertIsBeforeRendersEqualWithInstantEpoch("false", date, date);
        assertIsBeforeRendersEqualWithInstantEpoch("true", date, date.plusSeconds(1));
    }

    @Test
    public void shouldCalculateIsBeforeForDateTime() throws Exception {
        final OffsetDateTime now = Instant.now().atOffset(ZoneOffset.UTC);
        assertIsBeforeRendersEqualWithDateTime("false", now, now.minusSeconds(1));
        assertIsBeforeRendersEqualWithDateTime("false", now, now);
        assertIsBeforeRendersEqualWithDateTime("true", now, now.plusSeconds(1));
    }

    private void assertIsBeforeRendersEqualWithInstant(String expected, Instant date, Instant compareTo) throws IOException {
        assertIsBeforeRendersEqual(expected, date, compareTo, this::getJSRuntimeDateFromInstant);
    }

    private void assertIsBeforeRendersEqualWithInstantEpoch(String expected, Instant date, Instant compareTo) throws IOException {
        assertIsBeforeRendersEqual(expected, date.toEpochMilli(), compareTo.toEpochMilli(), (obj, runtime) -> obj);
    }

    private void assertIsBeforeRendersEqualWithDateTime(String expected, OffsetDateTime date, OffsetDateTime compareTo) throws IOException {
        assertIsBeforeRendersEqual(expected, date, compareTo, this::getJSRuntimeDateTimeFromOffsetDataTime);
    }

    private <T> void assertIsBeforeRendersEqual(String expected, T date, T compareTo, BiFunction<T, RhinoRuntime, Object> jsFunction) throws IOException {
        assertRenderEquals(expected, "templates.date.isBefore",
                           isBeforeFunctionTofuDataPopulate(date, compareTo),
                           isBeforeFunctionJSRuntimeDataPopulate(date, compareTo, jsFunction));
    }

    private <T> Consumer<Map<String, T>> isBeforeFunctionTofuDataPopulate(T date, T compareTo) {
        return (Map<String, T> data) -> {
            data.put("Date", date);
            data.put("CompareTo", compareTo);
        };
    }

    private <T> BiConsumer<NativeObject, RhinoRuntime> isBeforeFunctionJSRuntimeDataPopulate(final T date,
                                                                                             final T compareTo,
                                                                                             final BiFunction<T, RhinoRuntime, Object> toJSRuntimeObject) {
        return (data, runtime) -> {
            data.defineProperty("Date", toJSRuntimeObject.apply(date, runtime), READONLY);
            data.defineProperty("CompareTo", toJSRuntimeObject.apply(compareTo, runtime), READONLY);
        };
    }
}
