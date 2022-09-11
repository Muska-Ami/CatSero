package moe.xmcn.catsero.v2.listeners.NewGroupMemberWelcome;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiMemberJoinEvent event) {
        if (
                Configs.getConfig("uses-config.yml").getBoolean("new-group-member-message.enabled") &&
                        event.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                        event.getBotID() == Configs.getBotCode(Utils.X_Bot)
        ) {
            long code = event.getNewMemberID();
            String message = Configs.getConfig("uses-config.yml").getString("new-group-member-message.format");
            message = message
                    .replace("%code%", String.valueOf(code))
                    .replace("%at%", "[mirai:at:" + code + "]");
            Env.AMiraiMC.sendMiraiGroupMessage(message, Utils.X_Bot, Utils.X_Group);
        }
    }

}
