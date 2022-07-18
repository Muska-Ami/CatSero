package moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnGamePlayerQuit implements Listener {

    @EventHandler
    public void onGamePlayerQuitEvent(PlayerQuitEvent plqev) {
        if (Config.UsesConfig.getBoolean("qmsg.send-player-join-quit.enabled")) {
            isEnabled(plqev);
        }
    }

    private void isEnabled(PlayerQuitEvent plqev) {
        String plqname = plqev.getPlayer().getName();
        String quitmsg = Config.UsesConfig.getString("qmsg.send-player-join-quit.format.quit");
        quitmsg = quitmsg.replace("%player%", plqname);
        quitmsg = Config.tryToPAPI(plqev.getPlayer(), quitmsg);
        Config.sendMiraiGroupMessage(quitmsg);
    }

}