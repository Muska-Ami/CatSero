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
package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Configuration {

    // 定义plugin
    Plugin plugin = CatSero.getPlugin(CatSero.class);

    class CFI {
        // File
        static File config_file = new File(plugin.getDataFolder(), "config.yml");
        static File usesconfig_file = new File(plugin.getDataFolder(), "uses-config.yml");
        static File mirai_bot_file = new File(plugin.getDataFolder(), "mirai-configs/bot.yml");
        static File mirai_group_file = new File(plugin.getDataFolder(), "mirai-configs/group.yml");
        static File mirai_qqop_file = new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml");
        public static File version_file = new File(plugin.getDataFolder(), "version");

        static FileConfiguration plugin_config = YamlConfiguration.loadConfiguration(config_file);
        static FileConfiguration uses_config = YamlConfiguration.loadConfiguration(usesconfig_file);
        static FileConfiguration bot_config = YamlConfiguration.loadConfiguration(mirai_bot_file);
        static FileConfiguration group_config = YamlConfiguration.loadConfiguration(mirai_group_file);
        static FileConfiguration qqop_config = YamlConfiguration.loadConfiguration(mirai_qqop_file);
    }

    // 配置定义
    interface PLUGIN {

        String LOCALE = CFI.plugin_config.getString("locale");
        boolean BSTATS = CFI.plugin_config.getBoolean("bstats");
        interface CHECK_UPDATE {
            /* 定义节点 为了区分
             用拼凑的`"check-update" + "."`
             而不是`"check-update."`
             */
            String sub_node = "check-update" + ".";

            boolean ENABLE = CFI.plugin_config.getBoolean(sub_node + "enable");
            int INTERVAL = CFI.plugin_config.getInt(sub_node + "interval");
            String API_URL = CFI.plugin_config.getString(sub_node + "api-url");
            String MODE = CFI.plugin_config.getString(sub_node + "mode");
        }
        interface CUSTOM_QQ_COMMAND_PREFIX {
            String sub_node = "custom-qq-command-prefix" + ".";

            boolean ENABLE = CFI.plugin_config.getBoolean(sub_node + "enable");
            String PREFIX = CFI.plugin_config.getString(sub_node + "prefix");
        }
    }

    interface USES_CONFIG {

    }

    static void saveFiles() {
        Logger.logLoader("Saving plugin & uses config...");
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) plugin.saveResource("config.yml", false);
        if (!new File(plugin.getDataFolder(), "uses-config.yml").exists()) plugin.saveResource("uses-config.yml", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving mirai-configs...");
        if (!new File(plugin.getDataFolder(), "mirai-configs/bot.yml").exists()) plugin.saveResource("mirai-configs/bot.yml", false);
        if (!new File(plugin.getDataFolder(), "mirai-configs/group.yml").exists()) plugin.saveResource("mirai-configs/group.yml", false);
        if (!new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml").exists()) plugin.saveResource("mirai-configs/qq-op.yml", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving default locale...");
        if (!new File(plugin.getDataFolder(), "locale/zh_CN.yml").exists()) plugin.saveResource("locale/zh_CN.yml", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving version...");
        plugin.saveResource("version", true);
        Logger.logLoader("Saved.");
    }
    static void reloadFiles() {
        Logger.logLoader("Saving files...");
        saveFiles();
        Logger.logLoader("Saved all files.");

        Logger.logLoader("Reloading files...");
        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "uses-config.yml"));

        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "mirai-configs/bot.yml"));
        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "mirai-configs/group.yml"));
        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml"));

        CFI.plugin_config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "locale" + PLUGIN.LOCALE + ".yml"));
        Logger.logLoader("Reloaded.");
    }

}
