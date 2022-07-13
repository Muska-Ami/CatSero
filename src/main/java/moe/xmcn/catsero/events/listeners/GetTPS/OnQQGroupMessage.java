package moe.xmcn.catsero.events.listeners.GetTPS;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.ServerTPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "tps") && Config.UsesConfig.getBoolean("tps.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            isEnabled(event);
        }
    }

    private void isEnabled(MiraiGroupMessageEvent event) {
        if (event.getSenderID() == Config.QQ_OP) {
            try {
                MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(String.valueOf(ServerTPS.getTPS()));
            } catch (NoSuchElementException nse) {
                Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
            }
        } else {
            try {
                MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
            } catch (NoSuchElementException nse) {
                Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
            }
        }
    }

}
