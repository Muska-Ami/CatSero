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

import moe.xmcn.catsero.Configuration;

import java.util.ArrayList;
import java.util.List;

public interface QPS {

    static List<String> custom = new ArrayList<>();

    /**
     * 解析QQ群命令
     *
     * @param message 消息
     * @return 数组或null
     */
    static String[] parse(String message) {
        String pmhv = getParser.checkViaCommandHeader(message);
        String pmhc = getParser.checkCustomCommandHeader(message);
        if (pmhv != null) {
            return pmhv.split(" ");
        } else if (
                Configuration.PLUGIN.COMMAND_PREFIX.ENABLE &&
                        pmhc != null
        ) {
            return pmhc.split(" ");
        } else {
            return null;
        }
    }

    class getParser {

        /**
         * 判断是否有命令头，并处理命令头
         *
         * @param message 消息
         * @return 处理后的消息或null
         */
        private static String checkViaCommandHeader(String message) {
            if (message.startsWith("!catsero")) {
                //解析命令头(!)
                message = message.replaceFirst("!catsero ", "");
                return message;
            } else if (message.startsWith("/catsero")) {
                //解析命令头(/)
                message = message.replaceFirst("/catsero ", "");
                return message;
            }
            return null;
        }

        /**
         * 判断是否有命令头，并处理命令头
         *
         * @param message 消息
         * @return 处理后的消息或null
         */
        private static String checkCustomCommandHeader(String message) {
            String custom_head = Configuration.PLUGIN.COMMAND_PREFIX.PREFIX;
            if (message.startsWith("!" + custom_head)) {
                //解析命令头(!)
                message = message.replaceFirst("!" + custom_head + " ", "");
                return message;
            } else if (message.startsWith("/" + custom_head)) {
                //解析命令头(/)
                message = message.replaceFirst("/" + custom_head + " ", "");
                return message;
            }
            return null;
        }

        /**
         * 判定是否是自定义命令
         */
        private static String checkCustomCommand() {
            return null;
        }

    }

}