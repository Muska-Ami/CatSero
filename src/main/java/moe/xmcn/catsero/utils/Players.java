/*
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
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Players {
    /**
     * 由玩家名获取玩家UUID
     *
     * @param name 玩家名
     * @return 玩家UUID
     */
    private static UUID getUUIDByName(String name) {
        return Objects.requireNonNull(Lists.newArrayList(Bukkit.getOfflinePlayers()).parallelStream().filter(
                i -> Objects.equals(i.getName(), name)
        ).findFirst().orElse(null)).getUniqueId();
    }

    /**
     * 由UUID获取玩家名
     * UUID类型参数
     *
     * @param uuid 玩家UUID
     * @return 玩家名
     */
    private static String getNameByUUID(UUID uuid) {
        return Objects.requireNonNull(Lists.newArrayList(Bukkit.getOfflinePlayers()).parallelStream().filter(
                i -> Objects.equals(i.getUniqueId(), uuid)
        ).findFirst().orElse(null)).getName();
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
        return Bukkit.getOfflinePlayer(uuid);
    }

}