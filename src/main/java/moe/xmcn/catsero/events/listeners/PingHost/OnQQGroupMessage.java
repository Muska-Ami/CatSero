package moe.xmcn.catsero.events.listeners.PingHost;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.events.gists.PingHost;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("pinghost.enabled") && event.getGroupID() == Config.INSTANCE.getUse_Group() && event.getBotID() == Config.INSTANCE.getUse_Bot()) {
            isEnabled(event);
        }
    }

    private void isEnabled(MiraiGroupMessageEvent event) {
        if (Config.INSTANCE.getUsesConfig().getBoolean("pinghost.op-only")) {
            if (event.getSenderID() == Config.INSTANCE.getQQ_OP()) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                    try {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.doing"));
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
                        String result = PingHost.PingHostUtils(args[2]);
                        if (Objects.equals(result, "Error")) {
                            try {
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.error"));
                            } catch (NoSuchElementException nse) {
                                Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                            }
                        } else {
                            long flag = Long.parseLong(result);
                            try {
                                String message = Config.INSTANCE.getMsgByMsID("qq.pinghost.success")
                                        .replace("%address_original%", args[2])
                                        .replace("%address_punycode%", Punycode.encodeURL(args[2]))
                                        .replace("%withdraw%", String.valueOf(flag))
                                        .replace("%lost%", String.valueOf(4 - flag))
                                        .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                                MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(message);
                                //MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            } catch (NoSuchElementException nse) {
                                Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                            }
                        }
                    } catch (UnknownHostException e) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.failed"));
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
                    }
                }
            } else {
                try {
                    MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.no-permission"));
                } catch (NoSuchElementException nse) {
                    Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                }
            }
        } else {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                try {
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.doing"));
                    } catch (NoSuchElementException nse) {
                        Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                    }
                    String result = PingHost.PingHostUtils(args[2]);
                    if (Objects.equals(result, "Error")) {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.error"));
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
                    } else {
                        long flag = Long.parseLong(result);
                        try {
                            String message = Config.INSTANCE.getMsgByMsID("qq.pinghost.success")
                                    .replace("%address_original%", args[2])
                                    .replace("%address_punycode%", Punycode.encodeURL(args[2]))
                                    .replace("%withdraw%", String.valueOf(flag))
                                    .replace("%lost%", String.valueOf(4 - flag))
                                    .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(message);
                            //MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                        } catch (NoSuchElementException nse) {
                            Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                        }
                    }
                } catch (UnknownHostException e) {
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + Config.INSTANCE.getMsgByMsID("qq.pinghost.failed"));
                    } catch (NoSuchElementException nse) {
                        Config.INSTANCE.getPlugin().getLogger().warning(Config.INSTANCE.getMsgByMsID("general.send-message-qq.error").replace("%error%", nse + Arrays.toString(nse.getStackTrace())));
                    }
                }
            }
        }
    }

}