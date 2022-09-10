package moe.xmcn.catsero.v2.utils;

import moe.xmcn.catsero.v2.CatSero;
import moe.xmcn.xmcore.ThisAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Configs {
    Plugin plugin = CatSero.getPlugin(CatSero.class);
    FileConfiguration PluginInfo = ThisAPI.Companion.readPlugin("CatSero");

    /**
     * 获取file-configs
     *
     * @param file extra-config文件名
     */
    static FileConfiguration getConfig(String file) {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), file));
    }

    /**
     * 获得回执文本
     *
     * @param msid 文本MsID
     * @return 回执文本/undefined
     */
    static String getMsgByMsID(String msid) {
        String locale;
        if (getConfig("config.yml").getString("locale") != null) {
            locale = getConfig("config.yml").getString("locale");
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

}
