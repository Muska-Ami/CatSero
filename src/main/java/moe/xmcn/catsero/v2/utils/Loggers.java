package moe.xmcn.catsero.v2.utils;

import java.util.ArrayList;
import java.util.logging.Level;

public interface Loggers {

    static void logINFO(String log) {
        Configs.plugin.getLogger().log(Level.INFO, log);
    }

    static void logWARN(String warn) {
        Configs.plugin.getLogger().log(Level.WARNING, warn);
    }

    interface CustomLevel {
        static void logLoader(String msg) {
            logINFO("[Loader] " + msg);
        }

        static void logLoader(ArrayList<String> msgs) {
            for (int i = 1; i < msgs.toArray().length - 1; i++) {
                logINFO("[Loader] " + msgs.toArray()[i - 1]);
            }
        }
    }

}
