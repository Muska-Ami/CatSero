package moe.xmcn.catsero.events.listeners.BanPlayerQQ;

import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

public class OnGamePlayerJoin implements Listener {

    @EventHandler
    public void highIsBaned(PlayerJoinEvent pje) {
        if (Config.UsesConfig.getBoolean("qban-player.use-database")) {
            try {
                if (Database.BanDatabase.queryBan(pje.getPlayer().getUniqueId())) {
                    pje.getPlayer().kickPlayer(Config.UsesConfig.getString("qban-player.reason"));
                }
            } catch (SQLException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
        }
    }

}
