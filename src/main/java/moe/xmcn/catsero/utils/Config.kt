package moe.xmcn.catsero.utils

import me.clip.placeholderapi.PlaceholderAPI
import moe.xmcn.catsero.Main
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Config文件读取
 * 规定相关设置变量
 */
object Config {

    val plugin: Plugin = Main.getPlugin(Main::class.java)
    var UsesConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "usesconfig.yml"))

    val Use_Bots: List<Long> = plugin.config.getLongList("qq-set.bots")
    val Use_Groups: List<Long> = plugin.config.getLongList("qq-set.groups")
    val QQ_OPs: List<Long> = plugin.config.getLongList("qq-set.qq-ops")
    val Prefix_MC: String = plugin.config.getString("format-list.prefix.to-mc")
    val Prefix_QQ: String = plugin.config.getString("format-list.prefix.to-qq")

    val Version: String = plugin.config.getString("version")

    /**
     * 尝试转为PlaceholderAPI文本
     * @param player 玩家
     * @param text 旧文本
     * @return 新文本
     */
    fun tryToPAPI(player: Player, text: String): String? {
        return if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI.setBracketPlaceholders(player, text)
        } else {
            text
        }
    }

    /**
     * 保存默认的配置文件
     */
    fun saveDefConfig() {
        if (!File(plugin.dataFolder, "config.yml").exists()) {
            plugin.saveResource("config.yml", false)
        }
        if (!File(plugin.dataFolder, "usesconfig.yml").exists()) {
            plugin.saveResource("usesconfig.yml", false)
        }
    }

    /**
     * 重载配置文件
     */
    fun reloadConfig() {
        plugin.reloadConfig()
        UsesConfig = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "usesconfig.yml"))
    }

}
