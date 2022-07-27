package moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnGamePlayerQuit implements Listener {

    @EventHandler
    public void onGamePlayerQuitEvent(PlayerQuitEvent plqev) {
        if (Config.UsesConfig.getBoolean("send-player-join-quit.enabled")) {
            if (Config.UsesConfig.getBoolean("send-player-join-quit.need-permission")) {
                if (plqev.getPlayer().hasPermission("catsero.send-player-join-quit.quit")) {
                    String plqname = plqev.getPlayer().getName();
                    String quitmsg = Config.UsesConfig.getString("send-player-join-quit.format.quit");
                    quitmsg = quitmsg.replace("%player%", plqname);
                    quitmsg = Config.tryToPAPI(plqev.getPlayer(), quitmsg);
                    Config.sendMiraiGroupMessage(quitmsg);
                }
            } else {
                String plqname = plqev.getPlayer().getName();
                String quitmsg = Config.UsesConfig.getString("send-player-join-quit.format.quit");
                quitmsg = quitmsg.replace("%player%", plqname);
                quitmsg = Config.tryToPAPI(plqev.getPlayer(), quitmsg);
                Config.sendMiraiGroupMessage(quitmsg);
            }
        }
    }

}