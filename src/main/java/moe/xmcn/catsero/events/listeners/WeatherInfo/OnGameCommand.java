package moe.xmcn.catsero.events.listeners.WeatherInfo;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class OnGameCommand {

    public static boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("weather") && Config.UsesConfig.getBoolean("weatherinfo.enabled")) {
            if (Config.UsesConfig.getBoolean("weatherinfo.op-only")) {
                if (sender.hasPermission("catsero.admin")) {
                    if (args.length == 2) {
                        WeatherMain(sender, command, label, args);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
                    }
                }
            } else {
                if (args.length == 2) {
                    WeatherMain(sender, command, label, args);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
                }
            }
            return true;
        }
        return false;
    }

    private static void WeatherMain(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.doing")));
                try {
                    String[] resvi = Utils.getWeather(args[1]);
                    if (resvi.length == 1) {
                        sender.sendMessage(ChatColor.RED + resvi[0]);
                    } else {
                        String message = Config.getMsgByMsID("minecraft.weatherinfo.success")
                                .replace("%type%", resvi[4])
                                .replace("%temperature%", resvi[1])
                                .replace("%wind%", resvi[2])
                                .replace("%wind_direction%", resvi[3])
                                .replace("%date%", resvi[0]);
                        sender.sendMessage(message);
                    }
                } catch (UnsupportedEncodingException uee) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.error")));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

}
