package moe.xmcn.catsero.v3.util;

import moe.xmcn.catsero.v3.CatSero;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Logger {

    PluginLogger pluginLogger = new PluginLogger(CatSero.INSTANCE);

    /**
     * 输出INFO类型的日志
     * @param message 消息
     */
    static void info(String message) {
        pluginLogger.info(message);
    }

    /**
     * 输出INFO类型的日志
     * @param message 消息组
     */
    static void info(String[] message) {
        for (int i = 1; i <= message.length; i++) {
            info(message[i-1]);
        }
    }

    /**
     * 输出WARN类型的日志
     * @param message 消息组
     */
    static void warn(String message) {
        pluginLogger.warning(message);
    }

    /**
     * 输出WARN类型的日志
     * @param message 消息组
     */
    static void warn(String[] message) {
        for (int i = 1; i <= message.length; i++) {
            warn(message[i-1]);
        }
    }

    /**
     * 输出ERROR类型的日志
     * @param message 消息组
     */
    static void error(String message) {
        pluginLogger.severe(message);
    }

    /**
     * 输出ERROR类型的日志
     * @param message 消息组
     */
    static void error(String[] message) {
        for (int i = 1; i <= message.length; i++) {
            error(message[i-1]);
        }
    }

    static void catchEx(Exception e) {

        StringBuilder stackT = new StringBuilder();
        List<StackTraceElement> list = new ArrayList<>();
        Collections.addAll(list, e.getStackTrace());
        list.forEach(stackT::append);

        String[] message = Arrays.asList(
                "CatSero运行时发生错误：" + e.getMessage(),
                "堆栈追踪：",
                stackT.toString()
        ).toArray(new String[0]);

        error(message);
    }

}
