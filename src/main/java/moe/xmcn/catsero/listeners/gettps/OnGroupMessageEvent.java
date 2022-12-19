package moe.xmcn.catsero.listeners.gettps;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.QPS;
import moe.xmcn.catsero.utils.TPSCalculator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onGroupMessageEvent(MiraiGroupMessageEvent e) {
        String[] args = QPS.parse(e.getMessage());
        if (args != null) {
            if (
                    Configuration.USES_CONFIG.GET_TPS.ENABLE
                            && args[0].equalsIgnoreCase("tps")
            ) {
                if (args.length == 2) {
                    double tps = TPSCalculator.getTPS();
                    BigDecimal around_tps = BigDecimal.valueOf(tps).setScale(1, RoundingMode.HALF_UP);

                    switch (args[1]) {
                        case "accurate":
                            MessageSender.sendGroup("TPS: " + tps, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                            break;
                        case "around":
                            MessageSender.sendGroup("TPS: " + around_tps, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                            break;
                        default:
                            MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                    }
                } else
                    MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
            }
        }
    }

}
