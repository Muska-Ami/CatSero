package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class onPlayerJoin implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pljev) {
        if (plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.enabled") == "true") {
            Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
            Long group = Long.valueOf(plugin.getConfig().getString("general.group"));

            String pljname = pljev.getPlayer().getName();
            String joinmsg = plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.format.join").replace("%player%", pljname);
            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(joinmsg);
        }
    }

}