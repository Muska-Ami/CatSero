/*
 * XMCore copyright XMNetwork with XiaMoHuaHuo_CN.
 */
package moe.xmcn.xmcore

import moe.xmcn.catsero.util.Config.plugin
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.logging.Level

class ThisAPI {
    companion object {

        private const val infoFile: String = "xmcore.info"

        fun readPlugin(plugin_name: String): YamlConfiguration {
            return YamlConfiguration.loadConfiguration(File(plugin.dataFolder.parent, "XMCore/$plugin_name.info"))
        }

        fun savePlugin(plugin_name: String) {
            File(plugin.dataFolder.parent, "XMCore/$plugin_name.info").delete()
            val outFile = File(plugin.dataFolder.parent.plus("/XMCore"), infoFile)
            val lastIndex: Int = infoFile.lastIndexOf(47.toChar())
            val outDir =
                File(
                    plugin.dataFolder.parent.plus("/XMCore"),
                    infoFile.substring(0, if (lastIndex >= 0) lastIndex else 0)
                )
            if (!outDir.exists()) {
                outDir.mkdirs()
            }
            val `in` = plugin.getResource(infoFile)
            try {
                val out: OutputStream = FileOutputStream(outFile)
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
                out.close()
                `in`.close()
                File(plugin.dataFolder.parent, "XMCore/$infoFile").renameTo(
                    File(
                        plugin.dataFolder.parent,
                        "XMCore/$plugin_name.info"
                    )
                )
                File(plugin.dataFolder.parent, "XMCore/$infoFile").delete()
            } catch (var10: IOException) {
                plugin.logger.log(
                    Level.INFO,
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        "&e[&dXMCore&e] &c无法保存插件信息\n" +
                                "插件名：$plugin_name\n" +
                                var10.toString() + "\n" +
                                var10.stackTrace
                    )
                )
            }
        }
    }
}
