package moe.xmcn.catsero.events.listeners.BanPlayerQQ;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnQQGroupMessage implements Listener {


    @EventHandler
    public void onMiraiGroupMessageEvent0(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    if (args.length == 3) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], Config.UsesConfig.getString("qban-player.reason"), null, null);
                        if (Players.getPlayer(args[2]).isOnline()) {
                            Bukkit.getScheduler().runTask(Config.plugin, () -> Players.getPlayer(args[2]).kickPlayer(Config.UsesConfig.getString("qban-player.reason")));
                        }
                        String message = Config.getMsgByMsID("qq.qban-player.success-ban")
                                .replace("%player%", args[2]);
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

    @EventHandler
    public void onMiraiGroupMessageEvent1(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("unban")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    if (args.length == 3) {
                        Bukkit.getBanList(BanList.Type.NAME).pardon(args[2]);
                        String message = Config.getMsgByMsID("qq.qban-player.success-unban")
                                .replace("%player%", args[2]);
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
