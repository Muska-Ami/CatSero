package moe.xmcn.catsero.v3.core

import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.core.listener.chatForward.OnQQGroupChat
import moe.xmcn.catsero.v3.core.listener.chatForward.OnTrChatChat
import moe.xmcn.catsero.v3.core.listener.chatForward.OnVanillaChat
import moe.xmcn.catsero.v3.core.listener.joinQuitForward.OnPlayerJoin
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import moe.xmcn.catsero.v3.util.Logger
import org.bukkit.event.Listener

class CoreRegister {

    companion object {

        private val config = Configuration.usesConfig!!

        fun registerListener() {

            Logger.info("开始注册 Listener")

            /*
             定义要注册的监听器列表
             格式：
              - 监听器
              - 条件
              - 追加兼容监听器(Optional)
              - 追加兼容条件(Optional)
             */
            val list = listOf(
                listOf(
                    OnVanillaChat(),
                    "chatForward . enable",
                    OnTrChatChat(),
                    Configuration.Depend.TrChat,
                ),
                listOf(
                    OnQQGroupChat(),
                    "chatForward . enable",
                ),
                listOf(
                    OnPlayerJoin(),
                    "joinQuitForward . enable",
                )
            )

            list.forEach {
                    Logger.info("注册：${it[0].javaClass}")
                    if (it.size == 4) {
                        // 当追加监听时
                        if (config.getBoolean(it[1] as String) == true) {
                            if (it[3] as Boolean) {
                                CatSero.INSTANCE.server.pluginManager.registerEvents(
                                    it[2] as Listener,
                                    CatSero.INSTANCE
                                )
                            } else {
                                CatSero.INSTANCE.server.pluginManager.registerEvents(
                                    it[0] as Listener,
                                    CatSero.INSTANCE
                                )
                            }
                        }
                    } else if (it.size == 2) {
                        // 当不追加监听时
                        if (config.getBoolean(it[1] as String) == true) {
                            CatSero.INSTANCE.server.pluginManager.registerEvents(
                                it[0] as Listener,
                                CatSero.INSTANCE
                            )
                        }
                    } else {
                        Logger.warn("警告：内部监听注册逻辑不正确，请报告此问题！| $it")
                    }
            }

            /*
            if (config.getBoolean("chatForward . enable")!!) {
                CatSero.INSTANCE.server.pluginManager.registerEvents(OnVanillaChat(), CatSero.INSTANCE)
            }
             */

        }

        fun registerTimer() {

            Logger.info("开始注册 Timer")

            val list = listOf(
                listOf(
                    Filter.startUpdate(),
                    "chatForward . enable",
                ),
            )

            list.forEach {
                if (config.getBoolean(it[1] as String) == true) {
                    Logger.info("注册：${it[0].javaClass}")
                    it[0]
                }
            }
        }

    }


}