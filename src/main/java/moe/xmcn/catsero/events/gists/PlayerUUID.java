package moe.xmcn.catsero.events.gists;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerUUID {
    public static UUID getUUIDByName(String name) {
        return Bukkit.getPlayerExact(name).getUniqueId();
    }
}
