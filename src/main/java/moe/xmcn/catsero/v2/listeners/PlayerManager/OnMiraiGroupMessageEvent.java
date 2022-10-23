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
package moe.xmcn.catsero.v2.listeners.PlayerManager;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.*;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMiraiGroupMessageEvent implements Listener {

    private boolean isSuccessRun = false;

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (
                    Configs.JPConfig.uses_config.getBoolean("player-manager.enabled") &&
                            e.getBotID() == Configs.getBotCode(Utils.X_Bot) &&
                            e.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                            strings != null &&
                            strings[0].equals("pm")
            ) {
                // 检查是否为QQOp
                if (Configs.isQQOp(e.getSenderID())) {
                    if (strings.length > 2) {
                        if (Utils.v.ban_tool) banTool(strings);
                        if (Utils.v.op_tool) opTool(strings);
                        if (Utils.v.kick_tool) kickTool(strings);
                    }
                    if (!isSuccessRun) {
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.invalid-options"), Utils.X_Bot, Utils.X_Group);
                    }
                } else
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.no-permission"), Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

    private void banTool(String[] strings) {
        switch (strings[1]) {
            case "ban":
                if (strings.length >= 3) {
                    if (Configs.JPConfig.uses_config.getBoolean("player-manager.configs.ban-tool.ban.custom-command.enabled")) {
                        // 自定义命令封禁
                        Bukkit.getScheduler().runTask(
                                Configs.plugin,
                                () -> {
                                    if (strings.length == 3) {
                                        // 没写原因
                                        Bukkit.dispatchCommand(
                                                Bukkit.getConsoleSender(),
                                                Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.ban.custom-command.command")
                                                        .replace("%player%", strings[2])
                                                        .replace("%reason%", ChatColor.translateAlternateColorCodes('&', Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.ban.default-reason")))
                                        );
                                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.ban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                                    } else {
                                        // 写了原因
                                        Bukkit.dispatchCommand(
                                                Bukkit.getConsoleSender(),
                                                Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.ban.custom-command.command")
                                                        .replace("%player%", strings[2])
                                                        .replace("%reason%", ChatColor.translateAlternateColorCodes('&', Utils.iterateArray(strings)))
                                        );
                                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.ban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                                    }
                                }
                        );
                    } else {
                        // Bukkit内置封禁
                        if (strings.length == 3) {
                            // 没写原因
                            Bukkit.getBanList(BanList.Type.NAME).addBan(
                                    strings[2],
                                    ChatColor.translateAlternateColorCodes('&', Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.ban.default-reason")),
                                    null,
                                    null
                            );
                            if (Players.getPlayer(strings[2]).isOnline()) {
                                Bukkit.getScheduler().runTask(
                                        Configs.plugin,
                                        () -> Players.getOnlinePlayer(strings[2]).kickPlayer(
                                                ChatColor.translateAlternateColorCodes('&', Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.ban.default-reason"))
                                        )
                                );
                            }
                            Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.ban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                        } else {
                            // 写了原因
                            Bukkit.getBanList(BanList.Type.NAME).addBan(
                                    strings[2],
                                    ChatColor.translateAlternateColorCodes('&', strings[3]),
                                    null,
                                    null
                            );
                            if (Players.getPlayer(strings[2]).isOnline()) {
                                Bukkit.getScheduler().runTask(
                                        Configs.plugin,
                                        () -> Players.getOnlinePlayer(strings[2]).kickPlayer(
                                                ChatColor.translateAlternateColorCodes('&', Utils.iterateArray(strings))
                                        )
                                );
                            }
                            Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.ban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                        }
                    }
                    isSuccessRun = true;
                }
                break;
            case "unban":
            case "pardon":
                if (strings.length == 3) {
                    if (Configs.JPConfig.uses_config.getBoolean("player-manager.configs.ban-tool.unban.custom-command.enabled")) {
                        // 自定义命令
                        Bukkit.getScheduler().runTask(Configs.plugin, () -> Bukkit.dispatchCommand(
                                Bukkit.getConsoleSender(),
                                Configs.JPConfig.uses_config.getString("player-manager.configs.ban-tool.unban")
                                        .replace("%player%", strings[2])
                        ));
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.unban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    } else {
                        // Bukkit内置
                        Bukkit.getBanList(BanList.Type.NAME).pardon(strings[2]);
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.unban.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    }
                    isSuccessRun = true;
                }
                break;
        }
    }

    private void opTool(String[] strings) {
        switch (strings[1]) {
            case "op":
                if (strings.length == 3) {
                    if (Players.getPlayer(strings[2]).isOp()) {
                        // 已是OP
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.op.already-is-op").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    } else {
                        // 不是OP
                        Players.getPlayer(strings[2]).setOp(true);
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.op.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    }
                    isSuccessRun = true;
                }
                break;
            case "unop":
            case "deop":
                if (strings.length == 3) {
                    if (!Players.getPlayer(strings[2]).isOp()) {
                        // 不是OP
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.unop.is-not-op").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    } else {
                        // 已是OP
                        Players.getPlayer(strings[2]).setOp(false);
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.unop.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    }
                    isSuccessRun = true;
                }
                break;
        }
    }

    private void kickTool(String[] strings) {
        if (strings[1].equals("kick")) {
            if (strings.length >= 3) {
                if (strings.length == 3) {
                    // 未写原因
                    if (Players.getPlayer(strings[2]).isOnline()) {
                        // 玩家在线
                        Players.getOnlinePlayer(strings[2]).kickPlayer(Configs.JPConfig.uses_config.getString("player-manager.configs.kick-tool.kick.default-reason"));
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.kick.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    } else {
                        // 玩家不在线
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.kick.not-online").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    }
                } else {
                    // 写了原因
                    if (Players.getPlayer(strings[2]).isOnline()) {
                        // 玩家在线
                        Players.getOnlinePlayer(strings[2]).kickPlayer(Utils.iterateArray(strings));
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.kick.success").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    } else {
                        // 玩家不在线
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.kick.not-online").replace("%player%", strings[2]), Utils.X_Bot, Utils.X_Group);
                    }
                }
                isSuccessRun = true;
            }
        }
    }

}
