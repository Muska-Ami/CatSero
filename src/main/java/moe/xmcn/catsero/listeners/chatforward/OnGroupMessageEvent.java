package moe.xmcn.catsero.listeners.chatforward;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent e) {
        if (
                Configuration.USES_CONFIG.CHAT_FORWARD.ENAbLE
                && e.getBotID() == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT)
                && e.getGroupID() == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP)
        ) {
            String message = e.getMessage();

            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                if (!message.contains(Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.TO_MC))
                    run(e, message);
            } else
                run(e, message);

        }
    }

    private void run(MiraiGroupMessageEvent e, String message) {
        String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_MC;

        if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_MC)
            message = cleanStyleCodes(message);
        format = format.replace("%message%", message);
                //.replace("%name%", );
        int sender_permission = e.getSenderPermission();
        switch (sender_permission) {
            case 0:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.MEMBER);
                break;
            case 1:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.ADMIN);
                break;
            case 2:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.OWNER);
                break;
        }

    }

    private static String cleanStyleCodes(String s) {
        Set<String> s0 = new HashSet<>(Arrays.asList(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "k",
                "l",
                "o",
                "r"
        ));
        for (var i=0;i<s0.toArray().length; i++) {
            s = s.replace("" + s0.toArray()[i], "");
        }
        return s;
    }

}
