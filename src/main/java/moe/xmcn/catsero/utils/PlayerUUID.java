package moe.xmcn.catsero.utils;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerUUID {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    public static UUID getUUIDByName(String name) {
        try {
            return Bukkit.getPlayerExact(name).getUniqueId();
        } catch (NullPointerException npe) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
    }

    /**
     * 由UUID获取玩家名
     * java.util.UUID类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(UUID uuid) {
        return Bukkit.getPlayer(uuid).getDisplayName();
    }

}