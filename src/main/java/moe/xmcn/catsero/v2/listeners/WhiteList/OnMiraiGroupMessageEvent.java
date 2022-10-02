package moe.xmcn.catsero.v2.listeners.WhiteList;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnMiraiGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            String[] strings = QPS.getParser.parse(e.getMessage());
            if (
                    Configs.JPConfig.uses_config.getBoolean("whitelist.enabled") &&
                            e.getBotID() == Configs.getBotCode(Utils.X_Bot) &&
                            e.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                            strings != null &&
                            strings[0].equals("whitelist")
            ) {
                // 检查是否为QQOp
                if (Configs.isQQOp(e.getSenderID())) {
                    if (strings.length == 4) {
                        switch (strings[1]) {
                            case "add":
                                if (!(Long.parseLong(strings[3]) == 0)) {
                                    if (Configs.JPConfig.uses_config.getBoolean("whitelist.not-allow-is-not-group-member")) {
                                        if (MiraiBot.getBot(Configs.getBotCode(Utils.X_Bot)).getGroup(Configs.getGroupCode(Utils.X_Group)).getMember(Long.parseLong(strings[3])) != null) {
                                            Objects.requireNonNull(Utils.getWhiteList()).set("list", strings[2] + ":|:" + strings[3]);
                                            Env.AMiraiMC.sendMiraiGroupMessage(
                                                    Configs.getMsgByMsID("qq.whitelist.success")
                                                            .replace("%name%", strings[2])
                                                            .replace("%code%", strings[3]),
                                                    Utils.X_Bot,
                                                    Utils.X_Group
                                            );
                                        } else {
                                            Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.whitelist.invalid-qq"), Utils.X_Bot, Utils.X_Group);
                                        }
                                    } else {
                                        Objects.requireNonNull(Utils.getWhiteList()).set("list", strings[2] + ":|:" + strings[3]);
                                        Env.AMiraiMC.sendMiraiGroupMessage(
                                                Configs.getMsgByMsID("qq.whitelist.success")
                                                        .replace("%name%", strings[2])
                                                        .replace("%code%", strings[3]),
                                                Utils.X_Bot,
                                                Utils.X_Group
                                        );
                                    }
                                } else
                                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.invalid-options"), Utils.X_Bot, Utils.X_Group);
                                break;
                            case "remove":
                                switch (strings[2]) {
                                    case "-n":
                                        Objects.requireNonNull(Utils.getWhiteList()).getStringList("list").forEach(it -> {
                                            String[] strs = it.split(":\\|:");
                                            if (strs[0].equals(strings[3])) {
                                                Objects.requireNonNull(Utils.getWhiteList()).getStringList("list").remove(strings[3] + ":|:" + strs[1]);
                                            }
                                        });
                                        break;
                                    case "-q":
                                        Objects.requireNonNull(Utils.getWhiteList()).getStringList("list").forEach(it -> {
                                            String[] strs = it.split(":\\|:");
                                            if (strs[1].equals(strings[3])) {
                                                Objects.requireNonNull(Utils.getWhiteList()).getStringList("list").remove(strs[0] + ":|:" + strings[3]);
                                            }
                                        });
                                        break;
                                }
                                break;
                            default:
                                Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.invalid-options"), Utils.X_Bot, Utils.X_Group);
                                break;
                        }
                    } else
                        Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.invalid-options"), Utils.X_Bot, Utils.X_Group);
                } else
                    Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.player-manager.no-permission"), Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
