package moe.xmcn.catsero.cmd;

import moe.xmcn.catsero.utils.Punycode;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Commands implements CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args[0].equalsIgnoreCase("ping") && plugin.getConfig().getString("general.ext-pinghost.enabled") == "true") {
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

        } else if (args[0].equalsIgnoreCase("weather") && plugin.getConfig().getString("general.ext-weatherinfo.enabled") == "true") {
            if (args.length == 2) {
                String res = WeatherUtils.GetWeatherData(args[1]);
                System.out.println(res);
                sender.sendMessage(res);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c请输入城市"));
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage("&e[&bCatSero&e]&a配置文件已重载");
            } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e] &c无法找到使用方法"));
        }
        return false;
    }
}
