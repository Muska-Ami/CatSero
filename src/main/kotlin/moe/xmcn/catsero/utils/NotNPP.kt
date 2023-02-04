package moe.xmcn.catsero.utils

import moe.xmcn.catsero.CatSero
import moe.xmcn.catsero.Configuration
import org.bukkit.Bukkit
import java.util.*

class NotNPP : Runnable {
    override fun run() {
        if (
            System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")
            && CatSero.findProcess("notepad++.exe")
        ) {
            val fucknpp: List<String> = mutableListOf(
                "检测到Notepad++(Npp)，为保障您的信息安全，终止服务器！",
                "如需编辑，请更换其他编辑器：",
                "- Visual Studio Code：https://code.visualstudio.com/",
                "- Notepad--：https://gitee.com/cxasm/notepad--"
            )
            Logger.logWARN(fucknpp)
            Configuration.plugin.server.dispatchCommand(Bukkit.getConsoleSender(), "stop")
        }
    }

}