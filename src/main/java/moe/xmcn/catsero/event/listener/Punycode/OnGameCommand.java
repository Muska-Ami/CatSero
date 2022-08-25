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
package moe.xmcn.catsero.event.listener.Punycode;

import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.Punycode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class OnGameCommand {

    public static boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("punycode") && Config.UsesConfig.getBoolean("punycode.enabled")) {
            if (args.length == 3 && Config.UsesConfig.getBoolean("punycode.url-support") && args[2].equalsIgnoreCase("urlmode")) {
                //URL编码
                sender.sendMessage(Punycode.encodeURL(args[1]));
            } else if (args.length == 2) {
                //默认编码
                sender.sendMessage(Punycode.encode(args[1]));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_QQ + Config.getMsgByMsID("mc.punycode.too-many-or-low-options")));
            }
            return true;
        }
        return false;
    }

}
