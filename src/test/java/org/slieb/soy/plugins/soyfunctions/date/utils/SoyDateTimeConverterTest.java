package org.slieb.soy.plugins.soyfunctions.date.utils;

import com.google.template.soy.data.SoyValueConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slieb.soy.plugins.soyfunctions.date.models.DateTimeSoyValue;
import org.slieb.soy.plugins.soyfunctions.date.models.InstantSoyValue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SoyDateTimeConverterTest {

    private SoyDateTimeConverter converter;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    SoyValueConverter soyValueConverter;

    @Before
    public void setUp() throws Exception {
        converter = new SoyDateTimeConverter(() -> ZoneOffset.UTC);
    }

    @Test
    public void shouldConvertInstant() throws Exception {
        final Instant instant = Instant.now();
        final InstantSoyValue expected = new InstantSoyValue(instant);
        assertEquals(expected, converter.convert(soyValueConverter, instant));
    }

    @Test
    public void shouldConvertDate() throws Exception {
        final Date date = Date.from(Instant.EPOCH);
        final InstantSoyValue expected = new InstantSoyValue(Instant.EPOCH);
        assertEquals(expected, converter.convert(soyValueConverter, date));
    }

    @Test
    public void shouldConvertOffsetDateTime() {
        final OffsetDateTime offsetDateTime = OffsetDateTime.MAX;
        final DateTimeSoyValue expected = new DateTimeSoyValue(offsetDateTime);
        assertEquals(expected, converter.convert(soyValueConverter, offsetDateTime));
    }

    @Test
    public void shouldConvertLocalDateTime() {
        final LocalDateTime localDateTime = LocalDateTime.MAX;
        final OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
        final DateTimeSoyValue expected = new DateTimeSoyValue(offsetDateTime);
        assertEquals(expected, converter.convert(soyValueConverter, localDateTime));
    }
}
