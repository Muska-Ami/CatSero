package moe.xmcn.catsero.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class Players {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    public static UUID getUUIDByName(String name) {
        try {
            return Database.UUIDDatabase.readTable.getUUID(name);
        } catch (SQLException e) {
            Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
        }
        return null;
    }

    /**
     * 由UUID获取玩家名
     * UUID类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    public static String getNameByUUID(UUID uuid) {
        try {
            return Database.UUIDDatabase.readTable.getName(uuid);
        } catch (SQLException e) {
            Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
        }
        return null;
    }

    /**
     * 由UUID获取玩家名
     * String类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    @Deprecated
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