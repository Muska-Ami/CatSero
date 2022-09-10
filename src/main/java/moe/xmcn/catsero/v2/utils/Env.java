package moe.xmcn.catsero.v2.utils;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Env {

    public static boolean MiraiMC = false;
    public static boolean PlaceholderAPI = false;
    public static boolean TrChat = false;

    public static void checkDependsLoad() {
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null) {
            MiraiMC = true;
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI = true;
        }
        if (Bukkit.getPluginManager().getPlugin("TrChat") != null) {
            TrChat = true;
        }
    }

    public static class APlaceholderAPI {

        /**
         * 尝试转为PlaceholderAPI文本
         *
         * @param player 玩家
         * @param text   旧文本
         * @return 新文本
         */
        public static String tryToPAPI(Player player, String text) {
            if (PlaceholderAPI) {
                return me.clip.placeholderapi.PlaceholderAPI.setBracketPlaceholders(player, text);
            }
            return text;
        }

        /**
         * 尝试转为PlaceholderAPI文本
         * l         *
         *
         * @param player 发送者
         * @param text   旧文本
         * @return 新文本
         */
        public static String tryToPAPI(CommandSender player, String text) {
            if (PlaceholderAPI) {
                Player pl = (Player) player;
                return me.clip.placeholderapi.PlaceholderAPI.setBracketPlaceholders(pl, text);
            }
            return text;
        }
    }

    public static class AMiraiMC {
        public static void sendMiraiGroupMessage(String message, String botid, String groupid) {
            try {
                long bot = Configs.getConfig("mirai-configs/bot.yml").getLong(botid);
                long group = Configs.getConfig("mirai-configs/group.yml").getLong(groupid);
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
            } catch (Exception e) {
                Loggers.logWARN(Configs.getMsgByMsID("general.send-message-qq-error").replace("%error%", e + Arrays.toString(e.getStackTrace())));
            }
        }
    }

}
