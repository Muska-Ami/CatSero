package moe.xmcn.catsero.events.listeners.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
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
        if (Config.INSTANCE.getUsesConfig().getBoolean("qban-player.enabled") && event.getGroupID() == Config.INSTANCE.getUse_Group() && event.getBotID() == Config.INSTANCE.getUse_Bot()) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {

                if (event.getSenderID() == Config.INSTANCE.getQQ_OP()) {
                    System.out.println(event.getSenderID());
                    if (args.length == 5) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                        try {
                            String message = Config.INSTANCE.getMsgByMsID("qban-player.success")
                                    .replace("%player%", args[2])
                                    .replace("%reason%", args[3]);
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + message);
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
                    } else {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.invalid-options"));
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
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
}
