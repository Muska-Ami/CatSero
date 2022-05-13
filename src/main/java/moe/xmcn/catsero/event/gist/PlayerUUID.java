package moe.xmcn.catsero.event.gist;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerUUID {
    public static UUID getUUIDByName(String name) {
        return Bukkit.getPlayerExact(name).getUniqueId();
    }
}
