package moe.xmcn.catsero.events.listeners.WeatherInfo;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("weatherinfo.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("weather")) {
                if (Config.UsesConfig.getBoolean("weatherinfo.op-only")) {
                    if (event.getSenderID() == Config.QQ_OP) {
                        if (args.length == 3 && event.getGroupID() == Config.Use_Group) {
                            WeatherMain(args);
                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                        }
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                    }
                } else {
                    if (args.length == 3 && event.getGroupID() == Config.Use_Group) {
                        WeatherMain(args);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                    }
                }
            }
        }
    }

    private static void WeatherMain(@NotNull String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.doing"));
                    String[] resvi = Utils.getWeather(args[2]);
                    String message = Config.getMsgByMsID("qq.weatherinfo.success")
                            .replace("%type%", resvi[4])
                            .replace("%temperature%", resvi[1])
                            .replace("%wind%", resvi[2])
                            .replace("%wind_direction%", resvi[3])
                            .replace("%date%", resvi[0]);
                    Config.sendMiraiGroupMessage(message);
                } catch (UnsupportedEncodingException uee) {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.error"));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

}
