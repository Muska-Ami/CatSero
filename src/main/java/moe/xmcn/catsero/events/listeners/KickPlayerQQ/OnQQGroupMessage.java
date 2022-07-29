package moe.xmcn.catsero.events.listeners.KickPlayerQQ;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "kick") && Config.UsesConfig.getBoolean("qkick-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            if (event.getSenderID() == Config.QQ_OP) {
                if (Players.getPlayer(args[2]).isOnline()) {
                    Bukkit.getScheduler().runTask(Config.plugin, () -> Bukkit.getPlayer(args[2]).kickPlayer(Config.UsesConfig.getString("qkick-player.message")));
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qkick-player.kick").replace("%player%", args[2]));
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qkick-player.not-online").replace("%player%", args[2]));
                }
            } else {
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
            }
        }
    }

}