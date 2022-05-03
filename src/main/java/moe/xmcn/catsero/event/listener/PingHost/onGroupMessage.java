package moe.xmcn.catsero.event.listener.PingHost;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {


    @EventHandler
    public void OnGroupMessage(MiraiGroupMessageEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("pinghost.enabled")) {
            if (!Config.INSTANCE.getUsesConfig().getBoolean("pinghost.op-only")) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping") && event.getGroupID() == Config.INSTANCE.getUse_Group()) {
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping进行中，请耐心等待...");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(Punycode.encodeURL(args[2]));
                    } catch (UnknownHostException e) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "无法解析主机名/IP");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            assert address != null;
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse);
                            }
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse);
                            }
                        }
                    }
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                }
            } else if (event.getSenderID() == Config.INSTANCE.getPlugin().getConfig().getLong("qbgset.qq-op")) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                long bot = Config.INSTANCE.getPlugin().getConfig().getLong("qbgset.bot");
                long group = Config.INSTANCE.getPlugin().getConfig().getLong("qbgset.group");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping") && event.getGroupID() == group) {
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping进行中，请耐心等待...");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(Punycode.encodeURL(args[2]));
                    } catch (UnknownHostException e) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "无法解析主机名/IP");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            assert address != null;
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse);
                            }
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "Ping时发生错误");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse);
                            }
                        }
                    }
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                }
            }
        }
    }

}
