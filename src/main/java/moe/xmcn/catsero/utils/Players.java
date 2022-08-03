package moe.xmcn.catsero.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Players {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    public static UUID getUUIDByName(String name) {
        return Database.UUIDDatabase.queryUUID(name);
    }

    /**
     * 由UUID获取玩家名
     * UUID类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(UUID uuid) {
        return Database.UUIDDatabase.queryName(uuid);
    }

    /**
     * 得到一个玩家对象
     *
     * @param name 玩家名
     */
    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(getUUIDByName(name));
    }

    /**
     * 得到一个玩家对象
     *
     * @param uuid 玩家UUID
     */
    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

}