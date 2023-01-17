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
package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.Player;
import moe.xmcn.catsero.utils.WhiteListDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "version":
                        if (args.length == 1) {
                            if (sender.hasPermission("catsero.admin")) {
                                List<String> env = Arrays.asList(
                                        "&b===== &dCatSero Runtime Checker &b=====",
                                        "&bServer Version: " + Envrionment.server_version,
                                        "&bBukkit Version: " + Envrionment.bukkit_version,
                                        "&bPlugin Version: " + Envrionment.plugin_version,
                                        "&bDepends:",
                                        "- &bMiraiMC &a=> &e" + Envrionment.Depends.MiraiMC,
                                        "&bSoft-depends:",
                                        "- &bPlaceholderAPI &a=> &e" + Envrionment.Depends.PlaceholderAPI,
                                        "- &bTrChat &a=> &e" + Envrionment.Depends.TrChat,
                                        "&b==================================="
                                );
                                for (int i = 1; i <= env.toArray().length; i++) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) env.toArray()[i - 1]));
                                }
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                        }
                        break;
                    case "whitelist":
                        if (sender.hasPermission("catsero.admin")) {
                            //白名单
                            if (args.length == 4) {
                                // 添加
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (!WhiteListDatabase.getNameList().contains(args[2])) {
                                        if (WhiteListDatabase.insertList(args[2], Long.parseLong(args[3]))) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.ADD_SUCCESS));
                                        } else
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.ADD_ERROR_SQL));
                                    } else
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.ADD_ERROR_REPEAT));
                                }
                            } else if (args.length == 3) {
                                // 移除
                                if (args[1].equalsIgnoreCase("remove")) {
                                    if (WhiteListDatabase.getNameList().contains(args[2])) {
                                        if (WhiteListDatabase.removeList(args[2])) {
                                            if (
                                                    Player.getUUIDByName(args[2]) != null
                                                            && Player.getPlayer(args[2]).isOnline()
                                            ) {
                                                Bukkit.getScheduler().runTask(
                                                        Configuration.plugin,
                                                        () -> Player.getOnlinePlayer(args[2])
                                                                .kickPlayer(
                                                                        ChatColor.translateAlternateColorCodes(
                                                                                '&',
                                                                                Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_KICK
                                                                        )
                                                                )
                                                );
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_SUCCESS));
                                            }
                                        } else
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_ERROR_SQL));
                                    } else
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_ERROR_NOT_FOUND));
                                }
                            } else if (args.length == 5) {
                                // 更新
                                if (args[1].equalsIgnoreCase("change")) {
                                    if (args[2].equalsIgnoreCase("name")) {
                                        if (WhiteListDatabase.getNameList().contains(args[3])) {
                                            if (WhiteListDatabase.updateList(args[3], args[4])) {
                                                // 如果玩家在线，将玩家踢出
                                                if (
                                                        Player.getUUIDByName(args[3]) != null
                                                                && Player.getPlayer(args[3]).isOnline()
                                                ) {
                                                    Bukkit.getScheduler().runTask(
                                                            Configuration.plugin,
                                                            () -> Player.getOnlinePlayer(args[3])
                                                                    .kickPlayer(
                                                                            ChatColor.translateAlternateColorCodes(
                                                                                    '&',
                                                                                    Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_KICK
                                                                            )
                                                                    )
                                                    );
                                                }
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_SUCCESS));
                                            } else
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_ERROR_SQL));
                                        } else
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_ERROR_NOT_FOUND));
                                    } else if (args[2].equalsIgnoreCase("qq")) {
                                        if (WhiteListDatabase.getNameList().contains(args[3])) {
                                            if (WhiteListDatabase.updateList(Long.parseLong(args[3]), Long.parseLong(args[4]))) {
                                                // 如果玩家在线，将玩家踢出

                                                if (
                                                        Player.getUUIDByCode(Long.parseLong(args[3])) != null
                                                                && Player.getPlayer(Player.getUUIDByCode(Long.parseLong(args[3]))).isOnline()
                                                ) {
                                                    Bukkit.getScheduler().runTask(
                                                            Configuration.plugin,
                                                            () -> Player.getOnlinePlayer(Player.getUUIDByCode(Long.parseLong(args[3])))
                                                                    .kickPlayer(
                                                                            ChatColor.translateAlternateColorCodes(
                                                                                    '&',
                                                                                    Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_KICK
                                                                            )
                                                                    )
                                                    );
                                                }
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_SUCCESS));
                                            } else
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_ERROR_SQL));
                                        } else
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_ERROR_NOT_FOUND));
                                    }
                                }
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                        } else
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
                        break;
                    case "reload":
                        if (args.length == 1) {
                            if (sender.hasPermission("catsero.admin")) {
                                Configuration.plugin.reloadConfig();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.RELOAD.SUCCESS));
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                        }
                        break;
                    default:
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                }
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eCatSero &bby &eXiaMoHuaHuo_CN, version: " + Envrionment.plugin_version));

        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            if (args.length >= 1) {
                List<String> sublist = new ArrayList<>();
                if (args.length == 1) {
                    sublist.add("version");
                    sublist.add("reload");
                    sublist.add("whitelist");
                    return sublist;
                }
                if (args.length == 2 && args[0].equalsIgnoreCase("whitelist")) {
                    sublist.add("add");
                    sublist.add("remove");
                    sublist.add("change");
                    return sublist;
                }
                if (
                        args.length == 3
                                && args[0].equalsIgnoreCase("whitelist")
                ) {
                    if (args[1].equalsIgnoreCase("change")) {
                        sublist.add("name");
                        sublist.add("qq");
                        return sublist;
                    }
                }
                if (
                        args.length == 4
                                && args[0].equalsIgnoreCase("whitelist")
                ) {
                    if (args[1].equalsIgnoreCase("add")) {
                        sublist.add("QQ号");
                        return sublist;
                    }
                }
                return null;
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
    }

}
