package org.slieb.soy.plugins.soyfunctions.date.models;

import com.google.template.soy.data.SoyAbstractValue;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneOffsetSoyValue extends SoyAbstractValue {

    private final ZoneOffset zoneOffset;

    public ZoneOffsetSoyValue(final ZoneOffset zoneOffset) {this.zoneOffset = zoneOffset;}

    @Override
    public long longValue() {
        return zoneOffset.getTotalSeconds();
    }

    @Override
    public double numberValue() {
        return zoneOffset.getTotalSeconds();
    }

    @Override
    public int integerValue() {
        return zoneOffset.getTotalSeconds();
    }

    @Override
    public boolean coerceToBoolean() {
        return true;
    }

    @Override
    public String coerceToString() {
        return zoneOffset.toString();
    }

    @Override
    public void render(final Appendable appendable) throws IOException {
        appendable.append(coerceToString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final ZoneOffsetSoyValue that = (ZoneOffsetSoyValue) o;
        return Objects.equals(zoneOffset, that.zoneOffset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneOffset);
    }
}
