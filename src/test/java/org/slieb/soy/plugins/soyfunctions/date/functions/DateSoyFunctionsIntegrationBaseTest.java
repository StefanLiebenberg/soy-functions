package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.inject.Module;
import org.slieb.runtimes.rhino.EnvJSRuntime;
import org.slieb.runtimes.rhino.RhinoRuntime;
import org.slieb.soy.plugins.soyfunctions.SoyFunctionsIntegrationBaseTest;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;

import static com.google.inject.name.Names.named;
import static com.google.inject.util.Modules.override;
import static org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule.NOW_INSTANT;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.toDate;
import static org.slieb.soy.plugins.soyfunctions.date.utils.JsExprDateUtils.toDateTime;
import static org.slieb.soy.plugins.soyfunctions.utils.Expressions.expression;

abstract class DateSoyFunctionsIntegrationBaseTest extends SoyFunctionsIntegrationBaseTest {

    Instant FAKE_NOW = Instant.now();

    @Override
    protected Module createModule() {
        return override(super.createModule())
                .with((Module) binder -> binder.bind(Instant.class).annotatedWith(named(NOW_INSTANT)).toInstance(FAKE_NOW));
    }

    @Override
    protected EnvJSRuntime getRuntime() throws IOException {
        final EnvJSRuntime runtime = super.getRuntime();
        runtime.execute(String.format("Date.now = function () { return %s; }", FAKE_NOW.toEpochMilli()));
        return runtime;
    }

    Object getJSRuntimeDateFromInstant(final Instant date, final RhinoRuntime runtime) {
        return runtime.execute(toDate(expression(date.toEpochMilli())).getText());
    }

    Object getJSRuntimeDateTimeFromOffsetDataTime(final OffsetDateTime date, final RhinoRuntime runtime) {
        return runtime.execute(toDateTime(expression(date.toInstant().toEpochMilli())).getText());
    }
}
