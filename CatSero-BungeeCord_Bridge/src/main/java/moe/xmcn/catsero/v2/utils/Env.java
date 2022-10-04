package moe.xmcn.catsero.v2.utils;

import me.dreamvoid.miraimc.api.MiraiBot;

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

    public static class AMiraiMC {
        public static void sendMiraiGroupMessage(String message, long bot, long group) {
            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
        }

        public static void sendMiraiGroupMemberMessage(String message, long bot, long group, long member_code) {
            MiraiBot.getBot(bot).getGroup(group).getMember(member_code).sendMessage(message);
        }
    }

}
