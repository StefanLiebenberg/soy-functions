package org.slieb.soy.plugins.soyfunctions.models;

import com.google.template.soy.data.SoyAbstractValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class SoyDateTime extends SoyAbstractValue {

    public Instant getInstant() {
        return instant;
    }

    private final Instant instant;

    public SoyDateTime(final Instant instant) {this.instant = instant;}

    /**
     * Coerces this value into a boolean.
     *
     * @return This value coerced into a boolean.
     */
    @Override
    public boolean coerceToBoolean() {
        return true;
    }

    /**
     * Coerces this value into a string.
     *
     * @return This value coerced into a string.
     */
    @Override
    public String coerceToString() {
        return String.valueOf(instant.toEpochMilli());
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
        final SoyDateTime that = (SoyDateTime) o;
        return Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
