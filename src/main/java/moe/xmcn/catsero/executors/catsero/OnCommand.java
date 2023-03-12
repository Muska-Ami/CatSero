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

import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.Logger;
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
            if (args.length >= 1) {
                switch (args[0]) {
                    case "version":
                        new version().command(sender, args);
                        break;
                    case "whitelist":
                        new whitelist().command(sender, args);
                        break;
                    case "reload":
                        new reload().command(sender, args);
                        break;
                    case "debug":
                        new debug().command(sender, args);
                        break;
                    default:
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "command", "invalid-option"
                        )))));
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
