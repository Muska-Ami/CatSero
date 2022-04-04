package moe.xmcn.catsero.event;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class PingHost implements Listener, CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (plugin.getConfig().getString("general.ext-pinghost.enabled") == "true") {
            if (args.length > 0 && args[0].equalsIgnoreCase("ping")) {
                if (plugin.getConfig().getString("general.ext-pinghost.op-only") == "true") {
                    if (sender.hasPermission("catsero.admin")) {
                        if (args.length == 2) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&aPing进行中，请耐心等待..."));
                            InetAddress address = null;
                            try {
                                address = InetAddress.getByName(new Punycode().encodeURL(args[1]));
                            } catch (UnknownHostException e) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c无法解析主机名/IP"));
                                return false;
                            }
                            int flag = 0;
                            for (int i = 0; i < 4; i++) {
                                boolean b = false;
                                try {
                                    b = address.isReachable(1000);
                                } catch (IOException e) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                                    return false;
                                }
                                if (b)
                                    flag++;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                                    return false;
                                }
                            }
                            sender.sendMessage(args[1] + "(" + (new Punycode().encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c请键入正确的地址"));
                        }
                    } else {
                        sender.sendMessage("&e[&bCatSero&b]&c你无权这样做");
                        return false;
                    }
                } else if (args.length == 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&aPing进行中，请耐心等待..."));
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(new Punycode().encodeURL(args[1]));
                    } catch (UnknownHostException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c无法解析主机名/IP"));
                        return false;
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                            return false;
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                            return false;
                        }
                    }
                    sender.sendMessage(args[1] + "(" + (new Punycode().encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c请键入正确的地址"));
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getString("general.ext-pinghost.enabled") == "true") {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
                Long group = Long.valueOf(plugin.getConfig().getString("general.group"));
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping进行中，请耐心等待...");
                try {
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(new Punycode().encodeURL(args[2]));
                    } catch (UnknownHostException e) {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]无法解析主机名/IP");
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping时发生错误");
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping时发生错误");
                        }
                    }
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(args[2] + "(" + (new Punycode().encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                } catch (NoSuchElementException e) {
                    plugin.getLogger().warning("指定的机器人" + bot + "不存在，是否已经登录了机器人？");
                }
            }
        }
    }

}