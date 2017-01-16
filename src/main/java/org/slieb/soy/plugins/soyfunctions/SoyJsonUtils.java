package org.slieb.soy.plugins.soyfunctions;

import com.google.common.html.types.SafeScripts;
import com.google.gson.Gson;
import com.google.template.soy.data.*;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.data.restricted.NumberData;
import com.google.template.soy.data.restricted.StringData;

import java.util.Iterator;

import static com.google.template.soy.data.SanitizedContent.ContentKind.JS;
import static com.google.template.soy.data.SanitizedContents.fromSafeScript;

public class SoyJsonUtils {

    public SanitizedContent toJson(SoyValue soyValue) {

        if (soyValue instanceof StringData) {
            return UnsafeSanitizedContentOrdainer.ordainAsSafe("\"" + soyValue.stringValue() + "\"", JS);
        }

        if (soyValue instanceof NumberData || soyValue instanceof BooleanData) {
            return UnsafeSanitizedContentOrdainer.ordainAsSafe(soyValue.toString(), JS);
        }

        if (soyValue instanceof SoyDict) {
            return soyDictToJson((SoyDict) soyValue);
        }
        if (soyValue instanceof SoyList) {
            return soyListToJson((SoyList) soyValue);
        }

        return fromSafeScript(SafeScripts.fromConstant("null"));
    }

    @SuppressWarnings("unchecked")
    public SoyValue fromString(String jsonString) {
        return SoyData.createFromExistingData(new Gson().fromJson(jsonString, Object.class));
    }

    private SanitizedContent soyListToJson(final SoyList soyValue) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        final Iterator<? extends SoyValue> iterator = soyValue.asResolvedJavaList().iterator();
        while (iterator.hasNext()) {
            SoyValue value = iterator.next();
            stringBuilder.append(toJson(value).getContent());
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        return fromSafeScript(SafeScripts.fromConstant(stringBuilder.toString()));
    }

    public SanitizedContent soyDictToJson(SoyDict dict) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        final Iterable<? extends SoyValue> itemKeys = dict.getItemKeys();
        final Iterator<? extends SoyValue> iterator = itemKeys.iterator();
        while (iterator.hasNext()) {
            SoyValue key = iterator.next();
            stringBuilder.append("\"").append(key.coerceToString()).append("\":")
                         .append(toJson(dict.getItem(key)).getContent());
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return fromSafeScript(SafeScripts.fromConstant(stringBuilder.toString()));
    }
}
