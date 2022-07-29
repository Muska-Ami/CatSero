package moe.xmcn.catsero.events.listeners.BindQQ;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("bind-qq.enabled")) {
            String message = event.getMessage();
            String[] args = message.split(" ");
            if (Objects.equals(args[0], "catsero") && Objects.equals(args[1], "bind")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    if (args.length == 5 && Objects.equals(args[2], "add")) {
                        if (!Objects.requireNonNull(MiraiMC.getBind(Long.parseLong(args[3]))).toString().equals("") || MiraiMC.getBind(Players.getUUIDByName(args[3])) != 0L) {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.already-bind"));
                        } else {
                            MiraiMC.addBind(Players.getUUIDByName(args[4]), Long.parseLong(args[3]));
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.add-success"));
                        }
                    } else if (args.length == 4 && Objects.equals(args[2], "remove")) {
                        if (!Objects.requireNonNull(MiraiMC.getBind(Long.parseLong(args[3]))).toString().equals("") || MiraiMC.getBind(Players.getUUIDByName(args[3])) != 0L) {
                            MiraiMC.removeBind(Long.parseLong(args[3]));
                            MiraiMC.removeBind(Players.getUUIDByName(args[3]));
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.remove-success"));

                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.no-bind-found"));
                        }
                    }
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }

}
