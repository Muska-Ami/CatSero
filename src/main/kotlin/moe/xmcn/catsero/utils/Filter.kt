package moe.xmcn.catsero.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import moe.xmcn.catsero.Configuration
import java.nio.file.Files
import java.nio.file.Paths

class Filter : Runnable {

    class CHAT_FORWARD {

        private val fullQQWords = ArrayList<String>()
        private val fullMCWords = ArrayList<String>()

        val MCWords: ArrayList<String>
            get() = fullMCWords
        val QQWords: ArrayList<String>
            get() = fullQQWords

        fun updateList() {
            val remoteUrls = Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.IMPORT.REMOTE
            val localFiles = Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.IMPORT.LOCAL
            val publicWords = ArrayList<String>()
            val mcWords = Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.VIA.TO_MC
            val qqWords = Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.VIA.TO_QQ

            val hc = HttpClient()

            var jsonData: JSONArray? = null

            localFiles.forEach { path ->
                run {
                    //读取文件
                    val lines = Files.readAllLines(Paths.get(path))
                    val data = StringBuilder()
                    lines.forEach(
                        data::append
                    )

                    try {
                        jsonData = JSON.parseObject(data.toString()).getJSONArray("words")
                    } catch (e: Exception) {
                        Logger.logTask("无法解析本地源屏蔽词数据: " + e.message + " | 源: " + path)
                    }

                    if (jsonData != null) {
                        // 遍历并添加屏蔽词
                        val wordsLength = jsonData!!.toArray().size
                        for (i in 0 until wordsLength) {
                            publicWords.add(jsonData!![i].toString())
                        }
                        jsonData = null
                    }
                }
            }
            remoteUrls.forEach { url ->
                run {
                    var res: String? = null
                    try {
                        res = hc.getRequest(url)
                    } catch (e: Exception) {
                        Logger.logTask("无法请求远程源屏蔽词: " + e.message + " | 源: " + url)
                    }

                    if (res != null) {
                        try {
                            jsonData = JSON.parseObject(res).getJSONArray("words")
                        } catch (e: Exception) {
                            Logger.logTask("无法解析远程源屏蔽词数据: " + e.message + " | 源: " + url)
                        }

                        if (jsonData != null) {
                            // 遍历并添加屏蔽词
                            val wordsLength = jsonData!!.toArray().size
                            for (i in 0 until wordsLength) {
                                publicWords.add(jsonData!![i].toString())
                            }
                            jsonData = null
                        }
                    }
                }
            }

            fullMCWords.addAll(mcWords)
            fullMCWords.addAll(publicWords)
            fullQQWords.addAll(qqWords)
            fullQQWords.addAll(publicWords)

            if (lastImportLength != publicWords.size) {
                Logger.logTask("成功更新了 " + (publicWords.size - lastImportLength) + " 个屏蔽词，当前屏蔽词数量: " + publicWords.size)
                Logger.logDebug("屏蔽词列表: $publicWords")
            }
        }
    }

    override fun run() {
        CHAT_FORWARD().updateList()
    }

    companion object {
        @JvmStatic
        private val lastImportLength: Int = 0
        @JvmStatic
        fun startUpdate() {
            // 聊天转发
            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.AUTO_UPDATE.ENABLE)
                Configuration.plugin.server.scheduler.runTaskTimerAsynchronously(
                    Configuration.plugin,
                    Filter(),
                    0L,
                    Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.AUTO_UPDATE.INTERVAL * 20L
                )
            else CHAT_FORWARD().updateList()
        }
    }

}