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
package moe.xmcn.catsero.events.listeners.PingHost;

import moe.xmcn.catsero.utils.Punycode;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    /**
     * 向某个地址发包，并收取包
     *
     * @param ars 地址
     * @return 收回的包
     * @throws UnknownHostException 未知主机
     */
    public static String PingHostUtils(String ars) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(Punycode.encodeURL(ars));
        int flag = 0;
        for (int i = 0; i < 4; i++) {
            boolean b;
            try {
                assert address != null;
                b = address.isReachable(1000);
            } catch (IOException e) {
                return "Error";
            }
            if (b)
                flag++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return "Error";
            }
        }
        return String.valueOf(flag);
    }
}
