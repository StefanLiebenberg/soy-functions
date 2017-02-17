package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.template.soy.data.SoyCustomValueConverter;

import java.util.List;
import java.util.Set;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static java.util.stream.Collectors.toList;

/**
 * This module introduces a multi-binding for {link SoyCustomValueConverter}.
 */
@SuppressWarnings("WeakerAccess")
public class CustomValueConvertersModule extends AbstractModule {

    @Override
    protected void configure() {
        newSetBinder(binder(), SoyCustomValueConverter.class);
    }

    @Provides
    public List<SoyCustomValueConverter> customValueConverters(Set<SoyCustomValueConverter> customValueConverters) {
        return customValueConverters.stream().collect(toList());
    }
}
