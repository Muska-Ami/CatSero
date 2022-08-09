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
package moe.xmcn.catsero.events.listeners.ChatForward;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnGameChat implements Listener {

    @EventHandler
    public void onAsyncGameChat(AsyncPlayerChatEvent event) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled")) {
            String playermessage = event.getMessage();
            String playername = event.getPlayer().getDisplayName();
            String message = Config.UsesConfig.getString("forward-chat.format.to-qq");
            message = message.replace("%player%", playername)
                    .replace("%message%", playermessage);
            message = Config.tryToPAPI(event.getPlayer(), message);
            message = tryRemoveColorCode(message);

            if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled")) {
                if (message.startsWith(Config.UsesConfig.getString("forward-chat.prefix.format.to-qq"))) {
                    Config.sendMiraiGroupMessage(message);
                }
            } else {
                Config.sendMiraiGroupMessage(message);
            }
        }
    }

    private String tryRemoveColorCode(String message) {
        if (Config.UsesConfig.getBoolean("forward-chat.clean-colorcode")) {
            return message.replace("§1", "")
                    .replace("§2", "")
                    .replace("§3", "")
                    .replace("§4", "")
                    .replace("§5", "")
                    .replace("§6", "")
                    .replace("§7", "")
                    .replace("§8", "")
                    .replace("§9", "")
                    .replace("§0", "")
                    .replace("§a", "")
                    .replace("§b", "")
                    .replace("§c", "")
                    .replace("§d", "")
                    .replace("§e", "")
                    .replace("§f", "")
                    .replace("§k", "")
                    .replace("§l", "")
                    .replace("§m", "")
                    .replace("§n", "")
                    .replace("§o", "")
                    .replace("§r", "");
        } else {
            return message;
        }
    }

}
