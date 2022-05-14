package moe.xmcn.catsero.utils

import moe.xmcn.catsero.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

object Config {
    val plugin: Plugin = Main.getPlugin(Main::class.java)
    val UsesConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "usesconfig.yml"))
    fun customConfig(config: String): FileConfiguration {
        val customConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, config))
        return customConfig
    }

    val Use_Bot = plugin.config.getLong("qbgset.bot")
    val Use_Group = plugin.config.getLong("qbgset.group")
    val QQ_OP = plugin.config.getLong("qbgset.qq-op")
    val Prefix_MC: String = plugin.config.getString("format-list.prefix.to-mc")
    val Prefix_QQ: String = plugin.config.getString("format-list.prefix.to-qq")

    val Version: String = plugin.config.getString("version")
}
