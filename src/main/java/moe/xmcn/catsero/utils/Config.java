package moe.xmcn.catsero.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import moe.xmcn.catsero.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Config文件读取
 * 规定相关设置变量
 */
public interface Config {
    Plugin plugin = Main.getPlugin(Main.class);
    FileConfiguration Config = plugin.getConfig();
    FileConfiguration UsesConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "usesconfig.yml"));

    long Use_Bot = Config.getLong("qq-set.bot");
    long Use_Group = Config.getLong("qq-set.group");
    long QQ_OP = Config.getLong("qq-set.qq-op");
    String Prefix_MC = Config.getString("format-list.prefix.to-mc") + ChatColor.translateAlternateColorCodes('&', "&r");
    String Prefix_QQ = Config.getString("format-list.prefix.to-qq");

    FileConfiguration PluginInfo = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getParent(), "XMCore/catsero.info"));

    /**
     * 尝试转为PlaceholderAPI文本
     * @param   player 玩家
     * @param   text 旧文本
     * @return  新文本
     */
    static String tryToPAPI(Player player, String text) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setBracketPlaceholders(player, text);
        } else {
            return text;
        }
    }

    /**
     * 尝试转为PlaceholderAPI文本
     * @param   player 发送者
     * @param   text 旧文本
     * @return  新文本
     */
    static String tryToPAPI(CommandSender player, String text) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Player pl = (Player) player;
            return PlaceholderAPI.setBracketPlaceholders(pl, text);
        } else {
            return text;
        }
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
        if (!new File(plugin.getDataFolder(), "locate/zh_CN.lang").exists()) {
            plugin.saveResource("locate/zh_CN.lang", false);
        }
    }

    /**
     * 获得回执文本
     * @param msid  文本MsID
     * @return      回执文本/undefined
     */
    static String getMsgByMsID(String msid) {
        String locate;
        if (Config.getString("locate") != null) {
            locate = Config.getString("locate");
        } else {
            locate = "zh_CN";
        }
        FileConfiguration messageData = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "locate/" + locate + ".lang"));
        if (messageData.getString(msid) != null) {
            return messageData.getString(msid);
        } else {
            return "undefined";
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
