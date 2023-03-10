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
package moe.xmcn.catsero.events.bridge;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ParseTool {

    private static final ArrayList<String> commands = new ArrayList<>();

    private String command;
    private String label;
    private String[] arguments;
    private boolean custom;

    private String ess = null;

    /**
     * 解析命令
     *
     * @param message 接收消息原文
     * @return 解析后的数组，如果解析失败则直接返回null
     */
    boolean parse(@NotNull String message) {
        /*
        思路：先检查默认的命令，如不成功则继续检查别名
        直接将命令去除，返回参数
         */
        Logger.logDebug("尝试解析命令体");
        parseCommand(message);

        if (this.command != null) {

            String[] villa = parseVilla(message, this.command);
            String[] villax = parseVillaX(message, this.command);
            String[] custom = parseCustom(message, this.command);

            if (villa != null) {
                this.arguments = villa;
                this.custom = false;
                this.label = "catsero";
                return true;
            } else if (villax != null) {
                this.arguments = villax;
                this.custom = false;
                this.label = Configuration.PLUGIN.COMMAND_PREFIX.PREFIX;
                return true;
            } else if (custom != null) {
                this.arguments = custom;
                this.custom = true;
                return true;
            } else return false;
        } else return false;
    }

    private void parseCommand(@NotNull String message) {
        message = message.substring(1);
        String[] msplt = message.split(" ");

        if (message.startsWith("catsero ")) {
            if (msplt.length > 1) {
                this.command = msplt[1];
                Logger.logDebug("命令标头：" + this.command);
            }
        } else if (message.startsWith(Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + " " + command)) {
            if (msplt.length > 1) {
                this.command = msplt[1];
                Logger.logDebug("命令标头：" + this.command);
            }
        } else {
            commands.forEach(cmd -> {
                if (Configuration.CFI.command_alias_config.getStringList(cmd).contains(msplt[0])) {
                    this.command = msplt[0];
                    Logger.logDebug("命令标头：" + this.command);
                }
            });
        }
    }

    private String[] parseVilla(@NotNull String message, @NotNull String command) {
        //解析命令头(/,!)
        if (message.startsWith("!catsero " + command)) {
            return message.replaceFirst(
                    "!catsero " + command + " ",
                    ""
            ).split(" ");
        } else if (message.startsWith("/catsero " + command)) {
            return message.replaceFirst(
                    "/catsero " + command + " ",
                    ""
            ).split(" ");
        }
        return null;
    }

    private String[] parseVillaX(@NotNull String message, @NotNull String command) {
        if (message.startsWith("!" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + " " + command)) {
            // CH -> !
            return message.replaceFirst(
                    "!" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + command + " ",
                    ""
            ).split(" ");
        } else if (message.startsWith("/" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + " " + command)) {
            // CH -> /
            return message.replaceFirst(
                    "/" + Configuration.PLUGIN.COMMAND_PREFIX.PREFIX + command + " ",
                    ""
            ).split(" ");
        }
        return null;
    }

    private String[] parseCustom(@NotNull String message, @NotNull String command) {
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
            return ess.split(" ");
        }
        return null;
    }

    public String getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArguments() {
        return arguments;
    }

    public boolean isCustom() {
        return custom;
    }

    public static void registerCommand(@NotNull String command) {
        commands.add(command);
    }

    public static void registerCommand(@NotNull List<String> command) {
        commands.addAll(command);
    }
}
