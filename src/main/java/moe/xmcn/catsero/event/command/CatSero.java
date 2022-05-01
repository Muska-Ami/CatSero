package moe.xmcn.catsero.event.command;

import moe.xmcn.catsero.utils.Punycode;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CatSero implements CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    String prefixmc = plugin.getConfig().getString("format-list.prefix.to-mc");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /*
          PingHost
         */
        if (args[0].equalsIgnoreCase("ping") && usesconfig.getBoolean("pinghost.enabled")) {
            if (usesconfig.getBoolean("weatherinfo.op-only")) {
                if (sender.hasPermission("catsero.admin")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&aPing进行中，请耐心等待..."));
                        InetAddress address;
                        try {
                            address = InetAddress.getByName(Punycode.encodeURL(args[1]));
                        } catch (UnknownHostException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法解析主机名/IP"));
                            return false;
                        }
                        int flag = 0;
                        for (int i = 0; i < 4; i++) {
                            boolean b;
                            try {
                                b = address.isReachable(1000);
                            } catch (IOException e) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&cPing时发生错误"));
                                return false;
                            }
                            if (b)
                                flag++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&cPing时发生错误"));
                                return false;
                            }
                        }
                        sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c请键入正确的地址"));
                    }
                } else {
                    sender.sendMessage(prefixmc + "&c你无权这样做");
                    return false;
                }
            } else if (args.length == 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&aPing进行中，请耐心等待..."));
                InetAddress address;
                try {
                    address = InetAddress.getByName(Punycode.encodeURL(args[1]));
                } catch (UnknownHostException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法解析主机名/IP"));
                    return false;
                }
                int flag = 0;
                for (int i = 0; i < 4; i++) {
                    boolean b;
                    try {
                        b = address.isReachable(1000);
                    } catch (IOException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&cPing时发生错误"));
                        return false;
                    }
                    if (b)
                        flag++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&cPing时发生错误"));
                        return false;
                    }
                }
                sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c请键入正确的地址"));
                return false;
            }

        /*
         天气获取
        */
        } else if (args[0].equalsIgnoreCase("weather") && usesconfig.getBoolean("weatherinfo.enabled")) {
            if (args.length == 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&a天气获取进行中，请耐心等待..."));
                try {
                    String[] resvi = WeatherUtils.getWeather(args[1]);
                    sender.sendMessage("天气信息:\n 类型:" + resvi[4] + "\n 温度:" + resvi[1] + "\n 风力:" + resvi[2] + "\n 风向:" + resvi[3] + "\n 日期:" + resvi[0]);
                } catch (UnsupportedEncodingException uee) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c获取天气时出现错误"));
                    return false;
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c请输入城市"));
                return false;
            }

        /*
         插件重载
        */
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&a配置文件已重载"));
            return true;
        }
        /*
          无效方法
         */
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法找到使用方法，请检查拼写"));
        return false;
    }
}
