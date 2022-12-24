package moe.xmcn.catsero.listeners.chatforward;

import me.arasple.mc.trchat.api.event.TrChatEvent;
import me.arasple.mc.trchat.module.display.ChatSession;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class OnTrChatEvent implements Listener {

    private boolean filter = false;

    private static String cleanStyleCodes(String s) {
        List<String> s0 = Arrays.asList(
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
        );
        for (var i = 0; i < s0.toArray().length; i++) {
            s = s.replace("" + s0.toArray()[i], "");
        }
        return s;
    }

    @EventHandler
    public void onTrChat(TrChatEvent e) {
        if (Configuration.USES_CONFIG.CHAT_FORWARD.ENABLE) {
            String message = e.getMessage();
            String channel = e.getChannel().getId();

            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.TO_QQ.forEach(it -> {
                    if (message.contains(it)) filter = true;
                });
                if (
                        !filter
                        && Configuration.EXTRA_CONFIG.TRCHAT.CHAT_FORWARD.CHANNEL.contains(channel)
                )
                        run(e.getSession(), message);
                else
                    filter = false;
            } else
                run(e.getSession(), message);
        }
    }

    private void run(ChatSession e, String message) {
        String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_QQ;

        if (!message.contains("[mirai:")) {
            if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_QQ)
                message = cleanStyleCodes(message);

            format = format.replace("%message%", message)
                    .replace("%name%", e.getPlayer().getName())
                    .replace("%display_name%", e.getPlayer().getDisplayName());

            if (e.getPlayer().isOp())
                format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.ADMIN);
            else
                format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.PLAYER);

            MessageSender.sendGroup(format, Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT, Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP);
        } else
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.CHAT_FORWARD.CASE_MIRAICODE));
    }

}
