package org.slieb.soy.plugins.soyfunctions.date.functions;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.jssrc.restricted.JsExpr;
import org.slieb.soy.plugins.soyfunctions.date.models.ZoneOffsetSoyValue;
import org.slieb.soy.plugins.soyfunctions.internal.AbstractSoyFunction;

import java.time.ZoneOffset;
import java.util.List;

import static org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule.DEFAULT_OFFSET;

public class ZoneOffsetSoyFunction extends AbstractSoyFunction<ZoneOffsetSoyValue> {

    private final Provider<ZoneOffset> defaultZoneOffsetProvider;

    @Inject
    public ZoneOffsetSoyFunction(@Named(DEFAULT_OFFSET) final Provider<ZoneOffset> defaultZoneOffsetProvider) {
        super("zoneOffset", Sets.newHashSet(0, 1));
        this.defaultZoneOffsetProvider = defaultZoneOffsetProvider;
    }

    @Override
    public JsExpr computeForJsSrc(final List<JsExpr> list) {
        return null;
    }

    @Override
    public ZoneOffsetSoyValue computeForJava(final List<SoyValue> list) {
        return null;
    }
}
