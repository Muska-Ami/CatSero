package moe.xmcn.catsero.v3.core

import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.core.listener.chatForward.OnVanillaChat
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import moe.xmcn.catsero.v3.util.Logger
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable

class CoreRegister {

    companion object {

        private val config = Configuration.usesConfig

        fun registerListener() {

            Logger.info("开始注册 Listener")

            val list = listOf(
                listOf(
                    OnVanillaChat(),
                    "chat-forward . enable",
                ),
            )

            list.forEach {
                Logger.warn(config.getBoolean(it[1] as String).toString())
                if (config.getBoolean(it[1] as String) == true) {
                    Logger.info("注册：${it[0]}")
                    CatSero.INSTANCE.server.pluginManager.registerEvents(it[0] as Listener, CatSero.INSTANCE)
                }
            }

            /*
            if (config.getBoolean("chat-forward . enable")!!) {
                CatSero.INSTANCE.server.pluginManager.registerEvents(OnVanillaChat(), CatSero.INSTANCE)
            }
             */

        }

        fun registerTimer() {

            Logger.info("开始注册 Timer")

            val list = listOf(
                listOf(
                    Filter.startUpdate(),
                    "chat-forward . enable",
                ),
            )

            list.forEach {
                if (config.getBoolean(it[1] as String) == true) {
                    Logger.info("注册：${it[0]}")
                    it[0]
                }
            }
        }

    }


}