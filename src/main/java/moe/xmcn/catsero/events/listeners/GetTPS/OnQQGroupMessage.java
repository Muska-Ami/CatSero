package moe.xmcn.catsero.events.listeners.GetTPS;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
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
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "kick") && Config.INSTANCE.getUsesConfig().getBoolean("tps.enabled") && event.getGroupID() == Config.INSTANCE.getUse_Group() && event.getBotID() == Config.INSTANCE.getUse_Bot()) {
            if (event.getSenderID() == Config.INSTANCE.getQQ_OP()) {
                try {
                    MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(String.valueOf(ServerTPS.getTPS()));
                } catch (NoSuchElementException nse) {
                    Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                }
            } else {
                try {
                    MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.no-permission"));
                } catch (NoSuchElementException nse) {
                    Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                }
            }
        }
    }

}
