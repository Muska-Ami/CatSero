package moe.xmcn.catsero.v2.utils;

public class Env {

    public static boolean MiraiMC = false;
    public static boolean TrChat = false;

    public static void checkDependsLoad() {
        if (Configs.plugin.getProxy().getPluginManager().getPlugin("MiraiMC") != null) {
            MiraiMC = true;
        }
        if (Configs.plugin.getProxy().getPluginManager().getPlugin("TrChat") != null) {
            TrChat = true;
        }
    }

}
