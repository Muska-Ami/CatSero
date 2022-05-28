package moe.xmcn.catsero.utils

import com.google.common.base.Charsets
import me.clip.placeholderapi.PlaceholderAPI
import moe.xmcn.catsero.Main
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.InputStreamReader

/**
 * Config文件读取
 * 规定相关设置变量
 */
object Config {

    val plugin: Plugin = Main.getPlugin(Main::class.java)
    var Config: FileConfiguration = plugin.config
    var UsesConfig: FileConfiguration = YamlConfiguration.loadConfiguration(InputStreamReader(plugin.getResource("usesconfig.yml"), Charsets.UTF_8))

    val Use_Bot: Long = plugin.config.getLong("qq-set.bot")
    val Use_Group: Long = plugin.config.getLong("qq-set.group")
    val QQ_OP: Long = plugin.config.getLong("qq-set.qq-op")
    val Prefix_MC: String = plugin.config.getString("format-list.prefix.to-mc")
    val Prefix_QQ: String = plugin.config.getString("format-list.prefix.to-qq")

    val Version: String = plugin.config.getString("version")

    /**
     * 尝试转为PlaceholderAPI文本
     * @param   player 玩家
     * @param   text 旧文本
     * @return  新文本
     */
    fun tryToPAPI(player: Player, text: String): String {
        return if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI.setBracketPlaceholders(player, text)
        } else {
            text
        }
    }
    fun tryToPAPI(player: CommandSender, text: String): String {
        return if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            val pl = player as Player
            PlaceholderAPI.setBracketPlaceholders(pl, text)
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
        if (!File(plugin.dataFolder, "locate/zh_CN.lang").exists()) {
            plugin.saveResource("locate/zh_CN.lang", false)
        }
    }

    /**
     * 获得回执文本
     * @param msid  文本MsID
     */
    fun getMsgByMsID(msid: String): String {
        val locate = if (Config.getString("locate") != null) {
            Config.getString("locate")
        } else {
            "zh_CN"
        }
        val messageData: FileConfiguration = YamlConfiguration.loadConfiguration(InputStreamReader(plugin.getResource("locate/$locate.lang"), Charsets.UTF_8))
        return if (messageData.getString(msid) != null) {
            messageData.getString(msid)
        } else {
            "undefined"
        }
    }

    /**
     * 重载配置文件
     */
    fun reloadConfig() {
        plugin.reloadConfig()
        UsesConfig.defaults = YamlConfiguration.loadConfiguration(InputStreamReader(plugin.getResource("usesconfig.yml"), Charsets.UTF_8))
    }

}
