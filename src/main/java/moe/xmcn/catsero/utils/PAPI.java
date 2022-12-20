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
package moe.xmcn.catsero.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PAPI {

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 玩家
     * @param text   旧文本
     * @return 新文本
     */
    public static String toPAPI(Player player, String text) {
        if (Envrionment.Depends.PlaceholderAPI) {
            return PlaceholderAPI.setBracketPlaceholders(player, text);
        }
        return text;
    }

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 命令发送者
     * @param text   旧文本
     * @return 新文本
     */
    public static String toPAPI(CommandSender player, String text) {
        if (Envrionment.Depends.PlaceholderAPI) {
            Player pl = (Player) player;
            return PlaceholderAPI.setBracketPlaceholders(pl, text);
        }
        return text;
    }

}
