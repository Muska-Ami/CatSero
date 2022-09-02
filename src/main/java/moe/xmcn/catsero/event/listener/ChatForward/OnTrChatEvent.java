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
package moe.xmcn.catsero.event.listener.ChatForward;

import me.arasple.mc.trchat.api.event.TrChatEvent;
import moe.xmcn.catsero.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnTrChatEvent implements Listener {

    FileConfiguration ExtConfig = Config.extraConfigs("trchat.yml");

    @EventHandler
    public void onTrChatEvent(TrChatEvent event) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled")) {
            String channel = event.getChannel().getId();
            String playermessage = event.getMessage();
            String playername = event.getSession().getPlayer().getName();

            ExtConfig.getStringList("forward-chat.channels").forEach(it -> {
                if (it.equals(channel)) {
                    String message = ExtConfig.getString("forward-chat.format.to-qq");
                    message = message.replace("%player%", playername)
                            .replace("%message%", playermessage)
                            .replace("%channel%", it);
                    message = Config.tryToPAPI(event.getSession().getPlayer(), message);
                    if (Config.UsesConfig.getBoolean("forward-chat.clean-colorcode")) {
                        message = Utils.removeColorCode(message);
                    }

                    if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled")) {
                        //启用了Prefix
                        if (message.startsWith(Config.UsesConfig.getString("forward-chat.prefix.format.to-qq"))) {
                            Config.sendMiraiGroupMessage(message);
                        }
                    } else {
                        Config.sendMiraiGroupMessage(message);
                    }
                }
            });
        }
    }

}