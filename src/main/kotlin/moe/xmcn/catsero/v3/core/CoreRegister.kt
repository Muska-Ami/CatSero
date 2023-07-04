package moe.xmcn.catsero.v3.core

import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.core.listener.chatForward.OnVanillaChat
import moe.xmcn.catsero.v3.util.Logger
import org.bukkit.event.Listener

class CoreRegister {

    companion object {

        private val config = Configuration.getUsesConfig()

        fun registerListener() {

            Logger.info("开始注册 Listener")

            val list = listOf(
                listOf(
                    OnVanillaChat(),
                    "chat-forward . enable",
                ),
            )

            list.forEach {
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

    }


}