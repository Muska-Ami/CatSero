package moe.xmcn.catsero

import org.bukkit.plugin.Plugin

object Config {
    val plugin: Plugin = Main.getPlugin(Main::class.java)

    val Use_Bot = plugin.config.getLong("qbgset.bot")
    val Use_Group = plugin.config.getLong("qbgset.group")
    val QQ_OP = plugin.config.getLong("qbgset.qq-op")
    val Prefix_MC: String = plugin.config.getString("format-list.prefix.to-mc")
    val Prefix_QQ: String = plugin.config.getString("format-list.prefix.to-qq")
}