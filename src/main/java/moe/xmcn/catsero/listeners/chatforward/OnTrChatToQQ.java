package moe.xmcn.catsero.listeners.chatforward;

import me.arasple.mc.trchat.api.event.TrChatEvent;
import me.arasple.mc.trchat.module.display.ChatSession;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class OnTrChatToQQ implements Listener {

    private final boolean enable;
    private final String bot;
    private final String group;
    private String message;

    public OnTrChatToQQ() {
        this.enable = Configuration.USES_CONFIG.CHAT_FORWARD.ENABLE;
        this.bot = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT;
        this.group = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP;
    }

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
        for (int i = 0; i < s0.toArray().length; i++) {
            s = s.replace("&" + s0.toArray()[i], "");
            s = s.replace("§" + s0.toArray()[i], "");
        }
        return s;
    }

    @EventHandler
    public void onTrChat(TrChatEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (enable) {
                        message = e.getMessage();
                        String channel = e.getChannel().getId();

                        // 先检查聊天频道
                        if (Configuration.EXTRA_CONFIG.TRCHAT.CHAT_FORWARD.CHANNEL.contains(channel)) {
                            // Filter
                            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.ALL_TO_QQ().forEach(it -> message = message.replace(it, Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.REPLACE));
                                run1(e.getSession(), message);
                /*
                if (
                        !filter
                        && Configuration.EXTRA_CONFIG.TRCHAT.CHAT_FORWARD.CHANNEL.contains(channel)
                )
                        run(e.getSession(), message);
                else
                    filter = false;

                 */
                            } else
                                run1(e.getSession(), message);
                        }
                    }
                } catch (Exception ex) {
                    Logger.logCatch(ex);
                }
            }
        }.runTaskAsynchronously(Configuration.plugin);

    }

    private void run1(ChatSession e, String message) {
        String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_QQ;

        // 检查消息是否含有Mirai码
        if (
                !Configuration.USES_CONFIG.CHAT_FORWARD.ALLOW_MIRAICODE
                        && !message.contains("[mirai:")
        ) {
            // 清理样式代码
            if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_QQ)
                message = cleanStyleCodes(message);

            format = format.replace("%message%", message)
                    .replace("%name%", e.getPlayer().getName())
                    .replace("%display_name%", e.getPlayer().getDisplayName());

            // 权限
            if (e.getPlayer().isOp())
                format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.ADMIN);
            else
                format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.PLAYER);

            if (Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.ENABLE) {
                if (message.startsWith(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_QQ))
                    MessageSender.sendGroup(format.replaceFirst(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_QQ, ""), bot, group);
            } else
                MessageSender.sendGroup(format, bot, group);
        } else
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.CHAT_FORWARD.CASE_MIRAICODE));
    }

}
