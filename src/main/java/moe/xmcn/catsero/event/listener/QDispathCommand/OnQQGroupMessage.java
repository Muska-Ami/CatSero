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
package moe.xmcn.catsero.event.listener.QDispathCommand;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.QCommandParser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnQQGroupMessage implements Listener {

    /**
     * 遍历dispatchcmd后面的数组累加
     */
    public static String iterateArray(String[] items) {
        StringBuilder ifd = new StringBuilder();
        for (int i = 0; i < items.length - 1; i++) {
            ifd.append(items[i + 1]);
            ifd.append(" ");
        }
        return ifd.substring(0, ifd.length() - 1);
    }

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String[] args = QCommandParser.getParser.parse(event.getMessage());
        if (args != null) {
            if (Config.UsesConfig.getBoolean("qdispatch-command.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot && args[0].equals("dispatchcmd")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    //Bukkit.dispatchCommand() 执行命令，发送者为 ConsoleCommandSender
                    Bukkit.getScheduler().runTask(Config.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), iterateArray(args)));
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qdispatch-command.success"));
                } else {
                    //无OP权限
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }
}
