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
package moe.xmcn.catsero.events.listeners.OPPlayerQQ;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import moe.xmcn.catsero.utils.QCommandParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.ServerOperator;

import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent0(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "setop") && Config.UsesConfig.getBoolean("qop-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            if (event.getSenderID() == Config.QQ_OP) {
                //有OP权限
                ServerOperator plu = Players.getPlayer(args[2]);
                boolean isOp = plu.isOp();
                if (isOp) {
                    //玩家是OP
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-is-op"));
                } else {
                    //玩家不是OP
                    plu.setOp(true);
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.success-add"));
                }
            } else {
                //无OP权限
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
            }
        }
    }

    @EventHandler
    public void onMiraiGroupMessageEvent1(MiraiGroupMessageEvent event) {
        String[] args = QCommandParser.getParser.parse(event.getMessage());
        if (!(args == null)) {
            if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "removeop") && Config.UsesConfig.getBoolean("qop-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
                if (event.getSenderID() == Config.QQ_OP) {
                    //有OP权限
                    ServerOperator plu = Players.getPlayer(args[2]);
                    boolean isOp = plu.isOp();
                    if (!isOp) {
                        //玩家不是OP
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-not-op"));
                    } else {
                        //玩家是OP
                        plu.setOp(false);
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.success-remove"));
                    }
                } else {
                    //无OP权限
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }

}