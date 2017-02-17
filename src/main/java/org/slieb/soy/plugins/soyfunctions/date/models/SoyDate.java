package org.slieb.soy.plugins.soyfunctions.date.models;

import com.google.template.soy.data.SoyValue;

import java.time.Instant;

public interface SoyDate extends SoyValue {

    public Instant getInstant();
}
