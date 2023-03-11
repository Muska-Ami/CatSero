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
package moe.xmcn.catsero.executors.cms;

import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnCommand implements TabExecutor {

    private final I18n i18n = new I18n();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (sender.hasPermission("catsero.cms")) {
                if (args.length >= 3) {
                    StringBuilder message = new StringBuilder();
                    for (int i = 2; i <= args.length - 1; i++) {
                        message.append(args[i])
                                .append(" ");
                    }
                    message = new StringBuilder(message.substring(0, message.length() - 1));
                    MessageSender.sendGroup(message.toString(), args[0], args[1]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                            "minecraft", "command", "cms", "sent"
                    )))));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                            "minecraft", "command", "invalid-option"
                    )))));
                }
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                        "minecraft", "command", "no-permission"
                )))));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                    "minecraft", "command", "cms", "error"
            )))));
            Logger.logCatch(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            if (args.length >= 1) {
                List<String> sublist = new ArrayList<>();
                switch (args.length) {
                    case 1:
                        sublist.add("BotID");
                        return sublist;
                    case 2:
                        sublist.add("GroupID");
                        return sublist;
                    case 3:
                        sublist.add("Message");
                        return sublist;
                    default:
                        return null;
                }
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
    }
}
