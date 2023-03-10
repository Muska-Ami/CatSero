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

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import moe.xmcn.catsero.Configuration;

import java.io.IOException;

public class MessageSender {

    public static void sendGroup(String message, String bot, String group) throws IOException {
        if (Configuration.PLUGIN.HTTP_API)
            MiraiHttpAPI.INSTANCE.sendGroupMessage(
                    MiraiHttpAPI.Bots.get(Configuration.Interface.getBotCode(bot)),
                    Configuration.Interface.getGroupCode(group),
                    message
            );
        else
            MiraiBot.getBot(Configuration.Interface.getBotCode(bot)).getGroup(Configuration.Interface.getGroupCode(group)).sendMessageMirai(message);
    }

    public static void sendFriend(String message, String bot, long friend) throws IOException {
        if (Configuration.PLUGIN.HTTP_API)
            MiraiHttpAPI.INSTANCE.sendFriendMessage(
                    MiraiHttpAPI.Bots.get(Configuration.Interface.getBotCode(bot)),
                    friend,
                    message
            );
        else
            MiraiBot.getBot(Configuration.Interface.getBotCode(bot)).getFriend(friend).sendMessageMirai(message);
    }

}
