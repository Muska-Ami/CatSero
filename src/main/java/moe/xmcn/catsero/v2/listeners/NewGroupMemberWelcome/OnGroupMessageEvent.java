package moe.xmcn.catsero.v2.listeners.NewGroupMemberWelcome;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiMemberJoinEvent event) {
        try {
            if (
                    Configs.JPConfig.uses_config.getBoolean("new-group-member-message.enabled") &&
                            event.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                            event.getBotID() == Configs.getBotCode(Utils.X_Bot)
            ) {
                long code = event.getNewMemberID();
                String message = Configs.JPConfig.uses_config.getString("new-group-member-message.format");
                message = message
                        .replace("%code%", String.valueOf(code))
                        .replace("%at%", "[mirai:at:" + code + "]");
                Env.AMiraiMC.sendMiraiGroupMessage(message, Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
