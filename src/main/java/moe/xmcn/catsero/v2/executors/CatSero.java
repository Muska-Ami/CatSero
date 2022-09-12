/*
 * CatSero v2
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
package moe.xmcn.catsero.v2.executors;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatSero implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            switch (strings[0]) {
                case "version":
                    if (strings.length == 1) {
                        if (commandSender.hasPermission("catsero.admin")) {
                            ArrayList<String> env = new ArrayList<>(Arrays.asList(
                                    "&e插件版本: &b" + Configs.PluginInfo.getString("version"),
                                    "&e插件作者: &b" + Configs.PluginInfo.getString("author"),
                                    "&e依赖装载情况:",
                                    "- MiraiMC &6=>&r " + Env.MiraiMC,
                                    "- PlaceholderAPI &6=>&r " + Env.PlaceholderAPI,
                                    "- TrChat &6=>&r " + Env.TrChat,
                                    "",
                                    "&e服务器版本: &b" + Bukkit.getBukkitVersion()
                            ));
                            for (int i = 1; i <= env.toArray().length; i++) {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) env.toArray()[i - 1]));
                            }
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configs.getMsgByMsID("minecraft.invalid-options")));
                    }
                    break;
                case "send":
                    break;
                default:
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c命令不存在"));
                    break;
            }
        } else commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCatSero&r by &eXiaMoHuaHuo_CN"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            List<String> sublist = new ArrayList<>();
            if (strings.length == 1) {
                sublist.add("version");
                return sublist;
            }
            return null;
        }
        return null;
    }
}
