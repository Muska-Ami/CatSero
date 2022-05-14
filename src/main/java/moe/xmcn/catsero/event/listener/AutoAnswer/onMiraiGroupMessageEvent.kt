package moe.xmcn.catsero.event.listener.AutoAnswer

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable

class onMiraiGroupMessageEvent : Listener {
    @EventHandler
    fun onGroupMessage(event: MiraiGroupMessageEvent) {
        object : BukkitRunnable() {
            override fun run() {
                if (Config.UsesConfig.getBoolean("auto-answer.enabled") && event.groupID == Config.Use_Bot && event.botID == Config.Use_Group) {
                    val config = Config.customConfig(Config.UsesConfig.getString("auto-answer.data-file"))
                    config.getStringList("list").forEach { list ->
                        val ques = config.getString("$list.ques")
                        val answ = config.getString("$list.answ")
                        val msg = event.message
                        if (msg == ques) {
                            try {
                                MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(answ)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Config.plugin)
    }
}