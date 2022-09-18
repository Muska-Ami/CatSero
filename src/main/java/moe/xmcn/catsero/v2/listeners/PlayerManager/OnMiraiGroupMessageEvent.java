package moe.xmcn.catsero.v2.listeners.PlayerManager;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMiraiGroupMessageEvent implements Listener {

    @EventHandler
    public void banTool(MiraiGroupMessageEvent e) {
        try {
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (
                    Utils.v.ban_tool &&
                            strings != null &&
                            strings[0].equals("pm")
            ) {

            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

    @EventHandler
    public void opTool(MiraiGroupMessageEvent e) {

    }

}
