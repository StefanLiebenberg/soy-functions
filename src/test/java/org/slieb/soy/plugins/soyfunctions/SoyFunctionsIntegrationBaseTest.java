package org.slieb.soy.plugins.soyfunctions;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.*;
import org.slieb.runtimes.rhino.EnvJSRuntime;
import org.slieb.runtimes.rhino.RhinoRuntime;

import java.io.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class SoyFunctionsIntegrationBaseTest {

    private static final String ROOT = "/META-INF/resources/webjars/google-closure-library/20170124.0.0";

    private SoyTofu getTofu() {
        return getFileSet().compileToTofu();
    }

    private SoyFileSet fileSet;

    private SoyFileSet getFileSet() {

        if (fileSet == null) {
            final SoyFileSet.Builder builder = getBuilder();
            final Class<?> klass = getClass();
            final SoyFileSet fileset = builder
                    .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/integration.soy"))
                    .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/style.soy"))
                    .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/date.soy"))
                    .add(klass.getResource("/org/slieb/soy/plugins/soyfunctions/templates/string.soy"))
                    .build();

            fileSet = getFileSet(fileset);
        }
        return fileSet;
    }

    private SoyFileSet getFileSet(final SoyFileSet fileset) {return fileset;}

    protected Module createModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                install(new SoyModule());
                install(new SoyFunctionsModule());
            }
        };
    }

    private Injector getInjector() {
        return Guice.createInjector(createModule());
    }

    private SoyFileSet.Builder getBuilder() {
        return getInjector()
                .getInstance(SoyFileSet.Builder.class);
    }

    protected SoyTofu.Renderer getRenderer(final String s) {
        return getTofu().newRenderer(s);
    }

    protected String render(final String templateName, final Map<String, Object> data) {
        return getRenderer(templateName).setData(data).render();
    }

    protected String renderWithJs(final String templateName, final NativeObject data) throws IOException {
        try (EnvJSRuntime envJSRuntime = getRuntime()) {
            envJSRuntime.putJavaObject("obj", data);
            return (String) envJSRuntime.execute(templateName + "(obj).getContent();");
        }
    }

    protected EnvJSRuntime getRuntime() throws IOException {
        EnvJSRuntime envJSRuntime = new EnvJSRuntime();
        envJSRuntime.initialize();
        envJSRuntime.putJavaObject("CLOSURE_IMPORT_SCRIPT", new Importer());
        loadGoodResource(envJSRuntime, "/closure/goog/base.js");
        loadResource(envJSRuntime, "/soy-2016-08-25-soyutils_usegoog.js");

        SoyJsSrcOptions jsSrcOptions = new SoyJsSrcOptions();
        jsSrcOptions.setShouldProvideRequireJsFunctions(true);

        getFileSet().compileToJsSrc(jsSrcOptions, SoyMsgBundle.EMPTY)
                .forEach((command) -> {
                    try {
                        envJSRuntime.execute(command);
                    } catch (RuntimeException evalException) {

                        try (Writer writer = new FileWriter(new File("template.js"))) {
                            writer.write(command);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        throw new RuntimeException(command, evalException);
                    }
                });

        return envJSRuntime;
    }

    private Object loadGoodResource(final EnvJSRuntime envJSRuntime, final String path) throws IOException {
        return loadResource(envJSRuntime, ROOT + path);
    }

    private Object loadResource(final EnvJSRuntime envJSRuntime, final String path) throws IOException {
        String command = IOUtils.toString(getClass().getResourceAsStream(path));
        return envJSRuntime.execute(command, path);
    }

    private final Map<String, Object> castMap = Collections.emptyMap();

    private Object toNative(Object obj) {
        if (castMap.getClass().isInstance(obj)) {
            return toNativeObjectFromMap(castMap.getClass().cast(obj));
        }

        if (obj instanceof Instant) {
            return String.valueOf(((Instant) obj).toEpochMilli());
        }

        return obj;
    }

    protected void assertRenders(String expected, String templateName, Map<String, Object> data) throws IOException {
        String tofuResult = renderTofu(templateName, data);
        String jsResult = renderJs(templateName, data);
        assertEquals(expected, tofuResult);
        assertEquals(expected, jsResult);
    }

    protected void assertRenderEquals(final String expected,
                                      final String templateNamed,
                                      final Consumer<Map<String, Object>> tofuDataConsumer,
                                      final BiConsumer<NativeObject, RhinoRuntime> jsDataConsumer) throws IOException {
        assertEquals("tofu should match", expected, renderTofu(templateNamed, getTofuData(tofuDataConsumer)));
        assertEquals("js should match", expected, renderJs(templateNamed, jsDataConsumer));
    }

    private Map<String, Object> getTofuData(final Consumer<Map<String, Object>> tofuDataConsumer) {
        Map<String, Object> tofuMap = Maps.newConcurrentMap();
        if (tofuDataConsumer != null) {
            tofuDataConsumer.accept(tofuMap);
        }
        return tofuMap;
    }

    private String renderTofu(final String templateName, final Map<String, Object> data) {
        return getRenderer(templateName)
                .setContentKind(SanitizedContent.ContentKind.TEXT)
                .setData(data)
                .render();
    }

    private String renderJs(String templateName, Map<String, Object> data) throws IOException {
        try (EnvJSRuntime runtime = getRuntime()) {
            runtime.putJavaObject("obj", toNativeObjectFromMap(data));
            return runtime.execute(String.format("%s(obj).getContent();", templateName)).toString();
        }
    }

    private String renderJs(String templateName, BiConsumer<NativeObject, RhinoRuntime> dataBuilder) throws IOException {
        try (EnvJSRuntime runtime = getRuntime()) {
            runtime.putJavaObject("obj", toNativeObjectFromMap(dataBuilder, runtime));
            return runtime.execute(String.format("%s(obj).getContent();", templateName)).toString();
        }
    }

    private Object toNativeObjectFromMap(final BiConsumer<NativeObject, RhinoRuntime> dataBuilder, final EnvJSRuntime runtime) {
        final NativeObject nativeObject = new NativeObject();
        if (dataBuilder != null) {
            dataBuilder.accept(nativeObject, runtime);
        }
        return nativeObject;
    }

    protected NativeObject toNativeObjectFromMap(Map<String, Object> map) {
        NativeObject nativeObject = new NativeObject();
        map.forEach((key, value) -> nativeObject.defineProperty(key, toNative(value), NativeObject.READONLY));
        return nativeObject;
    }

    private class Importer extends BaseFunction implements Callable {

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
                        filename + "')", "inlineStatements", 1, null);
                e.printStackTrace();
                throw new Error(e.getMessage(), e);
            }
        }
    }
}
