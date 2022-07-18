package moe.xmcn.catsero.events.listeners.NewGroupMember;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnQQGroupNewMember implements Listener {


    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiMemberJoinEvent event) {
        if (Config.UsesConfig.getBoolean("new-group-member-message.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            long code = event.getNewMemberID();
            String message = Config.UsesConfig.getString("new-group-member-message.format");
            message = message
                    .replace("%code%", String.valueOf(code))
                    .replace("%at%", "[mirai:at:" + code + "]");
            Config.sendMiraiGroupMessage(message);
        }
    }

}
