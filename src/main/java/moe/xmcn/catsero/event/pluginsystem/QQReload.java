package moe.xmcn.catsero.event.pluginsystem;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.QCommandParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QQReload implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String[] args = QCommandParser.getParser.parse(event.getMessage());
        if (args != null) {
            if (args[0].equals("reload")) {
                Config.reloadConfig();
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.reload"));
            }
        }
    }

}
