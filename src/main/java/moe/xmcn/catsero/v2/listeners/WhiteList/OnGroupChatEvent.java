package moe.xmcn.catsero.v2.listeners.WhiteList;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.Arrays;

public class OnGroupChatEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent0(MiraiGroupMessageEvent e) {
        if (
                Configs.getConfig("uses-config.yml").getBoolean("whitelist.enabled") &&
                        e.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                        e.getBotID() == Configs.getBotCode(Utils.X_Bot)
        ) {
            Utils.createData();
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (
                    Configs.getConfig("uses-config.yml").getBoolean("whitelist.self-get.enabled") &&
                            strings != null
            ) {
                if (strings.length == 2) {
                    if (strings[0].equals(Configs.getConfig("uses-config.yml").getString("whitelist.self-get.format"))) {
                        if (!Configs.getConfig("extra-configs/whitelist.yml").isSet("list." + e.getSenderID())) {
                            Configs.getConfig("extra-configs/whitelist.yml").addDefault("list." + e.getSenderID(), strings[1]);
                            try {
                                Configs.getConfig("extra-configs/whitelist.yml").save("extra-configs/whitelist.yml");
                            } catch (IOException ex) {
                                Loggers.logWARN(Configs.getMsgByMsID("general.save-file-error").replace("%error%", Arrays.toString(ex.getStackTrace())));
                            }
                            Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.whitelist.success-add"), Utils.X_Bot, Utils.X_Group);
                        } else {
                            Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.whitelist.already-added"), Utils.X_Bot, Utils.X_Group);
                        }
                    }
                } else {
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.invalid-options"), Utils.X_Bot, Utils.X_Group);
                }
            }
        }
    }

    @EventHandler
    public void onMiraiGroupMessageEvent1(MiraiGroupMessageEvent e) {
        if (
                Configs.getConfig("uses-config.yml").getBoolean("whitelist.enabled") &&
                        e.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                        e.getBotID() == Configs.getBotCode(Utils.X_Bot) &&
                        Configs.isQQOp(e.getSenderID())
        ) {
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (strings != null) {
                if (strings.length == 3) {
                    if (
                            !Configs.getConfig("extra-configs/whitelist.yml").isSet("list." + e.getSenderID()) &&
                                    strings[0].equals("whitelist") &&
                                    strings[1].equals("add")
                    ) {
                        Configs.getConfig("extra-configs/whitelist.yml").addDefault("list." + e.getSenderID(), strings[2]);
                        try {
                            Configs.getConfig("extra-configs/whitelist.yml").save("extra-configs/whitelist.yml");
                        } catch (IOException ex) {
                            Loggers.logWARN(Configs.getMsgByMsID("general.save-file-error").replace("%error%", Arrays.toString(ex.getStackTrace())));
                        }
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.whitelist.success-add"), Utils.X_Bot, Utils.X_Group);
                    } else {
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.whitelist.already-added"), Utils.X_Bot, Utils.X_Group);
                    }
                } else {
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.invalid-options"), Utils.X_Bot, Utils.X_Group);
                }
            }
        }
    }

}
