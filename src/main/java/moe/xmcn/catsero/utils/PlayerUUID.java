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
        return Bukkit.getPlayerExact(name).getUniqueId();
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

    /**
     * 由UUID获取玩家名
     * String类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(String uuid) {
        return Bukkit.getPlayer(uuid).getDisplayName();
    }

}