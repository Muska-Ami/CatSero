package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
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
                Config.plugin.getLogger().log(Level.WARNING, "无法写入数据库");
            }
        }
    }

    public static class BanRecord {
        public void record(UUID player_uuid, Long player_qq) {
            try {
                Database.BanDatabase.insertTable(player_uuid, player_qq);
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, "无法写入数据库");
            }
        }
    }

}
