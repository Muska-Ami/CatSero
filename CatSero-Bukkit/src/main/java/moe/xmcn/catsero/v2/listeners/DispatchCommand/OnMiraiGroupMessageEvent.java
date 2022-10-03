package moe.xmcn.catsero.v2.listeners.DispatchCommand;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class OnMiraiGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (
                    Configs.JPConfig.uses_config.getBoolean("dispatch-command.enabled") &&
                            e.getBotID() == Configs.getBotCode(Utils.X_Bot) &&
                            e.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                            strings != null &&
                            strings[0].equals("cmd")
            ) {
                if (Configs.isQQOp(e.getSenderID())) {
                    UUID uuid = UUID.randomUUID();
                    Bukkit.getScheduler().runTask(
                            Configs.plugin,
                            () -> {
                                Loggers.CustomLevel.logSign("start", uuid);
                                Bukkit.dispatchCommand(
                                        Bukkit.getConsoleSender(),
                                        Utils.iterateArray(strings)
                                );
                                Loggers.CustomLevel.logSign("end", uuid);
                            }
                    );
                    String data = Loggers.CustomLevel.getSignBlock(uuid);
                    System.out.println(data);
                    wait(2000);
                    Env.AMiraiMC.sendMiraiGroupMemberMessage(data, Utils.X_Bot, Utils.X_Group, e.getSenderID());
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.dispatch-command.success"), Utils.X_Bot, Utils.X_Group);
                } else
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.no-permission"), Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
