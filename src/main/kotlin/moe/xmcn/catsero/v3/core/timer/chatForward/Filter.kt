package moe.xmcn.catsero.v3.core.timer.chatForward

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.util.Logger
import okhttp3.OkHttpClient
import okhttp3.Request
import java.nio.file.Files
import java.nio.file.Paths

class Filter: Runnable {

    companion object {
        @JvmStatic
        private var lastImportLength: Int = 0
        @JvmStatic
        val fullWords = ArrayList<String>()

        fun startUpdate() {
            // 聊天转发屏蔽词
            if (Configuration.usesConfig.getBoolean("chatForward.filter . enable") == true) {
                if (Configuration.usesConfig.getBoolean("chatForward.filter.auto-update . enable") == true) {
                    Logger.info("Start chatForward filter auto-update.")
                    CatSero.INSTANCE.server.scheduler.runTaskTimerAsynchronously(
                        CatSero.INSTANCE,
                        Filter(),
                        0L,
                        (Configuration.usesConfig.getLong("chatForward.filter.auto-update . interval") ?: 300) * 20L
                    )
                } else {
                    Logger.info("Update chatForward filter words.")
                    Filter().updateList()
                }
            }
        }
    }

    private fun updateList() {
        val remoteUrls = Configuration.usesConfig.getArray("chatForward.filter.remote . remote-urls")
        val localFiles = Configuration.usesConfig.getArray("chatForward.filter.local . local-files")
        val publicWords = ArrayList<String>()

        val client = OkHttpClient()

        var jsonData: JSONArray? = null

        if (Configuration.usesConfig.getBoolean("chatForward.filter.local . enable") == true) {
            localFiles ?: ArrayList<String>().toList().forEach { path ->
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
                        Logger.warn("无法解析本地源屏蔽词数据: " + e.message + " | 源: " + path)
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

        if (Configuration.usesConfig.getBoolean("chatForward.filter.remote . enable") == true) {
            remoteUrls ?: ArrayList<String>().toList().forEach { url ->
                run {
                    val res: String?
                    try {
                        val getRequest = Request.Builder()
                            .url(url)
                            .build()
                        res = client.newCall(getRequest).execute().body?.string()
                    } catch (e: Exception) {
                        Logger.warn("无法请求远程源屏蔽词: " + e.message + " | 源: " + url)
                        return
                    }

                    if (res != null) {
                        try {
                            jsonData = JSON.parseObject(res).getJSONArray("words")
                        } catch (e: Exception) {
                            Logger.warn("无法解析远程源屏蔽词数据: " + e.message + " | 源: " + url)
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
        }

        fullWords.addAll(publicWords)

        if (lastImportLength != publicWords.size) {
            Logger.info("成功更新了 " + (publicWords.size - lastImportLength) + " 个屏蔽词，当前屏蔽词数量: " + publicWords.size)
            lastImportLength = publicWords.size
        }
    }

    override fun run() {
        Filter().updateList()
    }
}