package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.shared.restricted.SoyFunction;
import org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule;
import org.slieb.soy.plugins.soyfunctions.html.HtmlSoyFunction;
import org.slieb.soy.plugins.soyfunctions.html.ScriptSoyFunction;
import org.slieb.soy.plugins.soyfunctions.html.StyleSoyFunction;
import org.slieb.soy.plugins.soyfunctions.string.StringsSoyFunctionsModule;
import org.slieb.soy.plugins.soyfunctions.urls.UrlsSoyFunctionsModule;

import java.util.List;
import java.util.Set;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("WeakerAccess")
public class SoyFunctionsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new SoyDateFunctionsModule());
        install(new UrlsSoyFunctionsModule());
        install(new StringsSoyFunctionsModule());

        final Multibinder<SoyFunction> pluginBinder = newSetBinder(binder(), SoyFunction.class);
        pluginBinder.addBinding().to(StyleSoyFunction.class);
        pluginBinder.addBinding().to(ScriptSoyFunction.class);
        pluginBinder.addBinding().to(HtmlSoyFunction.class);

        newSetBinder(binder(), SoyCustomValueConverter.class);
    }

    @Provides
    public List<SoyCustomValueConverter> customValueConverters(Set<SoyCustomValueConverter> customValueConverters) {
        return customValueConverters.stream().collect(toList());
    }
}
