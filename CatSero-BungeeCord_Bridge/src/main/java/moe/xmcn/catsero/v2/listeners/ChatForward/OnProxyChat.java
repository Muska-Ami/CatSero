package moe.xmcn.catsero.v2.listeners.ChatForward;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OnProxyChat implements Listener {

    private boolean Cancel = false;
    private String message;

    static String cleanColorCode(String string) {
        Set<String> s = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "k", "l", "o", "r"));
        for (int i = 0; i < s.toArray().length; i++) {
            string = string.replace("§" + s.toArray()[i], "");
        }
        return string;
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer sender = (ProxiedPlayer) e.getSender();
        Server server = sender.getServer();
        ServerInfo server_info = server.getInfo();
        String sender_name = sender.getDisplayName();
        String messagex = e.getMessage();
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Configs.config);
        } catch (IOException ex) {
            Loggers.CustomLevel.logCatch(ex);
        }

        if (
                config.getBoolean("chat-bridge.enabled") &&
                        !message.startsWith("/")
        ) {
            String format = config.getString("chat-bridge.format.to-qq");

            if (config.getBoolean("chat-bridge.clean-colorcode")) {
                message = cleanColorCode(message);
            }
            if (config.getBoolean("chat-bridge.filter.enabled")) {
                Configuration finalConfig = config;
                config.getStringList("chat-bridge.filter.list").forEach(it -> {
                    if (finalConfig.getBoolean("chat-bridge.filter.replace-only")) {
                        message = message.replace(it, "***");
                    } else if (!message.contains(it)) {
                        Cancel = true;
                    }
                });
            }

            if (config.getBoolean("chat-bridge.prefix.enabled") &&
                    message.startsWith(config.getString("chat-bridge.prefix.format.to-qq")
                    )
            ) {
                if (message.startsWith(config.getString("chat-bridge.prefix.format.to-qq"))) {
                    message = message.replaceFirst(config.getString("chat-bridge.prefix.format.to-qq"), "");
                } else {
                    Cancel = true;
                }
            }

            if (!Cancel) {
                format = format.replace("%player%", sender_name)
                        .replace("%message%", message)
                        .replace("%server_name%", server_info.getName());
                Env.AMiraiMC.sendMiraiGroupMessage(format, config.getLong("chat-bridge.var.bot"), config.getLong("chat-bridge.var.group"));
            }
        }
    }
}
