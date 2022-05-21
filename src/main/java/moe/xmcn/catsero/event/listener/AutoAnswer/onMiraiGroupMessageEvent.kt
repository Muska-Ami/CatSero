package moe.xmcn.catsero.event.listener.AutoAnswer

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class onMiraiGroupMessageEvent : Listener {
    @EventHandler
    fun onGroupMessage(event: MiraiGroupMessageEvent) {
        Config.Use_Bots.forEach { bot ->
            Config.Use_Groups.forEach { group ->
                if (Config.UsesConfig.getBoolean("auto-answer.enabled") && event.groupID == bot && event.botID == bot) {
                    val config = Config.customConfig(Config.UsesConfig.getString("auto-answer.data-file"))
                    config.getStringList("list").forEach { list ->
                        val ques = config.getString("$list.ques")
                        val answ = config.getString("$list.answ")
                        val msg = event.message
                        if (msg == ques) {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(answ)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        }
                    }
                }
            }
        }
    }
}