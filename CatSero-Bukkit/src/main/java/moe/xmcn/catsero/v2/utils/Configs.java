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
package moe.xmcn.catsero.v2.utils;

import moe.xmcn.catsero.v2.CatSero;
import moe.xmcn.catsero.xmcore.ThisAPI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Configs {
    Plugin plugin = CatSero.getPlugin(CatSero.class);
    FileConfiguration PluginInfo = ThisAPI.Companion.readPlugin("CatSero");

    /**
     * 获取file-configs
     *
     * @param file extra-config文件名
     */
    static File getConfig(String file) {
        return new File(plugin.getDataFolder(), file);
    }

    /**
     * 获得回执文本
     *
     * @param msid 文本MsID
     * @return 回执文本/undefined
     */
    static String getMsgByMsID(String msid) {
        String locale;
        if (JPConfig.config.getString("locale") != null) {
            locale = JPConfig.config.getString("locale");
        } else {
            locale = "zh_CN";
        }
        FileConfiguration messageData = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "locale/" + locale + ".lang"));
        if (messageData.getString(msid) != null) {
            return messageData.getString(msid);
        } else {
            return "undefined";
        }
    }

    static long getBotCode(String botid) {
        return JPConfig.mirai_bot_config.getLong("list." + botid);
    }

    static long getGroupCode(String groupid) {
        return JPConfig.mirai_group_config.getLong("list." + groupid);
    }

    static boolean isQQOp(long senderID) {
        AtomicBoolean isOp = new AtomicBoolean(false);
        JPConfig.mirai_qqop_config.getLongList("list").forEach(it -> {
            if (it == senderID) {
                isOp.set(true);
            }
        });
        return isOp.get();
    }

    interface JPConfig {

        FileConfiguration config = YamlConfiguration.loadConfiguration(Configs.getConfig("config.yml"));
        FileConfiguration uses_config = YamlConfiguration.loadConfiguration(Configs.getConfig("uses-config.yml"));

        FileConfiguration trchat_config = YamlConfiguration.loadConfiguration(Configs.getConfig("extra-configs/trchat.yml"));

        FileConfiguration mirai_bot_config = YamlConfiguration.loadConfiguration(Configs.getConfig("mirai-configs/bot.yml"));
        FileConfiguration mirai_group_config = YamlConfiguration.loadConfiguration(Configs.getConfig("mirai-configs/group.yml"));
        FileConfiguration mirai_qqop_config = YamlConfiguration.loadConfiguration(Configs.getConfig("mirai-configs/qq-op.yml"));

        static void reload() throws IOException, InvalidConfigurationException {
            config.load(Configs.getConfig("config.yml"));
            uses_config.load(Configs.getConfig("uses-config.yml"));

            trchat_config.load(Configs.getConfig("extra-configs/trchat.yml"));

            mirai_bot_config.load(Configs.getConfig("mirai-configs/bot.yml"));
            mirai_group_config.load(Configs.getConfig("mirai-configs/group.yml"));
            mirai_qqop_config.load(Configs.getConfig("mirai-configs/qq-op.yml"));

        }

    }

}
