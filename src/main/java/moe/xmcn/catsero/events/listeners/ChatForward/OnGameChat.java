package moe.xmcn.catsero.events.listeners.ChatForward;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnGameChat implements Listener {

    @EventHandler
    public void onAsyncGameChat(AsyncPlayerChatEvent event) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled")) {
            String playermessage = event.getMessage();
            String playername = event.getPlayer().getDisplayName();
            String message = Config.UsesConfig.getString("forward-chat.format.to-qq");
            message = message.replace("%player%", playername)
                    .replace("%message%", playermessage);
            message = Config.tryToPAPI(event.getPlayer(), message);
            message = tryRemoveColorCode(message);

            if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled")) {
                if (message.startsWith(Config.UsesConfig.getString("forward-chat.prefix.format.to-qq"))) {
                    Config.sendMiraiGroupMessage(message);
                }
            } else {
                Config.sendMiraiGroupMessage(message);
            }
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
