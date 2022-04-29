package moe.xmcn.catsero.event.command;

import moe.xmcn.catsero.utils.Punycode;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class CatSero implements CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    Yaml yaml = new Yaml();
    InputStream in = plugin.getResource("usesconfig.yml");
    Map<String, Object> map = yaml.loadAs(in, Map.class);

    String prefixmc = plugin.getConfig().getString("format-list.prefix.to-mc");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /**
         * PingHost
         */
        if (args[0].equalsIgnoreCase("ping") && (Boolean) ((Map<String, Object>) map.get("pinghost")).get("enabled")) {
            if ((Boolean) ((Map<String, Object>) map.get("weatherinfo")).get("op-only")) {
                if (sender.hasPermission("catsero.admin")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&aPing进行中，请耐心等待..."));
                        InetAddress address = null;
                        try {
                            address = InetAddress.getByName(new Punycode().encodeURL(args[1]));
                        } catch (UnknownHostException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法解析主机名/IP"));
                            return false;
                        }
                        int flag = 0;
                        for (int i = 0; i < 4; i++) {
                            boolean b = false;
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
                InetAddress address = null;
                try {
                    address = InetAddress.getByName(Punycode.encodeURL(args[1]));
                } catch (UnknownHostException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法解析主机名/IP"));
                    return false;
                }
                int flag = 0;
                for (int i = 0; i < 4; i++) {
                    boolean b = false;
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

        /**
        * 天气获取
        */
        } else if (args[0].equalsIgnoreCase("weather") && (Boolean) ((Map<String, Object>) map.get("weatherinfo")).get("enabled")) {
            if (args.length == 2) {
                String res = WeatherUtils.GetWeatherData(args[1]);
                sender.sendMessage(res);
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c请输入城市"));
                return false;
            }

        /**
        * 插件重载
        */
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&a配置文件已重载"));
            return true;
        /**
        * 无效方法
        */
        } else if (args[0].equalsIgnoreCase("")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c无法找到使用方法，请检查拼写"));
            return false;
        }
        return false;
    }
}
