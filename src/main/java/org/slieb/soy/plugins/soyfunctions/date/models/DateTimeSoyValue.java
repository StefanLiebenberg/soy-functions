package org.slieb.soy.plugins.soyfunctions.date.models;

import com.google.template.soy.data.SoyAbstractValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

public class DateTimeSoyValue extends SoyAbstractValue implements SoyDate {

    private final java.time.OffsetDateTime dateTime;

    public DateTimeSoyValue(final OffsetDateTime dateTime) {this.dateTime = dateTime;}

    @Override
    public boolean coerceToBoolean() {
        return true;
    }

    @Override
    public String coerceToString() {
        return dateTime.toString();
    }

    @Override
    public void render(@Nonnull final Appendable appendable) throws IOException {
        appendable.append(coerceToString());
    }

    @Override
    public Instant getInstant() {
        return dateTime.toInstant();
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final DateTimeSoyValue that = (DateTimeSoyValue) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}
