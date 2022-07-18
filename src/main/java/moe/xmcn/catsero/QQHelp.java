package moe.xmcn.catsero;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.HelpList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class QQHelp implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "help")) {
            Config.sendMiraiGroupMessage(HelpList.Companion.getList("qq"));
        }
    }

}
