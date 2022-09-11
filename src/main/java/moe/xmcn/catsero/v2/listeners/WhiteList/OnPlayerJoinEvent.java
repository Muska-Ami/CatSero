package moe.xmcn.catsero.v2.listeners.WhiteList;

import moe.xmcn.catsero.v2.utils.Configs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private boolean Allow = false;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Configs.getConfig("uses-config.yml").getBoolean("whitelist.enabled")) {
            Player player = e.getPlayer();
            Configs.getConfig("extra-configs/whitelist.yml").getStringList("list").forEach(it -> {
                if (player.getName().equals(it)) {
                    Allow = true;
                }
            });

            if (!Allow) {
                e.setJoinMessage("");
                player.kickPlayer("你没有白名单");
            }
        }
    }

}
