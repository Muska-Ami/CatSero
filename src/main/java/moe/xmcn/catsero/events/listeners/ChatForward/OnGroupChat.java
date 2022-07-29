package moe.xmcn.catsero.events.listeners.ChatForward;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnGroupChat implements Listener {

    @EventHandler
    public void onGroupChat(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            long groupid = event.getGroupID();
            String groupname = event.getGroupName();
            long senderid = event.getSenderID();
            String sendername = event.getSenderNameCard();
            String emsg = event.getMessage();

            if (Config.UsesConfig.getBoolean("forward-chat.use-bind")) {
                String message = Config.UsesConfig.getString("forward-chat.format.to-mc");
                message = tryUseBind(message, groupid, groupname, senderid, sendername, emsg);
                message = tryRemoveColorCode(message);
                if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled")) {
                    if (message.startsWith(Config.UsesConfig.getString("forward-chat.prefix.format.to-qq"))) {
                        Bukkit.broadcastMessage(message);
                    }
                } else {
                    Bukkit.broadcastMessage(message);
                }
            }
        }
    }

    private String tryUseBind(String message, long groupid, String groupname, long senderid, String sendername, String emsg) {
        String mes = message.replace("%groupcode%", String.valueOf(groupid))
                .replace("%groupname%", groupname)
                .replace("%sendercode%", String.valueOf(senderid));
        if (Config.UsesConfig.getBoolean("forward-chat.use-bind")) {
            return mes.replace("%sendername%", Objects.requireNonNull(Players.getNameByUUID(MiraiMC.getBind(senderid))))
                    .replace("%message%", emsg);
        } else {
            return mes.replace("%sendername%", sendername)
                    .replace("%message%", emsg);
        }
    }

    private String tryRemoveColorCode(String message) {
        if (Config.UsesConfig.getBoolean("forward-chat.clean-colorcode")) {
            return message.replace("§1", "")
                    .replace("§2", "")
                    .replace("§3", "")
                    .replace("§4", "")
                    .replace("§5", "")
                    .replace("§6", "")
                    .replace("§7", "")
                    .replace("§8", "")
                    .replace("§9", "")
                    .replace("§0", "")
                    .replace("§a", "")
                    .replace("§b", "")
                    .replace("§c", "")
                    .replace("§d", "")
                    .replace("§e", "")
                    .replace("§f", "")
                    .replace("§k", "")
                    .replace("§l", "")
                    .replace("§m", "")
                    .replace("§n", "")
                    .replace("§o", "")
                    .replace("§r", "");
        } else {
            return message;
        }
    }

}
