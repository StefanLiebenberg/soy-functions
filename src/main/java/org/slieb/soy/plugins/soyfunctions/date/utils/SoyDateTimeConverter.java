package org.slieb.soy.plugins.soyfunctions.date.utils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.data.SoyValueConverter;
import com.google.template.soy.data.SoyValueProvider;
import org.slieb.soy.plugins.soyfunctions.date.SoyDateFunctionsModule;
import org.slieb.soy.plugins.soyfunctions.date.models.DateTimeSoyValue;
import org.slieb.soy.plugins.soyfunctions.date.models.InstantSoyValue;

import java.time.*;
import java.util.Date;

public class SoyDateTimeConverter implements SoyCustomValueConverter {

    private final Provider<ZoneOffset> defaultOffset;

    @Inject
    public SoyDateTimeConverter(@Named(SoyDateFunctionsModule.DEFAULT_OFFSET) final Provider<ZoneOffset> defaultOffset) {
        this.defaultOffset = defaultOffset;
    }

    @Override
    public SoyValueProvider convert(final SoyValueConverter valueConverter, final Object obj) {

        if (obj instanceof Instant) {
            return new InstantSoyValue((Instant) obj);
        }

        if (obj instanceof Date) {
            return new InstantSoyValue(((Date) obj).toInstant());
        }

        if (obj instanceof LocalDate) {
            return dateTimeSoyValue(((LocalDate) obj));
        }

        if (obj instanceof OffsetDateTime) {
            return dateTimeSoyValue((OffsetDateTime) obj);
        }

        if (obj instanceof ZonedDateTime) {
            return dateTimeSoyValue((ZonedDateTime) obj);
        }

        if (obj instanceof LocalDateTime) {
            return dateTimeSoyValue((LocalDateTime) obj);
        }

        return null;
    }

    private SoyValueProvider dateTimeSoyValue(final LocalDate obj) {
        return dateTimeSoyValue(obj.atStartOfDay(defaultOffset.get()));
    }

    private DateTimeSoyValue dateTimeSoyValue(final ZonedDateTime zonedDateTime) {
        return dateTimeSoyValue(zonedDateTime.toOffsetDateTime());
    }

    private DateTimeSoyValue dateTimeSoyValue(final LocalDateTime localDateTime) {
        return dateTimeSoyValue(localDateTime.atOffset(defaultOffset.get()));
    }

    private DateTimeSoyValue dateTimeSoyValue(final OffsetDateTime dateTime) {
        return new DateTimeSoyValue(dateTime);
    }
}
