package moe.xmcn.catsero.event.listener.JoinQuitMessage;

import moe.xmcn.catsero.Config;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerQuit implements Listener {


    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("join-quit-message.enabled")) {
            String msg = Config.INSTANCE.getUsesConfig().getString("join-quit-message.format.quit");
            msg = msg.replace("%player%", event.getPlayer().getDisplayName());
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            event.setJoinMessage(msg);
        }
    }
}
