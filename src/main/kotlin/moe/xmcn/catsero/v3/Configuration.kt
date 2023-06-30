package moe.xmcn.catsero.v3

import moe.xmcn.catsero.v3.util.Logger
import java.io.File

interface Configuration {

    companion object {

        /**
         * 保存文件
         */
        fun saveConfig() {
            val file = listOf(
                "config.toml",
                "lang/zh_CN.json"
            )
            file.forEach {
                if (!File(CatSero.INSTANCE.dataFolder.toString() + "/$it").exists()) {
                    Logger.info("保存文件: $it")
                    CatSero.INSTANCE.saveResource(it, false)
                }
            }
        }

    }

}