package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

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
        Config.INSTANCE.getUse_Bots().forEach(bot -> Config.INSTANCE.getUse_Groups().forEach(group -> {
            if (Config.INSTANCE.getUsesConfig().getBoolean("qmsg.send-player-join-quit.enabled")) {
                String pljname = pljev.getPlayer().getName();
                String joinmsg = Config.INSTANCE.getUsesConfig().getString("qmsg.send-player-join-quit.format.join");
                joinmsg = joinmsg.replace("%player%", pljname);
                joinmsg = Config.INSTANCE.tryToPAPI(pljev.getPlayer(), joinmsg);
                try {
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(joinmsg);
                } catch (NoSuchElementException nse) {
                    System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                }
            }
        }));
    }

}