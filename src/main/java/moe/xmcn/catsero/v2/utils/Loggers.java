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
package moe.xmcn.catsero.v2.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Loggers {

    static void logINFO(String log) {
        Configs.plugin.getLogger().log(Level.INFO, log);
    }

    static void logWARN(String warn) {
        Configs.plugin.getLogger().log(Level.WARNING, warn);
    }

    interface CustomLevel {
        static void logLoader(String msg) {
            logINFO("[Loader] " + msg);
        }

        static void logLoader(ArrayList<String> msgs) {
            for (int i = 1; i < msgs.toArray().length + 1; i++) {
                logINFO("[Loader] " + msgs.toArray()[i - 1]);
            }
        }

        static void logCatch(Exception e) {
            String error_type = e.getClass().getName();
            String error_message = e.getMessage();
            String error_info = Arrays.toString(e.getStackTrace());

            ArrayList<String> msgs = new ArrayList<>(Arrays.asList(
                    "捕获到一个错误",
                    "错误类型: " + error_type,
                    "捕获消息: " + error_message,
                    "详细信息: " + error_info
            ));
            for (int i = 1; i < msgs.toArray().length + 1; i++) {
                logWARN("[Catch] " + msgs.toArray()[i - 1]);
            }
        }

        static void logSign(String sign, UUID uuid) {
            if (sign.equals("start")) {
                logINFO("$%{CatSignStart@" + uuid + "}%$");
            }
            if (sign.equals("end")) {
                logINFO("$%{CatSignEnd@" + uuid + "}%$");
            }
        }

        static String getSignBlock(UUID uuid) throws IOException {
            //String reg = "\\$%\\{CatSignStart@" + uuid + "}%\\$(.*?)\\$%\\{CatSignEnd@" + uuid + "}%\\$";
            StringBuilder sb = new StringBuilder();
            File file = new File(new File(Configs.plugin.getDataFolder().getParent()).getParent(), "/logs/latest.log");
            System.out.println(file.getPath());
            FileReader fr = new FileReader(file);
            char[] a = new char[50];
            fr.read(a);
            for (char c : a) sb.append(c);
            fr.close();
            String data = sb.toString().replace("\n", "∅");

            // 正则匹配
            String regex = "(?<=\\$%\\{CatSignStart@" + uuid + "}%\\$).*?(?=\\$%\\{CatSignEnd@" + uuid + "}%\\$)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            StringBuilder res = new StringBuilder();
            while (matcher.find()) {
                String mg = matcher.group();
                res.append(mg);
            }
            return res.toString().replace("∅", "\n");
        }
    }

}
