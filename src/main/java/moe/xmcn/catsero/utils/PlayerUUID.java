package moe.xmcn.catsero.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUUID {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    public static UUID getUUIDByName(String name) {
        return UUID.fromString(Config.PlayerRecord.UUIDRecord.getString(name));
    }

    /**
     * 由UUID获取玩家名
     * UUID类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(UUID uuid) {
        return Bukkit.getPlayer(uuid).getName();
    }

    /**
     * 由UUID获取玩家名
     * String类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(String uuid) {
        return Bukkit.getPlayer(uuid).getName();
    }

    /**
     * 得到一个玩家对象
     *
     * @param name 玩家名
     */
    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(getUUIDByName(name));
    }

}