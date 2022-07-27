package moe.xmcn.catsero.events.listeners.GetTPS;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.ServerTPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "tps") && Config.UsesConfig.getBoolean("get-tps.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            Config.sendMiraiGroupMessage("TPS: " + ServerTPS.getTPS());
        }
    }

}
