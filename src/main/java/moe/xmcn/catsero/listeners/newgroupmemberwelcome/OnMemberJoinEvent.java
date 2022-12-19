package moe.xmcn.catsero.listeners.newgroupmemberwelcome;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMemberJoinEvent implements Listener {

    @EventHandler
    public void onMemberJoin(MiraiMemberJoinEvent e) {
        try {
            if (
                    Configuration.USES_CONFIG.NEW_GROUP_MEMBER.ENABLE
                    && e.getBotID() == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.NEW_GROUP_MEMBER.MIRAI.BOT)
                    && e.getGroupID() == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.NEW_GROUP_MEMBER.MIRAI.GROUP)
            ) {
                String format = Configuration.USES_CONFIG.NEW_GROUP_MEMBER.FORMAT;
                format = format.replace("%at%", "[mirai:at:" + e.getMember().getId() + "]")
                        .replace("%code%", String.valueOf(e.getMember().getId()))
                        .replace("%name%", e.getMember().getNick());
                MessageSender.sendGroup(format, Configuration.USES_CONFIG.NEW_GROUP_MEMBER.MIRAI.BOT, Configuration.USES_CONFIG.NEW_GROUP_MEMBER.MIRAI.GROUP);
            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
