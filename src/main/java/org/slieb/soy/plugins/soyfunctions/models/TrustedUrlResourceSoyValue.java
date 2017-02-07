package org.slieb.soy.plugins.soyfunctions.models;

import com.google.common.html.types.TrustedResourceUrl;
import com.google.template.soy.data.SoyAbstractValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

public class TrustedUrlResourceSoyValue extends SoyAbstractValue implements ImportableNode {

    private final TrustedResourceUrl trustedResourceUrl;

    public TrustedUrlResourceSoyValue(final TrustedResourceUrl trustedResourceUrl) {this.trustedResourceUrl = trustedResourceUrl;}

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
        return trustedResourceUrl.getTrustedResourceUrlString();
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
        appendable.append(trustedResourceUrl.getTrustedResourceUrlString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final TrustedUrlResourceSoyValue that = (TrustedUrlResourceSoyValue) o;
        return Objects.equals(trustedResourceUrl, that.trustedResourceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trustedResourceUrl);
    }

    @Override
    public TrustedResourceUrl getImportResourceUrl() {
        return trustedResourceUrl;
    }
}
