package moe.xmcn.catsero.event.command;

import moe.xmcn.catsero.event.gist.PingHost;
import moe.xmcn.catsero.event.gist.WeatherUtils;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Objects;

public class CatSero implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1) {
        /*
          PingHost
         */
            if (args[0].equalsIgnoreCase("ping") && Config.INSTANCE.getUsesConfig().getBoolean("pinghost.enabled")) {
                if (Config.INSTANCE.getUsesConfig().getBoolean("pinghost.op-only")) {
                    if (sender.hasPermission("catsero.admin")) {
                        if (args.length == 2) {
                            try {
                                sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.doing"))));
                                String result = PingHost.GameUtils(args[1]);
                                if (Objects.equals(result, "Error")) {
                                    sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.error"))));
                                } else {
                                    long flag = Long.parseLong(result);
                                    String message = Config.INSTANCE.tryToPAPI(sender, Config.INSTANCE.getMsgByMsID("minecraft.pinghost.success")
                                            .replace("%address_original%", args[1])
                                            .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                            .replace("%withdraw%", String.valueOf(flag))
                                            .replace("%lost%", String.valueOf(4 - flag))
                                            .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4)));
                                    //sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                                    sender.sendMessage(message);
                                }
                            } catch (UnknownHostException e) {
                                sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.failed"))));
                            }
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&c请键入正确的地址"));
                            return false;
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.no-permission")));
                        return false;
                    }
                } else if (args.length == 2) {
                    try {
                        sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.doing"))));
                        String result = PingHost.GameUtils(args[1]);
                        if (Objects.equals(result, "Error")) {
                            sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.error"))));
                        } else {
                            long flag = Long.parseLong(result);
                            String message = Config.INSTANCE.tryToPAPI(sender, Config.INSTANCE.getMsgByMsID("minecraft.pinghost.success")
                                    .replace("%address_original%", args[1])
                                    .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                    .replace("%withdraw%", String.valueOf(flag))
                                    .replace("%lost%", String.valueOf(4 - flag))
                                    .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4)));
                            //sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            sender.sendMessage(message);
                        }
                    } catch (UnknownHostException e) {
                        sender.sendMessage(Config.INSTANCE.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.pinghost.failed"))));
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&c请键入正确的地址"));
                    return false;
                }

        /*
         天气获取
        */
            } else if (args[0].equalsIgnoreCase("weather") && Config.INSTANCE.getUsesConfig().getBoolean("weatherinfo.enabled")) {
                if (args.length == 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&a天气获取进行中，请耐心等待..."));
                    try {
                        String[] resvi = WeatherUtils.getWeather(args[1]);
                        sender.sendMessage("天气信息:\n 类型:" + resvi[4] + "\n 温度:" + resvi[1] + "\n 风力:" + resvi[2] + "\n 风向:" + resvi[3] + "\n 日期:" + resvi[0]);
                    } catch (UnsupportedEncodingException uee) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&c获取天气时出现错误"));
                        return false;
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&c请输入城市"));
                    return false;
                }

        /*
         插件重载
        */
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    Config.INSTANCE.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&a配置文件已重载"));
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + "&c参数过多"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.undefined-usage")));
                return false;
            }
        } else {
        /*
          无效方法
         */
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.INSTANCE.getPrefix_MC() + Config.INSTANCE.getMsgByMsID("minecraft.undefined-usage")));
            return false;
        }
        return false;
    }
}
