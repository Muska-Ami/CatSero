package moe.xmcn.catsero.events.listeners.PingHost;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.UnknownHostException;
import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("pinghost.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                if (Config.UsesConfig.getBoolean("pinghost.op-only")) {
                    if (event.getSenderID() == Config.QQ_OP) {
                        try {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.doing"));
                            String result = Utils.PingHostUtils(args[2]);
                            if (Objects.equals(result, "Error")) {
                                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error"));
                            } else {
                                long flag = Long.parseLong(result);
                                String message = Config.getMsgByMsID("qq.pinghost.success")
                                        .replace("%address_original%", args[2])
                                        .replace("%address_punycode%", Punycode.encodeURL(args[2]))
                                        .replace("%withdraw%", String.valueOf(flag))
                                        .replace("%lost%", String.valueOf(4 - flag))
                                        .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                                Config.sendMiraiGroupMessage(message);
                                //MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            }
                        } catch (UnknownHostException e) {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.failed"));
                        }
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                    }
                } else {
                    try {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.doing"));
                        String result = Utils.PingHostUtils(args[2]);
                        if (Objects.equals(result, "Error")) {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error"));
                        } else {
                            long flag = Long.parseLong(result);
                            String message = Config.getMsgByMsID("qq.pinghost.success")
                                    .replace("%address_original%", args[2])
                                    .replace("%address_punycode%", Punycode.encodeURL(args[2]))
                                    .replace("%withdraw%", String.valueOf(flag))
                                    .replace("%lost%", String.valueOf(4 - flag))
                                    .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                            Config.sendMiraiGroupMessage(message);
                            //MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                        }
                    } catch (UnknownHostException e) {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.failed"));
                    }
                }
            }
        }
    }

}