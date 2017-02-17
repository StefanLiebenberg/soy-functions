package org.slieb.soy.plugins.soyfunctions.internal;

import com.google.common.html.types.SafeScripts;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.template.soy.data.*;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.data.restricted.NumberData;
import com.google.template.soy.data.restricted.StringData;

import java.util.Iterator;

import static com.google.template.soy.data.SanitizedContent.ContentKind.JS;
import static com.google.template.soy.data.SanitizedContents.fromSafeScript;
import static com.google.template.soy.data.UnsafeSanitizedContentOrdainer.ordainAsSafe;

public class SoyJsonUtils {

    private final Gson gson;

    private final SoyValueHelper helper;

    @Inject
    public SoyJsonUtils(final Gson gson, final SoyValueHelper helper) {
        this.gson = gson;
        this.helper = helper;
    }

    public SanitizedContent toJsonFromObject(Object object) {
        return toJson(helper.convert(object).resolve());
    }

    public SanitizedContent toJson(SoyValue soyValue) {

        if (soyValue instanceof StringData) {
            return ordainAsSafe("\"" + soyValue.stringValue() + "\"", JS);
        }

        if (soyValue instanceof NumberData || soyValue instanceof BooleanData) {
            return ordainAsSafe(soyValue.toString(), JS);
        }

        if (soyValue instanceof SoyDict) {
            return soyDictToJson((SoyDict) soyValue);
        }
        if (soyValue instanceof SoyList) {
            return soyListToJson((SoyList) soyValue);
        }

        return fromSafeScript(SafeScripts.fromConstant("null"));
    }

    public SoyValue fromString(String jsonString) {
        return helper.convert(gson.fromJson(jsonString, Object.class)).resolve();
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

    private SanitizedContent soyDictToJson(SoyDict dict) {
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
