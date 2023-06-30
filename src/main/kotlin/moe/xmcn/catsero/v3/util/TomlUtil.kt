package moe.xmcn.catsero.v3.util

import org.tomlj.Toml
import org.tomlj.TomlParseResult
import java.io.InputStream


interface TomlUtil {

    companion object {

        /**
         * 获取Toml
         * @param file 文件
         */
        fun getTomlResult(file: InputStream): TomlParseResult {
            val result: TomlParseResult = Toml.parse(file)
            result.errors().forEach { Logger.catchEx(it) }
            return result
        }

    }

}