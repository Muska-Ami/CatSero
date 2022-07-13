package moe.xmcn.catsero.events.listeners.NewGroupMember;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class OnQQGroupNewMember implements Listener {


    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiMemberJoinEvent event) {
        if (Config.UsesConfig.getBoolean("new-group-member-message.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            isEnabled(event);
        }
    }

    private void isEnabled(MiraiMemberJoinEvent event) {
        long code = event.getNewMemberID();
        String message = Config.UsesConfig.getString("new-group-member-message.format");
        message = message
                .replace("%code%", String.valueOf(code))
                .replace("%at%", "[mirai:at:" + code + "]");
        try {
            MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(message);
        } catch (NoSuchElementException nse) {
            Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
        }
    }
}
