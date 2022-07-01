package moe.xmcn.catsero.events.listeners.NewGroupMember;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class OnQQGroupNewMember implements Listener {


    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiGroupMemberJoinEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("new-group-member-message.enabled") && event.getGroupID() == Config.INSTANCE.getUse_Group() && event.getBotID() == Config.INSTANCE.getUse_Bot()) {
            isEnabled(event);
        }
    }

    private void isEnabled(MiraiGroupMemberJoinEvent event) {
        long code = event.getNewMemberID();
        String message = Config.INSTANCE.getUsesConfig().getString("new-group-member-message.format");
        message = message
                .replace("%code%", String.valueOf(code))
                .replace("%at%", "[mirai:at:" + code + "]");
        try {
            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(message);
        } catch (NoSuchElementException nse) {
            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
        }
    }
}
