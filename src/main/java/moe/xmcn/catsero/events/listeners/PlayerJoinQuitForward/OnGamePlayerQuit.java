package moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class OnGamePlayerQuit implements Listener {

    @EventHandler
    public void onGamePlayerQuitEvent(PlayerQuitEvent plqev) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("qmsg.send-player-join-quit.enabled")) {
            isEnabled(plqev);
        }
    }

    private void isEnabled(PlayerQuitEvent plqev) {
        String plqname = plqev.getPlayer().getName();
        String quitmsg = Config.INSTANCE.getUsesConfig().getString("qmsg.send-player-join-quit.format.quit");
        quitmsg = quitmsg.replace("%player%", plqname);
        quitmsg = Config.INSTANCE.tryToPAPI(plqev.getPlayer(), quitmsg);
        try {
            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(quitmsg);
        } catch (NoSuchElementException nse) {
            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
        }
    }

}