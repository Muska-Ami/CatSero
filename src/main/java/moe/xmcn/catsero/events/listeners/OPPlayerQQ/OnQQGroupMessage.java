package moe.xmcn.catsero.events.listeners.OPPlayerQQ;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.PlayerUUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.ServerOperator;

import java.util.Objects;
import java.util.UUID;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent0(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "setop") && Config.UsesConfig.getBoolean("qop-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            if (event.getSenderID() == Config.QQ_OP) {
                ServerOperator plu = /*Bukkit.getPlayer(pl)*/ Bukkit.getPlayer(args[2]);
                boolean isOp = plu.isOp();
                if (isOp) {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-is-op"));
                } else {
                    plu.setOp(true);
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.success-add"));
                }
            } else {
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
            }
        }
    }

    @EventHandler
    public void onMiraiGroupMessageEvent1(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "removeop") && Config.UsesConfig.getBoolean("qop-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            if (event.getSenderID() == Config.QQ_OP) {
                ServerOperator plu = /*Bukkit.getPlayer(pl)*/ Bukkit.getPlayer(args[2]);
                boolean isOp = plu.isOp();
                if (!isOp) {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-not-op"));
                } else {
                    plu.setOp(false);
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.success-remove"));
                }
            } else {
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
            }
        }
    }

}