package org.slieb.soy.plugins.soyfunctions.converters;

import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.data.SoyValueConverter;
import com.google.template.soy.data.SoyValueProvider;
import org.slieb.soy.plugins.soyfunctions.models.InstantSoyValue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class SoyDateTimeConverter implements SoyCustomValueConverter {

    @Override
    public SoyValueProvider convert(final SoyValueConverter valueConverter, final Object obj) {

        if (obj instanceof Instant) {
            return new InstantSoyValue((Instant) obj);
        }

        if (obj instanceof Date) {
            return new InstantSoyValue(((Date) obj).toInstant());
        }

        if (obj instanceof LocalDateTime) {
            return new InstantSoyValue(((LocalDateTime) obj).toInstant(ZoneOffset.UTC));
        }

        return null;
    }
}
