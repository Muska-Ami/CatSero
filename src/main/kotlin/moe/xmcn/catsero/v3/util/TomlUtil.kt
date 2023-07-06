package moe.xmcn.catsero.v3.util

import moe.xmcn.catsero.v3.CatSero
import org.tomlj.Toml
import org.tomlj.TomlParseResult
import java.nio.file.Paths


interface TomlUtil {

    companion object {

        /**
         * 获取Toml
         * @param file 文件
         */
        fun getTomlResult(file: String): TomlParseResult {
            val result: TomlParseResult = Toml.parse(Paths.get("${CatSero.INSTANCE.dataFolder}/$file"))
            result.errors().forEach { Logger.catchEx(it) }
            return result
        }

    }

}