package moe.xmcn.xmcore

import moe.xmcn.catsero.utils.Config.plugin
import org.bukkit.ChatColor
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.logging.Level

class ThisAPI {
    companion object {
        fun savXMCore(infoFile: String) {
            File(plugin.dataFolder.parent, "XMCore/catsero.info").delete()
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
                File(plugin.dataFolder.parent, "XMCore/xmcore.info").renameTo(File(plugin.dataFolder.parent, "XMCore/catsero.info"))
                File(plugin.dataFolder.parent,"XMCore/xmcore.info").delete()
            } catch (var10: IOException) {
                plugin.logger.log(
                    Level.INFO,
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        "&e[&dXMCore&e] &c无法保存插件信息" + var10.toString() + "\n" + var10.stackTrace
                    )
                )
            }
        }
    }
}
