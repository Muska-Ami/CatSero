package moe.xmcn.catsero.ext.qmsg;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class onPlayerJoinQuit implements Listener {
    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    @EventHandler
    public void onPlayerJoinQuit(PlayerQuitEvent plqev, PlayerJoinEvent pljev) {
        if (plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.enabled") == "true") {
            Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
            Long group = Long.valueOf(plugin.getConfig().getString("general.group"));

            String pljname = pljev.getPlayer().getName();
            String joinmsg = plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.format.join").replace("%player%", pljname);
            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(joinmsg);

            String plqname = pljev.getPlayer().getName();
            String quitmsg = plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.format.quit").replace("%player%", plqname);
            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(quitmsg);
        }
    }
}
