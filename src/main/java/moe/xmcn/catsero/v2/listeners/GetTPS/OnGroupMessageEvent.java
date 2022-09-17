package moe.xmcn.catsero.v2.listeners.GetTPS;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            if (Configs.JPConfig.uses_config.getBoolean("get-tps.enabled")) {
                String[] strings = QPS.getParser.parse(e.getMessage());
                if (
                        strings != null &&
                                strings[0].equals("tps")
                ) {
                    if (strings.length == 2) {
                        double tps = ServerTPS.getTPS();
                        double round_tps = 0.1 + Double.parseDouble(String.valueOf(BigDecimal.valueOf(tps).setScale(1, RoundingMode.HALF_UP)));

                        switch (strings[0]) {
                            case "accurate":
                                Env.AMiraiMC.sendMiraiGroupMessage("TPS: " + tps, Utils.X_Bot, Utils.X_Group);
                                break;
                            case "round":
                                Env.AMiraiMC.sendMiraiGroupMessage("TPS: " + round_tps, Utils.X_Bot, Utils.X_Group);
                                break;
                        }
                    } else Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.invalid-options"), Utils.X_Bot, Utils.X_Group);
                }
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
