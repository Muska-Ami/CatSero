package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerRecord implements Listener {

    @EventHandler
    public void record(PlayerJoinEvent pje) {
        try {
            if (new File(Config.plugin.getDataFolder(), "data/player.record").createNewFile()) {
                Config.plugin.getLogger().log(Level.INFO, "创建玩家记录文件");
            }
        } catch (IOException e) {
            Config.plugin.getLogger().log(Level.WARNING, "无法创建玩家记录文件");
        }
        String player_name = pje.getPlayer().getName();
        UUID player_uuid = pje.getPlayer().getUniqueId();

        if (Config.PlayerRecord.getString(player_name) == null) {
            Config.PlayerRecord.set(player_name, player_uuid);
        } else if (!UUID.fromString(Config.PlayerRecord.getString(player_name)).equals(player_uuid)) {
            pje.getPlayer().kickPlayer("玩家UUID与记录中的不一致\n记录值 " + Config.PlayerRecord.getString(player_name) + "实际值 " + player_uuid);
        }
    }

}
