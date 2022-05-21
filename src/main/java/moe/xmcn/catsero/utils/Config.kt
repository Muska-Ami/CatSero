package moe.xmcn.catsero.utils

import moe.xmcn.catsero.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

object Config {

    val plugin: Plugin = Main.getPlugin(Main::class.java)
    var UsesConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "usesconfig.yml"))
    fun customConfig(config: String): FileConfiguration {
        return YamlConfiguration.loadConfiguration(File(plugin.dataFolder, config))
    }

    val Use_Bots: List<Long> = plugin.config.getLongList("qq-set.bots")
    val Use_Groups: List<Long> = plugin.config.getLongList("qq-set.groups")
    val QQ_OPs: List<Long> = plugin.config.getLongList("qq-set.qq-ops")
    val Prefix_MC: String = plugin.config.getString("format-list.prefix.to-mc")
    val Prefix_QQ: String = plugin.config.getString("format-list.prefix.to-qq")

    val Version: String = plugin.config.getString("version")

    fun reloadConfig() {
        plugin.reloadConfig()
        UsesConfig = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "usesconfig.yml"))
    }
}
