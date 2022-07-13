package moe.xmcn.catsero.events.listeners.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class OnQQGroupMessage implements Listener {


    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            isEnabled(event);
        }
    }

    private void isEnabled(MiraiGroupMessageEvent event) {
        String msg = event.getMessage();
        String[] args = msg.split(" ");
        if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {

            if (event.getSenderID() == Config.QQ_OP) {
                if (args.length == 5) {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                    try {
                        String message = Config.getMsgByMsID("qban-player.success")
                                .replace("%player%", args[2])
                                .replace("%reason%", args[3]);
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(Config.Prefix_QQ + message);
                    } catch (NoSuchElementException nse) {
                        Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                    }
                } else {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.invalid-options"));
                    } catch (NoSuchElementException nse) {
                        Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                    }
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

}
