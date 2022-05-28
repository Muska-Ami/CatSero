package moe.xmcn.catsero.event.listener.PingHost;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.event.gist.PingHost;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class onGroupMessage implements Listener {

    @EventHandler
    public void OnGroupMessage(MiraiGroupMessageEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("pinghost.enabled") && event.getGroupID() == Config.INSTANCE.getUse_Group() && event.getBotID() == Config.INSTANCE.getUse_Bot()) {
            if (!Config.INSTANCE.getUsesConfig().getBoolean("pinghost.op-only")) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                    try {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping进行中，请耐心等待...");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                        }
                        String result = PingHost.GameUtils(args[2]);
                        if (Objects.equals(result, "Error")) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        } else {
                            long flag = Long.parseLong(result);
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        }
                    } catch (UnknownHostException e) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "无法解析主机名/IP");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                        }
                    }
                }
            } else if (event.getSenderID() == Config.INSTANCE.getQQ_OP()) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                    try {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping进行中，请耐心等待...");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                        }
                        String result = PingHost.GameUtils(args[2]);
                        if (Objects.equals(result, "Error")) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        } else {
                            long flag = Long.parseLong(result);
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        }
                    } catch (UnknownHostException e) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "无法解析主机名/IP");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                        }
                    }
                }
            }
        }
    }
}