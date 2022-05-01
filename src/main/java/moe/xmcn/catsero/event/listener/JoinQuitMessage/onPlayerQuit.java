package moe.xmcn.catsero.event.listener.JoinQuitMessage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class onPlayerQuit implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        if (usesconfig.getBoolean("join-quit-message.enabled")) {
            String msg = usesconfig.getString("join-quit-message.format.quit");
            msg = msg.replace("%player%", event.getPlayer().getDisplayName());
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            event.setJoinMessage(msg);
        }
    }
}
