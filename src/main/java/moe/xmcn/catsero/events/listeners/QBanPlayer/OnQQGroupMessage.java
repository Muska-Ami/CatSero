package moe.xmcn.catsero.events.listeners.QBanPlayer;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnQQGroupMessage implements Listener {


    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    if (args.length == 5) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                        String message = Config.getMsgByMsID("qban-player.success")
                                .replace("%player%", args[2])
                                .replace("%reason%", args[3]);
                        Config.sendMiraiGroupMessage(message);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.invalid-options"));
                    }
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }

}
