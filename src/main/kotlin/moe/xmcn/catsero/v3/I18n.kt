package moe.xmcn.catsero.v3

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.nio.file.Files
import java.nio.file.Paths

interface I18n {

    companion object {

        val lang: String?
            get() = Configuration.getPluginConfig().getString("plugin . lang")

        val messageJSON: JSONObject
            get() = JSON.parseObject(Files.readString(Paths.get("${CatSero.INSTANCE.dataFolder}/lang/$lang/message.json")))
        val formatJSON: JSONObject
            get() = JSON.parseObject(Files.readString(Paths.get("${CatSero.INSTANCE.dataFolder}/lang/$lang/format.json")))

        /**
         * 获取消息
         * @param s 节点
         * @return 消息
         */
        fun getMessage(s: String): String? {
            return read(s, messageJSON)
        }

        /**
         * 获取格式
         * @param s 节点
         * @return 消息
         */
        fun getFormat(s: String): String? {
            return read(s, formatJSON)
        }

        private fun read(node: String, data: JSONObject): String? {
            val nodes = node.split(".")
            var resx: JSONObject = JSONObject()
            for (i in 1 until nodes.size) {
                resx = data.getJSONObject(nodes[i - 1])
            }
            return resx.getString(nodes[nodes.size - 1])
        }

    }

}