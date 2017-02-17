package org.slieb.soy.plugins.soyfunctions.date.functions;

import org.junit.Test;

import java.time.Instant;

import static org.mozilla.javascript.ScriptableObject.READONLY;

public class ToInstantSoyFunctionIntegrationTest extends DateSoyFunctionsIntegrationBaseTest {

    @Test
    public void shouldRenderToInstantWithNow() throws Exception {
        assertRenderEquals(String.valueOf(FAKE_NOW.toEpochMilli()), "templates.date.toInstant",
                           (data) -> { },
                           (data, runtime) -> {});
    }

    @Test
    public void shouldRenderInstantWithIntegerTimestamp() throws Exception {
        Instant plusMinutes = FAKE_NOW.plusSeconds(1000);
        assertRenderEquals(String.valueOf(plusMinutes.toEpochMilli()), "templates.date.toInstantWithTimestamp",
                           (data) -> data.put("Timestamp", plusMinutes.toEpochMilli()),
                           (data, runtime) -> data.defineProperty("Timestamp", plusMinutes.toEpochMilli(), READONLY));
    }

    @Test
    public void shouldRenderInstantWithStringTimestamp() throws Exception {
        Instant plusMinutes = FAKE_NOW.plusSeconds(1000);
        assertRenderEquals(String.valueOf(plusMinutes.toEpochMilli()), "templates.date.toInstantWithTimestamp",
                           (data) -> data.put("Timestamp", String.valueOf(plusMinutes.toEpochMilli())),
                           (data, runtime) -> data.defineProperty("Timestamp", String.valueOf(plusMinutes.toEpochMilli()), READONLY));
    }
}
