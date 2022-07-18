package moe.xmcn.catsero.events.listeners.Punycode;

import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class OnGameCommand {

    public static boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("punycode") && Config.UsesConfig.getBoolean("punycode.enabled")) {
            if (args.length > 2 && args[2].equalsIgnoreCase("urlmode")) {
                sender.sendMessage(Punycode.encodeURL(args[1]));
            } else {
                sender.sendMessage(Punycode.encode(args[1]));
            }
            return true;
        }
        return false;
    }

}
