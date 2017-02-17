package org.slieb.soy.plugins.soyfunctions.string;

import org.junit.Test;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.io.IOException;

import static org.mozilla.javascript.ScriptableObject.READONLY;

public class MatchesSoyFunctionIntegrationTest extends SoyFunctionsIntegrationBaseTest {

    @Test
    public void shouldMatchAgainstRegex() throws Exception {
        assertMatchesFunctionEquals("true", "+", "^\\$|\\+$");
        assertMatchesFunctionEquals("true", "abab", "(a|b)+");
        assertMatchesFunctionEquals("true", "abcab", "(a|b)");
        assertMatchesFunctionEquals("false", "abcab", "^(a|b)+$");
    }

    @Test
    public void shouldMatchWithStringLiterals() throws Exception {
        assertRenderEquals("true", "templates.string.matchesWithStringLiterals", null, null);
    }

    private void assertMatchesFunctionEquals(final String expected, final String string, final String pattern) throws IOException {
        assertRenderEquals(expected, "templates.string.matches", (data) -> {
            data.put("String", string);
            data.put("Pattern", pattern);
        }, (data, runtime) -> {
            data.defineProperty("String", string, READONLY);
            data.defineProperty("Pattern", pattern, READONLY);
        });
    }
}
