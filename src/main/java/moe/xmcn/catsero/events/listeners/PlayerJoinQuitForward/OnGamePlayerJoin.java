package moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnGamePlayerJoin implements Listener {

    @EventHandler
    public void onGamePlayerJoinEvent(PlayerJoinEvent pljev) {
        if (Config.UsesConfig.getBoolean("qmsg.send-player-join-quit.enabled")) {
            String pljname = pljev.getPlayer().getName();
            String joinmsg = Config.UsesConfig.getString("qmsg.send-player-join-quit.format.join");
            joinmsg = joinmsg.replace("%player%", pljname);
            joinmsg = Config.tryToPAPI(pljev.getPlayer(), joinmsg);
            Config.sendMiraiGroupMessage(joinmsg);
        }
    }

}