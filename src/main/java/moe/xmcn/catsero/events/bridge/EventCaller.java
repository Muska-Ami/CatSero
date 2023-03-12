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
package moe.xmcn.catsero.events.bridge;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.events.OnQQFriendCommandEvent;
import moe.xmcn.catsero.events.OnQQGroupCommandEvent;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class EventCaller implements Listener {

    private final ParseTool pt = new ParseTool();

    @EventHandler
    public void onGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            if (pt.parse(e.getMessage()))
                callGroupCommandEvent(pt.getCommand(), Arrays.asList(pt.getArguments()), pt.getLabel(), e.getSenderID(), e.getGroupID(), e.getBotID(), pt.isCustom());
            Logger.logDebug("获得一个群消息");
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    @EventHandler
    public void onGroupMessageEvent(MiraiFriendMessageEvent e) {
        try {
            if (pt.parse(e.getMessage()))
                callFriendCommandEvent(pt.getCommand(), Arrays.asList(pt.getArguments()), pt.getLabel(), e.getSenderID(), e.getFriend().getID(), e.getBotID(), pt.isCustom());
            Logger.logDebug("获得一个私聊消息");
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    void callGroupCommandEvent(String command, List<String> arguments, String label, long sender, long group, long bot, boolean custom) {
        Logger.logDebug("Call -> 群命令事件");
        Bukkit.getScheduler().runTask(
                CatSero.INSTANCE,
                () -> CatSero.INSTANCE.getServer().getPluginManager().callEvent(new OnQQGroupCommandEvent(
                        command, arguments, label, sender, group, bot, custom
                ))
        );
    }

    void callFriendCommandEvent(String command, List<String> arguments, String label, long sender, long friend, long bot, boolean custom) {
        Logger.logDebug("Call -> 私聊命令事件");
        Bukkit.getScheduler().runTask(
                CatSero.INSTANCE,
                () -> CatSero.INSTANCE.getServer().getPluginManager().callEvent(new OnQQFriendCommandEvent(
                        command, arguments, label, sender, friend, bot, custom
                ))
        );
    }
}
