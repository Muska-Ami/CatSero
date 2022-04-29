package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class onPlayerQuit implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent plqev) {
        if (plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.enabled") == "true") {
            Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
            Long group = Long.valueOf(plugin.getConfig().getString("general.group"));

            String plqname = plqev.getPlayer().getName();
            String quitmsg = plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.format.quit").replace("%player%", plqname);
            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(quitmsg);
        }
    }

}