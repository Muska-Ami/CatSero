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
package moe.xmcn.catsero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Configuration {

    // 定义plugin
    Plugin plugin = CatSero.getPlugin(CatSero.class);

    private static JSONObject buildObject() throws IOException {
        String locale;
        locale = Objects.requireNonNull(PLUGIN.LOCALE, "zh_CN");

        InputStreamReader isr = new InputStreamReader(new FileInputStream(plugin.getDataFolder() + "/locale/" + locale + ".json"), StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(isr);
        String body;
        StringBuilder data = new StringBuilder();
        while ((body = in.readLine()) != null) {
            data.append(body);
        }

        return JSON.parseObject(data.toString());
    }

    private static JSONObject getIJObject() {
        try {
            return buildObject();
        } catch (IOException e) {
            Logger.logCatch(e);
        }
        return null;
    }

    static void saveFiles() {
        Logger.logLoader("Saving plugin & uses config...");
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) plugin.saveResource("config.yml", false);
        if (!new File(plugin.getDataFolder(), "uses-config.yml").exists())
            plugin.saveResource("uses-config.yml", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving mirai-configs...");
        if (!new File(plugin.getDataFolder(), "mirai-configs/bot.yml").exists())
            plugin.saveResource("mirai-configs/bot.yml", false);
        if (!new File(plugin.getDataFolder(), "mirai-configs/group.yml").exists())
            plugin.saveResource("mirai-configs/group.yml", false);
        if (!new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml").exists())
            plugin.saveResource("mirai-configs/qq-op.yml", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving extra-configs...");
        if (!new File(plugin.getDataFolder(), "extra-configs/trchat.yml").exists()) {
            plugin.saveResource("extra-configs/trchat.yml", false);
        }
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving default locale...");
        if (!new File(plugin.getDataFolder(), "locale/zh_CN.json").exists())
            plugin.saveResource("locale/zh_CN.json", false);
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving version...");
        plugin.saveResource("version", true);
        Logger.logLoader("Saved.");
    }

    static void reloadFiles() throws IOException, InvalidConfigurationException {
        Logger.logLoader("Saving files...");
        saveFiles();
        Logger.logLoader("Saved all files.");

        Logger.logLoader("Reloading files...");
        CFI.plugin_config.load(new File("config.yml"));
        CFI.uses_config.load(new File("uses-config.yml"));

        CFI.bot_config.load(new File("mirai-configs/qq.yml"));
        CFI.group_config.load(new File(plugin.getDataFolder(), "mirai-configs/group.yml"));
        CFI.qqop_config.load(new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml"));

        CFI.ext_trchat_config.load(new File(plugin.getDataFolder(), "extra-configs/trchat.yml"));
        Logger.logLoader("Reloaded.");
    }

    // 配置定义
    interface PLUGIN {

        String LOCALE = CFI.plugin_config.getString("locale");
        boolean BSTATS = CFI.plugin_config.getBoolean("bstats");

        interface CHECK_UPDATE {
            /* 定义节点 为了区分
             用拼凑的`"check-update" + "."`
             而不是`"check-update."`
             */
            String sub_node = "check-update" + ".";

            boolean ENABLE = CFI.plugin_config.getBoolean(sub_node + "enable");
            int INTERVAL = CFI.plugin_config.getInt(sub_node + "interval");
            String API_URL = CFI.plugin_config.getString(sub_node + "api-url");
            String MODE = CFI.plugin_config.getString(sub_node + "mode");
        }

        interface CUSTOM_QQ_COMMAND_PREFIX {
            String sub_node = "custom-qq-command-prefix" + ".";

            boolean ENABLE = CFI.plugin_config.getBoolean(sub_node + "enable");
            String PREFIX = CFI.plugin_config.getString(sub_node + "prefix");
        }
    }

    interface USES_CONFIG {
        interface SEND_PLAYER_JOIN_QUIT {
            String sub_node = "send-player-join-quit" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            boolean NEED_PERMISSION = CFI.uses_config.getBoolean(sub_node + "need-permission");

            interface FORMAT {
                String sub_node = "send-player-join-quit.format" + ".";

                String JOIN = CFI.uses_config.getString(sub_node + "join");
                String QUIT = CFI.uses_config.getString(sub_node + "quit");
            }

            interface MIRAI {
                String sub_node = "send-player-join-quit.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface SEND_PLAYER_DEATH {
            String sub_node = "send-player-death" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            String FORMAT = CFI.uses_config.getString(sub_node + "format");
            boolean NEED_PERMISSION = CFI.uses_config.getBoolean(sub_node + "need-permission");

            interface MIRAI {
                String sub_node = "send-player-death.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface GET_TPS {
            String sub_node = "get-tps" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");

            interface MIRAI {
                String sub_node = "get-tps.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface SEND_ADVANCEMENT {
            String sub_node = "send-advancement" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            String FORMAT = CFI.uses_config.getString(sub_node + "format");
            boolean NEED_PERMISSION = CFI.uses_config.getBoolean(sub_node + "need-permission");

            interface MIRAI {
                String sub_node = "send-advancement.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface NEW_GROUP_MEMBER {
            String sub_node = "new-group-member" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            String FORMAT = CFI.uses_config.getString(sub_node + "format");

            interface MIRAI {
                String sub_node = "new-group-member.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface GET_ONLINE_LIST {
            String sub_node = "get-online-list" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            String FORMAT_0 = CFI.uses_config.getString(sub_node + "format.0");
            String FORMAT_1 = CFI.uses_config.getString(sub_node + "format.1");

            interface MIRAI {
                String sub_node = "get-online-list.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }

        interface CHAT_FORWARD {
            String sub_node = "chat-forward" + ".";

            boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");
            boolean ALLOW_MIRAICODE = CFI.uses_config.getBoolean(sub_node + "allow-miraicode");
            boolean USE_BIND = CFI.uses_config.getBoolean(sub_node + "use-bind");

            interface CLEAN_STYLECODE {
                String sub_node = "chat-forward.clean-stylecode" + ".";

                boolean TO_MC = CFI.uses_config.getBoolean(sub_node + "to-mc");
                boolean TO_QQ = CFI.uses_config.getBoolean(sub_node + "to-qq");
            }

            interface FILTER {
                String sub_node = "chat-forward.filter" + ".";

                boolean ENABLE = CFI.uses_config.getBoolean(sub_node + "enable");

                interface LIST {
                    String sub_node = "chat-forward.filter.list" + ".";

                    List<String> TO_MC = CFI.uses_config.getStringList(sub_node + "to-mc");
                    List<String> TO_QQ = CFI.uses_config.getStringList(sub_node + "to-qq");
                }
            }

            interface FORMAT {
                String sub_node = "chat-forward.format" + ".";

                String TO_MC = CFI.uses_config.getString(sub_node + "to-mc");
                String TO_QQ = CFI.uses_config.getString(sub_node + "to-qq");
            }

            interface MIRAI {
                String sub_node = "chat-forward.var" + ".";

                String BOT = CFI.uses_config.getString(sub_node + "bot");
                String GROUP = CFI.uses_config.getString(sub_node + "group");
            }
        }
    }

    interface EXTRA_CONFIG {
        interface TRCHAT {
            interface CHAT_FORWARD {
                String sub_node = "chat-forward" + ".";

                List<String> CHANNEL = CFI.ext_trchat_config.getStringList(sub_node + "channel");
            }
        }
    }

    interface I18N {
        JSONObject object = getIJObject();

        interface MINECRAFT {
            JSONObject minecraft = object.getJSONObject("minecraft");

            interface COMMAND {
                JSONObject command = minecraft.getJSONObject("command");

                String INVALID_OPTION = command.getString("invalid-option");
                String NO_PERMISSION = command.getString("no-permission");

                interface RELOAD {
                    JSONObject reload = command.getJSONObject("reload");

                    String SUCCESS = reload.getString("success");
                }

                interface CMS {
                    JSONObject cms = command.getJSONObject("cms");

                    String SENT = cms.getString("sent");
                    String ERROR = cms.getString("error");
                }
            }

            interface USE {
                JSONObject use = minecraft.getJSONObject("use");

                interface CHAT_FORWARD {
                    JSONObject chat_forward = use.getJSONObject("chat-forward");

                    String CASE_MIRAICODE = chat_forward.getString("case-miraicode");
                }
            }

            interface CALL {
                JSONObject call = minecraft.getJSONObject("call");

                String PLAYER = call.getString("player");
                String ADMIN = call.getString("admin");
            }
        }

        interface QQ {
            JSONObject qq = object.getJSONObject("qq");

            interface COMMAND {
                JSONObject command = qq.getJSONObject("command");

                String INVALID_OPTION = command.getString("invalid-option");
                String NO_PERMISSION = command.getString("no-permission");
            }

            interface CALL {
                JSONObject call = qq.getJSONObject("call");

                String MEMBER = call.getString("member");
                String ADMIN = call.getString("admin");
                String OWNER = call.getString("owner");
            }
        }
    }

    interface Interface {
        /*
        /**
         * 获得回执文本
         *
         * @param iid 文本I18nID
         * @return 回执文本/undefined
         *\/
        static String getI18n(String iid) throws IOException {
            String locale;
            locale = Objects.requireNonNull(PLUGIN.LOCALE, "zh_CN");

            BufferedReader in = new BufferedReader(new FileReader(plugin.getDataFolder() + "locale/" + locale + ".json"));
            String body;
            StringBuilder data = new StringBuilder();
            while ((body = in.readLine()) != null) {
                data.append(body);
            }

            JSONObject object = JSON.parseObject(data.toString());

            String[] node_tree = iid.split("\\.");
            for (var i = 0;i<=node_tree.length - 1;i++) {
                object.get()
            }

            /*if (messageData.getString(iid) != null) {
                return messageData.getString(iid);
            } else {
                return "undefined";
            }*\/
        }
        */

        static long getBotCode(String botid) {
            return CFI.bot_config.getLong("list." + botid);
        }

        static long getGroupCode(String groupid) {
            return CFI.group_config.getLong("list." + groupid);
        }

        static boolean isQQOp(long senderID) {
            AtomicBoolean isOp = new AtomicBoolean(false);
            CFI.qqop_config.getLongList("list").forEach(it -> {
                if (it == senderID) {
                    isOp.set(true);
                }
            });
            return isOp.get();
        }
    }

    class CFI {
        public static File version_file = new File(plugin.getDataFolder(), "version");
        // File
        static File config_file = new File(plugin.getDataFolder(), "config.yml");
        static FileConfiguration plugin_config = YamlConfiguration.loadConfiguration(config_file);
        static File usesconfig_file = new File(plugin.getDataFolder(), "uses-config.yml");
        static FileConfiguration uses_config = YamlConfiguration.loadConfiguration(usesconfig_file);
        static File mirai_bot_file = new File(plugin.getDataFolder(), "mirai-configs/bot.yml");
        static FileConfiguration bot_config = YamlConfiguration.loadConfiguration(mirai_bot_file);
        static File mirai_group_file = new File(plugin.getDataFolder(), "mirai-configs/group.yml");
        static FileConfiguration group_config = YamlConfiguration.loadConfiguration(mirai_group_file);
        static File mirai_qqop_file = new File(plugin.getDataFolder(), "mirai-configs/qq-op.yml");
        static FileConfiguration qqop_config = YamlConfiguration.loadConfiguration(mirai_qqop_file);
        static File ext_trchat_file = new File(plugin.getDataFolder(), "extra-configs/trchat.yml");
        static FileConfiguration ext_trchat_config = YamlConfiguration.loadConfiguration(ext_trchat_file);
    }

}
