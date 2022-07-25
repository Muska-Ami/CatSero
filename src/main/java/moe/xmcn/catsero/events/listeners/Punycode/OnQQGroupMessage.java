package moe.xmcn.catsero.events.listeners.Punycode;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnQQGroupMessage implements Listener {
    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("punycode.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "punycode")) {
                if (args.length == 4 && Objects.equals(args[3], "urlmode")) {
                    Config.sendMiraiGroupMessage(Punycode.encodeURL(args[2]));
                } else {
                    Config.sendMiraiGroupMessage(Punycode.encode(args[2]));
                }
            }
        }
    }

}