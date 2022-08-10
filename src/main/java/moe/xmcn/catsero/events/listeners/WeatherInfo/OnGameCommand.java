/*
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
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
                //仅OP模式
                if (sender.hasPermission("catsero.admin")) {
                    //有OP权限
                    if (args.length == 2) {
                        WeatherMain(sender, args);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
                    }
                }
            } else {
                //通用模式
                if (args.length == 2) {
                    WeatherMain(sender, args);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
                }
            }
            return true;
        }
        return false;
    }

    private static void WeatherMain(@NotNull CommandSender sender, @NotNull String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.doing")));
                try {
                    String[] resvi = Utils.getWeather(args[1]);
                    if (resvi.length == 5) {
                        String message = Config.getMsgByMsID("minecraft.weatherinfo.success")
                                .replace("%type%", resvi[4])
                                .replace("%temperature%", resvi[1])
                                .replace("%wind%", resvi[2])
                                .replace("%wind_direction%", resvi[3])
                                .replace("%date%", resvi[0]);
                        sender.sendMessage(message);
                    } else {
                        sender.sendMessage(ChatColor.RED + resvi[0]);
                    }
                } catch (UnsupportedEncodingException uee) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.error")));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

}
