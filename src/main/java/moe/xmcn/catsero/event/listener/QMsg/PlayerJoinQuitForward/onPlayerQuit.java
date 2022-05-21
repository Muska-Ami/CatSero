package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class onPlayerQuit implements Listener {

    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent plqev) {
        Config.INSTANCE.getUse_Bots().forEach(bot -> Config.INSTANCE.getUse_Groups().forEach(group -> {
            if (Config.INSTANCE.getUsesConfig().getBoolean("qmsg.send-player-join-quit.enabled")) {

                String plqname = plqev.getPlayer().getName();
                String quitmsg = Config.INSTANCE.getUsesConfig().getString("qmsg.send-player-join-quit.format.quit");
                quitmsg = quitmsg.replace("%player%", plqname);
                try {
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(quitmsg);
                } catch (NoSuchElementException nse) {
                    System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                }
            }
        }));
    }

}