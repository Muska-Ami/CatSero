package moe.xmcn.catsero.v2.listeners.GetTPS;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        String[] strings = QPS.getParser.parse(e.getMessage());
        if (
                strings != null &&
                        strings[0].equals("tps")
        ) {

        }
    }

}
