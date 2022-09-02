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
package moe.xmcn.catsero.event.listener.PingHost;

import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.Punycode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.Objects;

public class OnGameCommand {

    public static boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("ping") && Config.UsesConfig.getBoolean("pinghost.enabled")) {
            if (Config.UsesConfig.getBoolean("pinghost.need-permission")) {
                //OP模式
                if (sender.hasPermission("catsero.pinghost")) {
                    //有OP权限
                    if (args.length == 2) {
                        PingMain(sender, args);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c请键入正确的地址"));
                    }
                } else {
                    //无OP权限
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.no-permission")));
                }
            } else if (args.length == 2) {
                //通用模式
                PingMain(sender, args);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c请键入正确的地址"));
            }
            return true;
        }
        return false;
    }

    private static void PingMain(@NotNull CommandSender sender, @NotNull String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.doing"))));
                    String result = Utils.PingHostUtils(args[1]);
                    if (Objects.equals(result, "Error")) {
                        sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.error"))));
                    } else {
                        long flag = Long.parseLong(result);
                        String message = Config.tryToPAPI(sender, Config.getMsgByMsID("minecraft.pinghost.success")
                                .replace("%address_original%", args[1])
                                .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                .replace("%withdraw%", String.valueOf(flag))
                                .replace("%lost%", String.valueOf(4 - flag))
                                .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4)));
                        sender.sendMessage(message);
                    }
                } catch (UnknownHostException e) {
                    sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.failed"))));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

}
