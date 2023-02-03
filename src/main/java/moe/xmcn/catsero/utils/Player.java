/*
 * CatSero v2
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface Player {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    static UUID getUUIDByName(String name) {
        OfflinePlayer player = Lists.newArrayList(Bukkit.getOfflinePlayers()).parallelStream().filter(
                i -> Objects.equals(i.getName(), name)
        ).findFirst().orElse(null);
        if (player != null)
            return player.getUniqueId();
        else
            return null;
    }

    /**
     * 由QQ号获取玩家UUID
     *
     * @param code QQ号
     * @return 玩家UUID
     */
    static UUID getUUIDByCode(long code) {
        OfflinePlayer player = Lists.newArrayList(Bukkit.getOfflinePlayers()).parallelStream().filter(
                i -> Objects.equals(i.getName(), WhiteListDatabase.getName(code))
        ).findFirst().orElse(null);
        if (player != null)
            return player.getUniqueId();
        else
            return null;
    }

    /**
     * 得到一个玩家对象
     *
     * @param name 玩家名
     */
    static OfflinePlayer getPlayer(String name) {
        return Bukkit.getOfflinePlayer(getUUIDByName(name));
    }

    /**
     * 得到一个玩家对象
     *
     * @param uuid 玩家UUID
     */
    static OfflinePlayer getPlayer(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid);
    }

    static org.bukkit.entity.Player getOnlinePlayer(String name) {
        return Bukkit.getPlayer(getUUIDByName(name));
    }

    static org.bukkit.entity.Player getOnlinePlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    static List<org.bukkit.entity.Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
}