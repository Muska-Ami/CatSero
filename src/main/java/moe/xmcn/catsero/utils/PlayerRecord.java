package moe.xmcn.catsero.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerRecord {

    public static class UUIDRecord implements Listener {
        @EventHandler
        public void record(PlayerJoinEvent pje) {
            String player_name = pje.getPlayer().getName();
            UUID player_uuid = pje.getPlayer().getUniqueId();
            try {
                Database.UUIDDatabase.insertTable(player_name, player_uuid);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }
    }

    public static class BanRecord {
        /**
         * 使用玩家UUID添加封禁
         *
         * @param player_uuid 玩家UUID
         */
        public static void record(UUID player_uuid) {
            try {
                Database.BanDatabase.insertTable(player_uuid);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }

        /**
         * 使用玩家绑定QQ号添加封禁（需要MiraiMC内置绑定QQ）
         *
         * @param player_qq 玩家QQ号
         */
        public static void record(Long player_qq) {
            try {
                Database.BanDatabase.insertTable(player_qq);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }

        public static void unrecord(UUID player_uuid) {
            try {
                Database.BanDatabase.uninsertTable(player_uuid);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }

        public static void unrecord(Long player_qq) {
            try {
                Database.BanDatabase.uninsertTable(player_qq);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }
    }

}
