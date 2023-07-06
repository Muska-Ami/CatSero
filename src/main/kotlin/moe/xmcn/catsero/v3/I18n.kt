package moe.xmcn.catsero.v3

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import moe.xmcn.catsero.v3.util.Logger
import java.nio.file.Files
import java.nio.file.Paths

interface I18n {

    companion object {

        private val lang: String?
            get() = Configuration.pluginConfig.getString("plugin . lang")

        private val messageJSON: JSONObject
            get() = JSON.parseObject(Files.readString(Paths.get("${CatSero.INSTANCE.dataFolder}/lang/$lang/message.json")))
        private val formatJSON: JSONObject
            get() = JSON.parseObject(Files.readString(Paths.get("${CatSero.INSTANCE.dataFolder}/lang/$lang/format.json")))

        /**
         * 获取消息
         * @param s 节点
         * @return 消息
         */
        fun getMessage(s: String): String {
            return read(s, messageJSON)
        }

        /**
         * 获取格式
         * @param s 节点
         * @return 消息
         */
        fun getFormat(s: String): String {
            return read(s, formatJSON)
        }

        private fun read(node: String, data: JSONObject): String {
            return try {
                val nodes = node.split(".")
                var resx = JSONObject()
                for (i in 1 until nodes.size) {
                    resx = data.getJSONObject(nodes[i - 1])
                }
                resx.getString(nodes[nodes.size - 1]) ?: "undefined"
            } catch (e: Exception) {
                Logger.catchEx(e)
                "undefined"
            }
        }

    }

}