package moe.xmcn.catsero.events.listeners.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class onPlayerJoin implements Listener {

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent pljev) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("qmsg.send-player-join-quit.enabled")) {
            String pljname = pljev.getPlayer().getName();
            String joinmsg = Config.INSTANCE.getUsesConfig().getString("qmsg.send-player-join-quit.format.join");
            joinmsg = joinmsg.replace("%player%", pljname);
            joinmsg = Config.INSTANCE.tryToPAPI(pljev.getPlayer(), joinmsg);
            try {
                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(joinmsg);
            } catch (NoSuchElementException nse) {
                Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
            }
        }

    }

}