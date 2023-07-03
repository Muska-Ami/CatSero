package moe.xmcn.catsero.v3

import moe.xmcn.catsero.v3.util.Logger
import moe.xmcn.catsero.v3.util.TomlUtil
import org.bukkit.Bukkit
import org.tomlj.TomlParseResult
import java.io.File

interface Configuration {

    // 定义依赖项目是否存在
    class Depend {

        companion object {

            var MiraiMC = false
            var PlaceholderAPI = false

        }
    }

    companion object {

        /**
         * 获取插件配置文件
         */
        fun getPluginConfig(): TomlParseResult {
            return TomlUtil.getTomlResult(CatSero.INSTANCE.getResource("config.toml"))
        }

        /**
         * 获取功能配置文件
         */
        fun getUsesConfig(): TomlParseResult {
            return TomlUtil.getTomlResult(CatSero.INSTANCE.getResource("use-config.toml"))
        }

        /**
         * 获取Bot
         * @param id BotID
         * @return BotCode
         */
        fun getBot(id: String): Long? {
            return TomlUtil.getTomlResult(CatSero.INSTANCE.getResource("mirai.toml")).getLong("bot . $id")
        }

        /**
         * 获取Group
         * @param id GroupID
         * @return GroupCode
         */
        fun getGroup(id: String): Long? {
            return TomlUtil.getTomlResult(CatSero.INSTANCE.getResource("mirai.toml")).getLong("group . $id")
        }

        /**
         * 保存文件
         */
        fun saveConfig() {
            val file = listOf(
                "config.toml",
                "mirai.toml",
                "use-config.toml",
                "lang/zh_CN/message.json",
                "lang/zh_CN/format.json"
            )
            file.forEach {
                if (!File(CatSero.INSTANCE.dataFolder.toString() + "/$it").exists()) {
                    Logger.info("保存文件: $it")
                    CatSero.INSTANCE.saveResource(it, false)
                }
            }
        }

        /**
         * 初始化环境变量
         */
        fun loadEnv() {
            if (isPluginInstall("MiraiMC")) {
                Depend.MiraiMC = true
            }
            if (isPluginInstall("PlaceholderAPI")) {
                Depend.PlaceholderAPI = true
            }

            val authorsbr = StringBuilder()
            CatSero.INSTANCE.description.authors.forEach {
                authorsbr.append("$it, ")
            }
            val authors = authorsbr.substring(0, authorsbr.length - 2)

            val info = listOf(
                "------ CatSero v3 ------",
                "版本: ${CatSero.INSTANCE.description.version}",
                "服务器: ${Bukkit.getBukkitVersion()} - ${Bukkit.getVersion()}",
                "",
                "CatSero v${CatSero.INSTANCE.description.version}",
                "由 $authors 制作",
                "----------------------------"
            )
            Logger.info(info)
        }

        private fun isPluginInstall(n: String): Boolean {
            return Bukkit.getPluginManager().getPlugin(n) != null
        }

    }

}