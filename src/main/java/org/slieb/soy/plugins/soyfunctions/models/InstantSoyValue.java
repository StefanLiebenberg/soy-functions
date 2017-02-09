package org.slieb.soy.plugins.soyfunctions.models;

import com.google.template.soy.data.SoyAbstractValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class InstantSoyValue extends SoyAbstractValue {

    public Instant getInstant() {
        return instant;
    }

    private final Instant instant;

    public InstantSoyValue(final Instant instant) {this.instant = instant;}

    @Override
    public boolean coerceToBoolean() {
        return true;
    }

    @Override
    public String coerceToString() {
        return String.valueOf(instant.toEpochMilli());
    }

    @Override
    public long longValue() {
        return instant.toEpochMilli();
    }

    /**
     * Renders this value to the given appendable.
     * <p>
     * <p>This should behave identically to {@code appendable.append(coerceToString())} but is
     * provided separately to allow more incremental approaches.
     *
     * @param appendable The appendable to render to.
     * @throws IOException
     */
    @Override
    public void render(@Nonnull final Appendable appendable) throws IOException {
        appendable.append(coerceToString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final InstantSoyValue that = (InstantSoyValue) o;
        return Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
