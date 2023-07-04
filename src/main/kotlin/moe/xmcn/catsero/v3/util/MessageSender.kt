package moe.xmcn.catsero.v3.util

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI
import moe.xmcn.catsero.v3.Configuration

interface MessageSender {

    companion object {

        /**
         * 发消息到QQ群
         * @param message 消息
         * @param bot 机器人
         * @param group 群
         */
        fun sendGroupMessage(message: String, bot: Long, group: Long) {
            // 是否启用HTTP API
            if (Configuration.pluginConfig.getBoolean("mirai . http-api") == true)
                // miraiCode支持
                if (Configuration.pluginConfig.getBoolean("mirai . enable-miraicode-support") == true)
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                else
                    MiraiBot.getBot(bot).getGroup(group).sendMessage(message)
            else {
                // 为什么HTTP API没有注释，***
                MiraiHttpAPI.INSTANCE.sendGroupMessage(
                    MiraiHttpAPI.Bots[bot],
                    group,
                    message
                )
            }
        }

    }

}