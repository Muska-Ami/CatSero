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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface QPS {

    /**
     * 解析命令
     *
     * @param message 接收消息原文
     * @param command 命令名称
     * @return 解析后的数组，如果解析失败则直接返回null
     */
    static String[] parse(@NotNull String message, @NotNull String command) {
        /*
        思路：先检查默认的命令，如不成功则继续检查别名
        直接将命令去除，返回参数
         */
        String vcp = new ParseTool().vCheckP(message, command);
        String ccp = new ParseTool().cCheckP(message, command);
        String ecp = new ParseTool().eCheckP(message, command);

        /*
        System.out.println(vcp);
        System.out.println(ccp);
        System.out.println(ecp);

         */

        if (vcp != null)
            return vcp.split(" ");
        else if (ccp != null)
            return ccp.split(" ");
        else if (ecp != null)
            return ecp.split(" ");
        return null;
    }

    /**
     * 解析QQ群命令
     *
     * @param message 消息
     * @return 数组或null
     */
    @Deprecated
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

    class ParseTool {

        String ess = null;

        private String vCheckP(@NotNull String message, @NotNull String command) {
            //解析命令头(/,!)
            if (message.startsWith("!catsero " + command)) {
                return message.replaceFirst(
                        "!catsero " + command + " ",
                        ""
                );
            } else if (message.startsWith("/catsero " + command)) {
                return message.replaceFirst(
                        "/catsero " + command + " ",
                        ""
                );
            }
            return null;
        }

        private String cCheckP(@NotNull String message, @NotNull String command) {
            if (message.startsWith("!" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + " " + command)) {
                // CH -> !
                return message.replaceFirst(
                        "!" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + command + " ",
                        ""
                );
            } else if (message.startsWith("/" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + " " + command)) {
                // CH -> /
                return message.replaceFirst(
                        "/" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + command + " ",
                        ""
                );
            }
            return null;
        }

        private String eCheckP(@NotNull String message, @NotNull String command) {
            if (Configuration.CFI.command_alias_config.getBoolean("enable")) {
                List<String> aliasList = Configuration.CFI.command_alias_config.getStringList(command);
                aliasList.forEach(alias -> {
                    if (message.startsWith("!" + alias)) {
                        // CH -> !
                        ess = message.replaceFirst(
                                "!" + alias + " ",
                                ""
                        );
                    } else if (message.startsWith("/" + alias)) {
                        // CH -> /
                        ess = message.replaceFirst(
                                "/" + alias + " ",
                                ""
                        );
                    }
                });
                return ess;
            }
            return null;
        }

        /*
         更高级的命令解析
         制作中ing...
         我声明绝对没有咕咕咕
         */
        private String sCheckP(String message, String command) {
            if (Configuration.CFI.command_alias_config.getBoolean("enable")) {

            }
            return null;
        }
    }

    @Deprecated
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

    }

}
