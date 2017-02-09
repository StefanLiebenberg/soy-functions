package org.slieb.soy.plugins.soyfunctions;

import com.google.inject.Guice;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.*;
import org.slieb.runtimes.rhino.EnvJSRuntime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SoyFunctionsIntegrationBaseTest {

    public static final String ROOT = "/META-INF/resources/webjars/google-closure-library/20170124.0.0";

    SoyTofu getTofu() {
        return getFileSet().compileToTofu();
    }

    SoyFileSet getFileSet() {
        final SoyFileSet.Builder builder = getBuilder();
        final Class<?> klass = getClass();
        final SoyFileSet fileset = builder
                .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/integration.soy"))
                .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/style.soy"))
                .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/date.soy"))
                .build();

        return getFileSet(fileset);
    }

    private SoyFileSet getFileSet(final SoyFileSet fileset) {return fileset;}

    private SoyFileSet.Builder getBuilder() {
        return Guice.createInjector(new SoyModule(),
                                    new SoyFunctionsModule())
                .getInstance(SoyFileSet.Builder.class);
    }

    protected SoyTofu.Renderer getRenderer(final String s) {
        return getTofu().newRenderer(s);
    }

    protected String render(final String templateName, final Map<String, Object> data) {
        return getRenderer(templateName).setData(data).render();
    }

    protected String renderWithJs(final String templateName, final NativeObject data) throws IOException {
        try (EnvJSRuntime envJSRuntime = getEnvJs()) {
            envJSRuntime.putJavaObject("obj", data);
            return (String) envJSRuntime.execute(templateName + "(obj).getContent();");
        }
    }

    protected EnvJSRuntime getEnvJs() throws IOException {
        EnvJSRuntime envJSRuntime = new EnvJSRuntime();
        envJSRuntime.initialize();
        envJSRuntime.putJavaObject("CLOSURE_IMPORT_SCRIPT", new Importer());
        loadGoodResource(envJSRuntime, "/closure/goog/base.js");
        loadResource(envJSRuntime, "/soy-2016-08-25-soyutils_usegoog.js");

        SoyJsSrcOptions jsSrcOptions = new SoyJsSrcOptions();
        jsSrcOptions.setShouldProvideRequireJsFunctions(true);

        getFileSet().compileToJsSrc(jsSrcOptions, SoyMsgBundle.EMPTY)
                .forEach(envJSRuntime::execute);

        return envJSRuntime;
    }

    private Object loadGoodResource(final EnvJSRuntime envJSRuntime, final String path) throws IOException {
        return loadResource(envJSRuntime, ROOT + path);
    }

    private Object loadResource(final EnvJSRuntime envJSRuntime, final String path) throws IOException {
        String command = IOUtils.toString(getClass().getResourceAsStream(path));
        return envJSRuntime.execute(command, path);
    }

    protected Object toNative(Object obj) {
        if (obj instanceof Map) {
            return toNativeObjectFromMap((Map<String, Object>) obj);
        }

        if (obj instanceof Instant) {
            return String.valueOf(((Instant) obj).toEpochMilli());
        }

        return obj;
    }

    protected NativeObject toNativeObjectFromMap(Map<String, Object> map) {
        NativeObject nativeObject = new NativeObject();
        map.forEach((key, value) -> nativeObject.defineProperty(key, toNative(value), NativeObject.READONLY));
        return nativeObject;
    }

    protected void assertRenders(String expected, String templateName, Map<String, Object> data) throws IOException {
        String tofuResult = renderTofu(templateName, data);
        String jsResult = renderJs(templateName, data);
        assertEquals(expected, tofuResult);
        assertEquals(expected, jsResult);
    }

    private String renderTofu(final String templateName, final Map<String, Object> data) {
        return getRenderer(templateName)
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    private String renderJs(String templateName, Map<String, Object> data) throws IOException {
        try (EnvJSRuntime runtime = getEnvJs()) {
            runtime.putJavaObject("obj", toNativeObjectFromMap(data));
            return runtime.execute(String.format("%s(obj).getContent();", templateName)).toString();
        }
    }

    class Importer extends BaseFunction implements Callable {

        @Override
        public Object call(final Context cx, final Scriptable scope, final Scriptable thisObj, final Object[] args) {
            String filename = args[0].toString();
            String path = ROOT + "/closure/goog/" + filename;
            try (InputStreamReader reader = new InputStreamReader(
                    getClass().getResourceAsStream(path))) {
                cx.evaluateReader(scope, reader, filename, 0, null);
                return true;
            } catch (IOException e) {
                cx.evaluateString(scope, "console.warn('Error while importing " +
                        filename + "')", "inline", 1, null);
                e.printStackTrace();
                throw new Error(e.getMessage(), e);
            }
        }
    }
}
