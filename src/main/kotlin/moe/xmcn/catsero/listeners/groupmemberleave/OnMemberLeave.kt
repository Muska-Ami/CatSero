package moe.xmcn.catsero.listeners.groupmemberleave

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.MessageSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnMemberLeave(
    private val enable: Boolean = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onGroupMemberLeave(e: MiraiMemberLeaveEvent) {
        if (
            enable
            && e.botID == Configuration.Interface.getBotCode(bot)
            && e.groupID == Configuration.Interface.getGroupCode(group)
        ) {
            val name = e.memberNick
            val code = e.targetID

            var format = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.FORMAT
            format = format.replace("%name%", name)
                .replace("%code%", code.toString())
            MessageSender.sendGroup(format, bot, group)
        }
    }

}