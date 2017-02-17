package org.slieb.soy.plugins.soyfunctions.date;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.shared.restricted.SoyFunction;
import org.slieb.soy.plugins.soyfunctions.date.functions.*;
import org.slieb.soy.plugins.soyfunctions.date.utils.SoyDateTimeConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public class SoyDateFunctionsModule extends AbstractModule {

    public static final String DEFAULT_OFFSET = "defaultOffset";

    public static final String NOW_INSTANT = "nowInstant";

    @Override
    protected void configure() {
        final Multibinder<SoyCustomValueConverter> converters = newSetBinder(binder(), SoyCustomValueConverter.class);
        converters.addBinding().to(SoyDateTimeConverter.class);

        final Multibinder<SoyFunction> pluginBinder = newSetBinder(binder(), SoyFunction.class);
        pluginBinder.addBinding().to(IsBeforeSoyFunction.class);
        pluginBinder.addBinding().to(IsAfterSoyFunction.class);
        pluginBinder.addBinding().to(PrintDateSoyFunction.class);
        pluginBinder.addBinding().to(ToDateTimeSoyFunction.class);
        pluginBinder.addBinding().to(ToInstantSoyFunction.class);
        pluginBinder.addBinding().to(ZoneOffsetSoyFunction.class);
        pluginBinder.addBinding().to(PrintTimestampSoyFunction.class);
    }

    @Provides
    @Named(DEFAULT_OFFSET)
    public ZoneOffset defaultOffset(@Named(NOW_INSTANT) Instant now) {
        return ZoneId.systemDefault().getRules().getOffset(now);
    }

    @Provides
    @Named(NOW_INSTANT)
    public Instant nowInstant() {
        throw new RuntimeException("should be faked?!");
        //        return Instant.now();
    }
}
