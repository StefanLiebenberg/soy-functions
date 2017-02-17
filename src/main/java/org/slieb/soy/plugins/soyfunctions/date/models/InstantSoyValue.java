package org.slieb.soy.plugins.soyfunctions.date.models;

import com.google.template.soy.data.SoyAbstractValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents a DateTime instant in soy.
 */
public class InstantSoyValue extends SoyAbstractValue implements SoyDate {

    private final Instant instant;

    public InstantSoyValue(@Nonnull final Instant instant) {
        this.instant = checkNotNull(instant);
    }

    @SuppressWarnings("WeakerAccess")
    @Nonnull
    public Instant getInstant() {
        return instant;
    }

    @Override
    public boolean coerceToBoolean() {
        return true;
    }

    @Override
    public String coerceToString() {
        return String.valueOf(instant.toEpochMilli());
    }

    @Override
    public void render(@Nonnull final Appendable appendable) throws IOException {
        appendable.append(coerceToString());
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final InstantSoyValue that = (InstantSoyValue) o;
        return Objects.equals(instant, that.instant);
    }

    @Override
    public long longValue() {
        return instant.toEpochMilli();
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
