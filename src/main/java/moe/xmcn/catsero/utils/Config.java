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
package moe.xmcn.catsero.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.Main;
import moe.xmcn.xmcore.ThisAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;

/**
 * Config文件读取
 * 规定相关设置变量
 */
public interface Config {
    Plugin plugin = Main.getPlugin(Main.class);
    FileConfiguration Config = plugin.getConfig();
    long Use_Bot = Config.getLong("qq-set.bot");
    long Use_Group = Config.getLong("qq-set.group");
    long QQ_OP = Config.getLong("qq-set.qq-op");
    String Prefix_MC = Config.getString("format-list.prefix.to-mc") + ChatColor.translateAlternateColorCodes('&', "&r");
    String Prefix_QQ = Config.getString("format-list.prefix.to-qq");
    FileConfiguration UsesConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "usesconfig.yml"));
    FileConfiguration PluginInfo = ThisAPI.Companion.readPlugin("CatSero");

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 玩家
     * @param text   旧文本
     * @return 新文本
     */
    static String tryToPAPI(Player player, String text) {
        if (LibChecker.PlaceholderAPI) {
            return PlaceholderAPI.setBracketPlaceholders(player, text);
        }
        return text;
    }

    /**
     * 尝试转为PlaceholderAPI文本
     *
     * @param player 发送者
     * @param text   旧文本
     * @return 新文本
     */
    static String tryToPAPI(CommandSender player, String text) {
        if (LibChecker.PlaceholderAPI) {
            Player pl = (Player) player;
            return PlaceholderAPI.setBracketPlaceholders(pl, text);
        }
        return text;
    }

    /**
     * 保存默认的文件
     */
    static void saveDefFile() {
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveResource("config.yml", false);
        }
        if (!new File(plugin.getDataFolder(), "usesconfig.yml").exists()) {
            plugin.saveResource("usesconfig.yml", false);
        }

        // 把语言文件保存也塞这里了 XD
        if (!new File(plugin.getDataFolder(), "locale/zh_CN.lang").exists()) {
            plugin.saveResource("locale/zh_CN.lang", false);
        }
        ThisAPI.Companion.savePlugin("CatSero");
    }

    /**
     * 获得回执文本
     *
     * @param msid 文本MsID
     * @return 回执文本/undefined
     */
    static String getMsgByMsID(String msid) {
        String locale;
        if (Config.getString("locale") != null) {
            locale = Config.getString("locale");
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

    static void sendMiraiGroupMessage(String message) {
        try {
            MiraiBot.getBot(Use_Bot).getGroup(Use_Group).sendMessageMirai(message);
        } catch (Exception e) {
            plugin.getLogger().warning(getMsgByMsID("general.send-message-qq-error").replace("%error%", e + Arrays.toString(e.getStackTrace())));
        }
    }

    /**
     * 重载配置文件
     */
    static void reloadConfig() {
        plugin.reloadConfig();
        UsesConfig.setDefaults(YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "usesconfig.yml")));
    }

}
