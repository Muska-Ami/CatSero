package moe.xmcn.catsero.events.cmdboots;

import moe.xmcn.catsero.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMS implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("send")) {
                Config.sendMiraiGroupMessage(args[1]);
            }
        }
        return true;
    }

}
