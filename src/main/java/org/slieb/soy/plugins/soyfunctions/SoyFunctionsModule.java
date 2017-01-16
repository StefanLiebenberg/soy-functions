package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.shared.restricted.SoyFunction;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public class SoyFunctionsModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<SoyFunction> pluginBinder = newSetBinder(binder(), SoyFunction.class);
        pluginBinder.addBinding().to(ToJsonSoyFunction.class);
        pluginBinder.addBinding().to(ParseJsonSoyFunction.class);
        pluginBinder.addBinding().to(ToLowerCaseSoyFunction.class);
        pluginBinder.addBinding().to(ToUpperCaseSoyFunction.class);
        pluginBinder.addBinding().to(StringLengthSoyFunction.class);
        pluginBinder.addBinding().to(PrintDateSoyFunction.class);
        pluginBinder.addBinding().to(ParseIntegerSoyFunction.class);
        pluginBinder.addBinding().to(ParseFloatSoyFunction.class);
    }
}
