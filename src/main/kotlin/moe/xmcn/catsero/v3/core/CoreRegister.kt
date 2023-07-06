package moe.xmcn.catsero.v3.core

import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.core.listener.chatForward.OnVanillaChat
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import moe.xmcn.catsero.v3.util.Logger
import org.bukkit.event.Listener

class CoreRegister {

    companion object {

        private val config = Configuration.usesConfig

        fun registerListener() {

            Logger.info("开始注册 Listener")

            val list = listOf(
                listOf(
                    OnVanillaChat(),
                    "chatForward . enable",
                ),
            )

            list.forEach {
                if (config.getBoolean(it[1] as String) == true) {
                    Logger.info("注册：${it[0].javaClass}")
                    CatSero.INSTANCE.server.pluginManager.registerEvents(it[0] as Listener, CatSero.INSTANCE)
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