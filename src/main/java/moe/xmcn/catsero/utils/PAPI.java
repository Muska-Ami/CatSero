package moe.xmcn.catsero.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PAPI {

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 玩家
     * @param text   旧文本
     * @return 新文本
     */
    public static String toPAPI(Player player, String text) {
        if (Envrionment.Depends.PlaceholderAPI) {
            return PlaceholderAPI.setBracketPlaceholders(player, text);
        }
        return text;
    }

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 命令发送者
     * @param text   旧文本
     * @return 新文本
     */
    public static String toPAPI(CommandSender player, String text) {
        if (Envrionment.Depends.PlaceholderAPI) {
            Player pl = (Player) player;
            return PlaceholderAPI.setBracketPlaceholders(pl, text);
        }
        return text;
    }

}
