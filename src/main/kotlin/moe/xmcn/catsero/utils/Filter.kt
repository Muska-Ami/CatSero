package moe.xmcn.catsero.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import moe.xmcn.catsero.CatSero
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
            val remoteUrls = Configuration.getUses().getStringList(
                Configuration.buildYaID(
                    iID, ArrayList<String>(
                        listOf(
                            "filter", "list", "import", "remote"
                        )
                    )
                )
            )
            val localFiles = Configuration.getUses().getStringList(
                Configuration.buildYaID(
                    iID, ArrayList<String>(
                        listOf(
                            "filter", "list", "import", "local"
                        )
                    )
                )
            )
            val publicWords = ArrayList<String>()
            val mcWords = Configuration.getUses().getStringList(
                Configuration.buildYaID(
                    iID, ArrayList<String>(
                        listOf(
                            "filter", "list", "via", "to-mc"
                        )
                    )
                )
            )
            val qqWords = Configuration.getUses().getStringList(
                Configuration.buildYaID(
                    iID, ArrayList<String>(
                        listOf(
                            "filter", "list", "via", "to-qq"
                        )
                    )
                )
            )

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
                    var res: String?
                    try {
                        res = hc.getRequest(url)
                    } catch (e: Exception) {
                        Logger.logTask("无法请求远程源屏蔽词: " + e.message + " | 源: " + url)
                        return
                    }

                    if (res != null) {
                        try {
                            jsonData = JSON.parseObject(res).getJSONArray("words")
                        } catch (e: Exception) {
                            Logger.logTask("无法解析远程源屏蔽词数据: " + e.message + " | 源: " + url)
                            return
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
                lastImportLength = publicWords.size
            }
        }
    }

    override fun run() {
        CHAT_FORWARD().updateList()
    }

    companion object {
        @JvmStatic
        val iID = Configuration.USESID.CHAT_FORWARD

        @JvmStatic
        private var lastImportLength: Int = 0

        @JvmStatic
        fun startUpdate() {
            // 聊天转发
            if (Configuration.getUses().getBoolean(
                    Configuration.buildYaID(
                        iID, ArrayList<String>(
                            listOf(
                                "filter", "auto-update", "enable"
                            )
                        )
                    )
                )
            ) {
                Logger.logLoader("Start filter auto-update.")
                CatSero.INSTANCE.server.scheduler.runTaskTimerAsynchronously(
                    CatSero.INSTANCE,
                    Filter(),
                    0L,
                    Configuration.getUses().getLong(
                        Configuration.buildYaID(
                            iID, ArrayList<String>(
                                listOf(
                                    "filter", "auto-update", "interval"
                                )
                            )
                        )
                    ) * 20L
                )
            } else {
                Logger.logLoader("Update filter words.")
                CHAT_FORWARD().updateList()
            }
        }
    }

}