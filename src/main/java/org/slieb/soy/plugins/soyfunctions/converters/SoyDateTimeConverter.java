package org.slieb.soy.plugins.soyfunctions.converters;

import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.data.SoyValueConverter;
import com.google.template.soy.data.SoyValueProvider;
import org.slieb.soy.plugins.soyfunctions.models.SoyDateTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class SoyDateTimeConverter implements SoyCustomValueConverter {

    /**
     * Converts the given object into a corresponding SoyValue or SoyValueProvider. If this converter
     * is not intended to handle the given object, then returns null.
     *
     * @param valueConverter The converter to use for internal arbitrary object conversions (if
     *                       needed). This should be a general converter that knows how to handle all object types.
     * @param obj            The object to convert.
     * @return A provider for the converted value (often the converted value itself).
     */
    @Override
    public SoyValueProvider convert(final SoyValueConverter valueConverter, final Object obj) {

        if (obj instanceof Instant) {
            return new SoyDateTime((Instant) obj);
        }

        if (obj instanceof Date) {
            return new SoyDateTime(((Date) obj).toInstant());
        }

        if (obj instanceof LocalDateTime) {
            return new SoyDateTime(((LocalDateTime) obj).toInstant(ZoneOffset.UTC));
        }

        return null;
    }
}
