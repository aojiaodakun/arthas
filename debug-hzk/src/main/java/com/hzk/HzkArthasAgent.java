package com.hzk;

import com.taobao.arthas.core.server.ArthasBootstrap;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.arthas.SpyAPI;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author hengyunabc 2020-06-22
 *
 */
public class HzkArthasAgent {
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    private static final String ARTHAS_CORE_JAR = "arthas-core.jar";
    private static final String ARTHAS_BOOTSTRAP = "com.taobao.arthas.core.server.ArthasBootstrap";
    private static final String GET_INSTANCE = "getInstance";
    private static final String IS_BIND = "isBind";
    private static Class<?> bootstrapClass;
    private static Object bootstrapObject;

    private String errorMessage;

    private Map<String, String> configMap = new HashMap<String, String>();
    private String arthasHome;
    private boolean slientInit;
    private Instrumentation instrumentation;

    public HzkArthasAgent() {
        this(null, null, false, null);
    }

    public HzkArthasAgent(Map<String, String> configMap) {
        this(configMap, null, false, null);
    }

    public HzkArthasAgent(String arthasHome) {
        this(null, arthasHome, false, null);
    }

    public HzkArthasAgent(Map<String, String> configMap, String arthasHome, boolean slientInit,
                          Instrumentation instrumentation) {
        if (configMap != null) {
            this.configMap = configMap;
        }

        this.arthasHome = arthasHome;
        this.slientInit = slientInit;
        this.instrumentation = instrumentation;
    }

    public static void attach() {
        new HzkArthasAgent().init();
    }

    /**
     * @see https://arthas.aliyun.com/doc/arthas-properties.html
     * @param configMap
     */
    public static void attach(Map<String, String> configMap) {
        new HzkArthasAgent(configMap).init();
    }

    /**
     * use the specified arthas
     * @param arthasHome arthas directory
     */
    public static void attach(String arthasHome) {
        new HzkArthasAgent().init();
    }

    public void init() throws IllegalStateException {
        // 尝试判断arthas是否已在运行，如果是的话，直接就退出
        try {
            Class.forName("java.arthas.SpyAPI"); // 加载不到会抛异常
            if (SpyAPI.isInited()) {
                return;
            }
        } catch (Throwable e) {
            // ignore
        }

        try {
            if (instrumentation == null) {
                instrumentation = ByteBuddyAgent.install();
            }
            // TODO，本地源码调试
            HzkAttachArthasClassloader arthasClassLoader = new HzkAttachArthasClassloader(
                    new URL[] {
                            new URL("file:/D:/source-project/arthas/debug-hzk/target/classes/"),
                            new URL("file:/D:/source-project/arthas/core/target/classes/"),
                            new URL("file:/D:/source-project/arthas/common/target/classes/"),
                            new URL("file:/D:/source-project/arthas/arthas-vmtool/target/classes/"),
                            new URL("file:/D:/source-project/arthas/memorycompiler/target/classes/"),
                            new URL("file:/D:/source-project/arthas/tunnel-client/target/classes/"),
                            new URL("file:/D:/source-project/arthas/tunnel-common/target/classes/"),
                            new URL("file:/D:/repo/com/alibaba/bytekit-core/0.0.8/bytekit-core-0.0.8.jar"),
                            new URL("file:/D:/repo/com/alibaba/bytekit-instrument-api/0.0.8/bytekit-instrument-api-0.0.8.jar"),
                            new URL("file:/D:/repo/com/alibaba/repackage-asm/0.0.13/repackage-asm-0.0.13.jar"),
                            new URL("file:/D:/repo/net/bytebuddy/byte-buddy-agent/1.11.6/byte-buddy-agent-1.11.6.jar"),
                            new URL("file:/D:/repo/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar"),
                            new URL("file:/D:/repo/io/netty/netty-common/4.1.92.Final/netty-common-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-buffer/4.1.92.Final/netty-buffer-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-handler/4.1.92.Final/netty-handler-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-resolver/4.1.92.Final/netty-resolver-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-transport-native-unix-common/4.1.92.Final/netty-transport-native-unix-common-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-codec/4.1.92.Final/netty-codec-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-transport/4.1.92.Final/netty-transport-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/io/netty/netty-codec-http/4.1.92.Final/netty-codec-http-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/com/alibaba/middleware/termd-core/1.1.7.12/termd-core-1.1.7.12.jar"),
                            new URL("file:/D:/repo/io/netty/netty-common/4.1.92.Final/netty-common-4.1.92.Final.jar"),
                            new URL("file:/D:/repo/com/fasterxml/jackson/core/jackson-databind/2.7.9.4/jackson-databind-2.7.9.4.jar"),
                            new URL("file:/D:/repo/com/fasterxml/jackson/core/jackson-annotations/2.7.0/jackson-annotations-2.7.0.jar"),
                            new URL("file:/D:/repo/com/fasterxml/jackson/core/jackson-core/2.7.9/jackson-core-2.7.9.jar"),
                            new URL("file:/D:/repo/com/alibaba/middleware/cli/1.0.4/cli-1.0.4.jar"),
                            new URL("file:/D:/repo/com/taobao/text/text-ui/0.0.3/text-ui-0.0.3.jar"),
                            new URL("file:/D:/repo/com/fifesoft/rsyntaxtextarea/2.5.8/rsyntaxtextarea-2.5.8.jar"),
                            new URL("file:/D:/repo/com/alibaba/arthas/arthas-repackage-logger/0.0.13/arthas-repackage-logger-0.0.13.jar"),
                            new URL("file:/D:/repo/com/alibaba/fastjson/1.2.83_noneautotype/fastjson-1.2.83_noneautotype.jar"),
                            new URL("file:/D:/repo/ognl/ognl/3.1.19/ognl-3.1.19.jar"),
                            new URL("file:/D:/repo/org/javassist/javassist/3.20.0-GA/javassist-3.20.0-GA.jar"),
                            new URL("file:/D:/repo/org/benf/cfr/0.152/cfr-0.152.jar")
                            // 整包jar
//                            new URL("file:\\C:\\Users\\Administrator\\.arthas\\lib\\3.7.1\\arthas\\arthas-core.jar")
                    });

            /**
             * <pre>
             * ArthasBootstrap bootstrap = ArthasBootstrap.getInstance(inst);
             * </pre>
             */
            // TODO hzk，static防止arthasClassLoader产生的class和object
//            Class<?> bootstrapClass = arthasClassLoader.loadClass(ARTHAS_BOOTSTRAP);
            bootstrapClass = arthasClassLoader.loadClass(ARTHAS_BOOTSTRAP);
            Object bootstrap = bootstrapClass.getMethod(GET_INSTANCE, Instrumentation.class, Map.class).invoke(null,
                    instrumentation, configMap);
            boolean isBind = (Boolean) bootstrapClass.getMethod(IS_BIND).invoke(bootstrap);
            if (!isBind) {
                String errorMsg = "Arthas server port binding failed! Please check $HOME/logs/arthas/arthas.log for more details.";
                throw new RuntimeException(errorMsg);
            }
            bootstrapObject = bootstrap;
        } catch (Throwable e) {
            errorMessage = e.getMessage();
            if (!slientInit) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static File createTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = "arthas-" + System.currentTimeMillis() + "-";

        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
            File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within " + TEMP_DIR_ATTEMPTS + " attempts (tried "
                + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static String getArthasPassword() {
        try {
            String bootstrap = (String)bootstrapClass.getMethod("getPassword").invoke(bootstrapObject);
            return bootstrap;
        } catch (Exception e) {
            return null;
        }
    }
}
