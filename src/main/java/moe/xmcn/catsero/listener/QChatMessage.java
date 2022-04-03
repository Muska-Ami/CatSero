package moe.xmcn.catsero.listener;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class QChatMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e) {
        if (plugin.getConfig().getString("genal.ext-qmsg.print-group-message-console") == "true") {
            System.out.println("[" + e.getGroupName() + "/" + e.getGroupID() + "]:" + e.getMessage());
        }
        if (plugin.getConfig().getString("genal.ext-qmsg.forward-message") == "true") {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("")));
        }
    }
}
