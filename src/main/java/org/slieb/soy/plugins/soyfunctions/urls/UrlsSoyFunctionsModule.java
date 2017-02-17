package org.slieb.soy.plugins.soyfunctions.urls;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.shared.restricted.SoyFunction;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public class UrlsSoyFunctionsModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<SoyFunction> pluginBinder = newSetBinder(binder(), SoyFunction.class);
        pluginBinder.addBinding().to(ToSafeUrlSoyFunction.class);
        pluginBinder.addBinding().to(ToTrustedUriSoyFunction.class);
    }
}
