package moe.xmcn.catsero.events.listeners.WeatherInfo;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class OnGameCommand {

    public static boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("weather") && Config.UsesConfig.getBoolean("weatherinfo.enabled")) {
            if (args.length == 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.doing")));
                try {
                    String[] resvi = Utils.getWeather(args[1]);
                    String message = Config.getMsgByMsID("minecraft.weatherinfo.success")
                            .replace("%type%", resvi[4])
                            .replace("%temperature%", resvi[1])
                            .replace("%wind%", resvi[2])
                            .replace("%wind_direction%", resvi[3])
                            .replace("%date%", resvi[0]);
                    sender.sendMessage(message);
                    //sender.sendMessage("天气信息:\n 类型:" + resvi[4] + "\n 温度:" + resvi[1] + "\n 风力:" + resvi[2] + "\n 风向:" + resvi[3] + "\n 日期:" + resvi[0]);
                } catch (UnsupportedEncodingException uee) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.error")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
            }
            return true;
        }
        return false;
    }

}
