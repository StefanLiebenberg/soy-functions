package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.shared.restricted.SoyFunction;
import org.slieb.soy.plugins.soyfunctions.converters.SoyDateTimeConverter;
import org.slieb.soy.plugins.soyfunctions.extra.PrintDateSoyFunction;
import org.slieb.soy.plugins.soyfunctions.extra.ToDateTimeSoyFunction;
import org.slieb.soy.plugins.soyfunctions.extra.ToSafeUrlSoyFunction;
import org.slieb.soy.plugins.soyfunctions.html.HtmlSoyFunction;
import org.slieb.soy.plugins.soyfunctions.html.ScriptSoyFunction;
import org.slieb.soy.plugins.soyfunctions.html.StyleSoyFunction;
import org.slieb.soy.plugins.soyfunctions.string.*;

import java.util.List;
import java.util.Set;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction.placeHolderSoyFunction;

@SuppressWarnings("WeakerAccess")
public class SoyFunctionsModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<SoyFunction> pluginBinder = newSetBinder(binder(), SoyFunction.class);
        pluginBinder.addBinding().to(ToJsonSoyFunction.class);
        pluginBinder.addBinding().to(ParseJsonSoyFunction.class);
        pluginBinder.addBinding().to(ToLowerCaseSoyFunction.class);
        pluginBinder.addBinding().to(StartsWithSoyFunction.class);
        pluginBinder.addBinding().to(EndsWithSoyFunction.class);
        pluginBinder.addBinding().to(ToUpperCaseSoyFunction.class);
        pluginBinder.addBinding().to(StringLengthSoyFunction.class);
        pluginBinder.addBinding().to(PrintDateSoyFunction.class);
        pluginBinder.addBinding().to(ToDateTimeSoyFunction.class);
        pluginBinder.addBinding().to(ParseIntegerSoyFunction.class);
        pluginBinder.addBinding().to(ParseFloatSoyFunction.class);
        pluginBinder.addBinding().to(SplitSoyFunction.class);
        pluginBinder.addBinding().to(JoinSoyFunction.class);
        pluginBinder.addBinding().to(TrimSoyFunction.class);
        pluginBinder.addBinding().to(SubstringSoyFunction.class);
        pluginBinder.addBinding().to(CapitalizeSoyFunction.class);
        pluginBinder.addBinding().to(ToFixedSoyFunction.class);
        pluginBinder.addBinding().to(StyleSoyFunction.class);
        pluginBinder.addBinding().to(ScriptSoyFunction.class);
        pluginBinder.addBinding().to(ToSafeUrlSoyFunction.class);
        pluginBinder.addBinding().to(ToTrustedUriSoyFunction.class);
        pluginBinder.addBinding().to(HtmlSoyFunction.class);
        pluginBinder.addBinding().toInstance(placeHolderSoyFunction("toInstant", singleton(1)));

        final Multibinder<SoyCustomValueConverter> converters = newSetBinder(binder(), SoyCustomValueConverter.class);
        converters.addBinding().to(SoyDateTimeConverter.class);
    }

    @Provides
    public List<SoyCustomValueConverter> customValueConverters(Set<SoyCustomValueConverter> customValueConverters) {
        return customValueConverters.stream().collect(toList());
    }
}
