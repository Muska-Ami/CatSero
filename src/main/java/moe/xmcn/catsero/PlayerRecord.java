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

            /*
            旧的部分
            if (Objects.equals(Config.PlayerRecord.UUIDRecord.getString(player_name), "") || Config.PlayerRecord.UUIDRecord.getString(player_name) == null) {
                System.out.println("我运行了");
                Config.PlayerRecord.UUIDRecord.set(player_name, player_uuid);
                try {
                    Config.PlayerRecord.UUIDRecord.save("data/uuid.record");
                } catch (IOException e) {
                    Config.plugin.getLogger().log(Level.WARNING, "记录玩家UUID发生错误 玩家:" + player_name + " UUID:" + player_uuid);
                }
            } else if (!UUID.fromString(Config.PlayerRecord.UUIDRecord.getString(player_name)).equals(player_uuid)) {
                pje.getPlayer().kickPlayer("玩家UUID与记录中的不一致\n记录值 " + Config.PlayerRecord.UUIDRecord.getString(player_name) + "实际值 " + player_uuid);
            }
            */
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
