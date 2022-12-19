package moe.xmcn.catsero.listeners.getonlinelist;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.QPS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class OnGroupMessageEvent implements Listener {

    private String player_list = null;

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent e) {
        String[] args = QPS.parse(e.getMessage());
        if (args != null) {
            if (
                    Configuration.USES_CONFIG.GET_ONLINE_LIST.ENABLE
                    && args[0].equalsIgnoreCase("list")
                    && Configuration.Interface.isQQOp(e.getSenderID())
                    && e.getBotID() == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                    && e.getGroupID() == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)
            ) {
                List<Player> list = (List<Player>) Bukkit.getOnlinePlayers();

                String format;

                // 0
                format = Configuration.USES_CONFIG.GET_ONLINE_LIST.FORMAT_0;
                format = format.replace("%count%", String.valueOf(list.toArray().length))
                        .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()));
                MessageSender.sendGroup(format, Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT, Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP);

                // 1
                if (list.toArray().length != 0) {
                    list.forEach(it -> player_list += it.getName() + ", ");
                    player_list = player_list.substring(4, player_list.length() - 2);

                    format = Configuration.USES_CONFIG.GET_ONLINE_LIST.FORMAT_1;
                    format = format.replace("%count%", String.valueOf(list.toArray().length))
                            .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()))
                            .replace("%list%", player_list);
                    MessageSender.sendGroup(format, Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT, Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP);

                    player_list = null;
                }
            }
        }
    }

}
