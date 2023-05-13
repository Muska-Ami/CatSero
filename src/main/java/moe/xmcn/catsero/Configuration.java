package moe.xmcn.catsero;

import moe.xmcn.catsero.events.bridge.ParseTool;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Configuration {

    private static final CatSero INSTANCE = CatSero.INSTANCE;

    // 定义配置文件位置
    // def://   -> / ,
    // ext://   -> /extra-configs/ ,
    // mirai:// -> /mirai-configs/
    private static final ArrayList<String> configuration = new ArrayList<>(
            Arrays.asList(
                    "def://config.yml",      // plugin
                    "def://uses-config.yml", // uses
                    "ext://trchat.yml",      // ext_trchat
                    "ext://sql.yml",         // ext_sql
                    "mirai://bot.yml",       // mirai_bot
                    "mirai://group.yml",     // mirai_group
                    "mirai://qq-op.yml",     // mirai_qqop
                    "ext://command-alias.yml"// ext_commandalias
            )
    );
    private static FileConfiguration plugin;
    private static FileConfiguration uses;
    private static FileConfiguration ext_trchat;
    private static FileConfiguration ext_sql;
    private static FileConfiguration ext_commandalias;
    private static FileConfiguration mirai_bot;
    private static FileConfiguration mirai_group;
    private static FileConfiguration mirai_qqop;

    static void registerQQCommand() {
        ParseTool.registerCommand(Arrays.asList(
                "list",
                "tps",
                "cmd",
                "whitelist"
        ));
    }

    public static FileConfiguration getPlugin() {
        return plugin;
    }

    public static FileConfiguration getUses() {
        return uses;
    }

    public static FileConfiguration getExt_trchat() {
        return ext_trchat;
    }

    public static FileConfiguration getExt_sql() {
        return ext_sql;
    }

    //public static FileConfiguration getMirai_bot() { return mirai_bot; }

    //public static FileConfiguration getMirai_group() { return mirai_group; }

    public static FileConfiguration getExt_commandalias() {
        return ext_commandalias;
    }

    /**
     * 生成Yaml节点ID
     *
     * @param useid 功能ID
     * @param arr   子节点ID
     * @return Yaml节点ID
     */
    public static String buildYaID(String useid, @NotNull ArrayList<String> arr) {
        StringBuilder opt = new StringBuilder();
        StringBuilder finalOpt = opt;
        arr.forEach(it -> finalOpt.append(it).append("."));
        opt = new StringBuilder(opt.substring(0, opt.length() - 1));
        String output = (useid + "." + opt);
        Logger.logDebug("构建一个节点: " + useid + " - " + opt + ", 结果: " + output);
        return output;
    }

    /**
     * 获取功能指定的Bot
     *
     * @param use 功能ID
     * @return BotID
     */
    public static String getUseMiraiBot(String use) {
        /*return uses.getString(buildYaID(use, new ArrayList<>(Arrays.asList(
                "var", "bot"
        ))));
         */
        String it = getUses().getString(buildYaID(use, new ArrayList<>(Arrays.asList(
                "var", "bot"
        ))));
        Logger.logDebug("获取一个BotID: " + it);
        return it;
    }

    /**
     * 获取功能指定的Group列表
     *
     * @param use 功能ID
     * @return String[] GroupID列表
     */
    public static List<String> getUseMiraiGroup(String use) {
        return uses.getStringList(buildYaID(use, new ArrayList<>(Arrays.asList(
                "var", "groups"
        ))));
    }

    public static void saveFiles() {
        Logger.logLoader("Saving plugin configuration files...");
        for (int i = configuration.size() - 1; i >= 0; i--) {
            if (!new File(formatPath(configuration.get(i), true)).exists()) {
                //Logger.logWARN(cleanPath(configuration.get(i)));
                INSTANCE.saveResource(cleanPath(configuration.get(i)/*, false*/), false);
                Logger.logLoader("Save: " + cleanPath(configuration.get(i)));
            }
        }
        Logger.logLoader("Saved.");

        Logger.logLoader("Saving default i18n locale file...");
        if (!new File(INSTANCE.getDataFolder() + "/locale/zh_CN.json").exists()) {
            INSTANCE.saveResource("locale/zh_CN.json", false);
            Logger.logLoader("Save: locale/zh_CN.json");
        }
        Logger.logLoader("Saved.");
    }

    /**
     * 格式化文件路径
     *
     * @param code 缩略路径
     * @param apdf 是否添加插件目录位置
     * @return 格式化后的标准路径
     */
    private static String formatPath(String code, boolean apdf) {
        if (apdf) return code.replaceFirst("def://", INSTANCE.getDataFolder().getPath().replace("\\", "/") + "/")
                .replaceFirst("ext://", INSTANCE.getDataFolder().getPath().replace("\\", "/") + "/extra-configs/")
                .replaceFirst("mirai://", INSTANCE.getDataFolder().getPath().replace("\\", "/") + "/mirai-configs/");
        else return code.replaceFirst("def://", "/")
                .replaceFirst("ext://", "/extra-configs/")
                .replaceFirst("mirai://", "/mirai-configs/");

    }

    /**
     * 清理文件路径
     *
     * @param code 缩略路径
     * @return 名称
     */
    private static String cleanPath(String code) {
        return code.replaceFirst("def://", "")
                .replaceFirst("ext://", "extra-configs/")
                .replaceFirst("mirai://", "mirai-configs/");
    }

    /**
     * 加载配置文件
     */
    static void loadConfiguration() throws IOException {
        plugin = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(0), true)));
        uses = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(1), true)));
        ext_trchat = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(2), true)));
        ext_sql = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(3), true)));
        mirai_bot = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(4), true)));
        mirai_group = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(5), true)));
        mirai_qqop = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(6), true)));
        ext_commandalias = YamlConfiguration.loadConfiguration(new File(formatPath(configuration.get(7), true)));

        plugin.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(0))))));
        uses.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(1))))));
        ext_trchat.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(2))))));
        ext_sql.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(3))))));
        mirai_bot.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(4))))));
        mirai_group.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(5))))));
        mirai_qqop.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(6))))));
        ext_commandalias.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(INSTANCE.getResource(cleanPath(configuration.get(7))))));

        plugin.save(new File(formatPath(configuration.get(0), true)));
        uses.save(new File(formatPath(configuration.get(1), true)));
        ext_trchat.save(new File(formatPath(configuration.get(2), true)));
        ext_sql.save(new File(formatPath(configuration.get(3), true)));
        mirai_bot.save(new File(formatPath(configuration.get(4), true)));
        mirai_group.save(new File(formatPath(configuration.get(5), true)));
        mirai_qqop.save(new File(formatPath(configuration.get(6), true)));
        ext_commandalias.save(new File(formatPath(configuration.get(7), true)));
    }

    /**
     * 验证是否为QQOp
     *
     * @param code QQ号
     * @return true/false
     */
    public boolean isQQOp(long code) {
        AtomicBoolean res = new AtomicBoolean(false);
        mirai_qqop.getLongList("list").forEach(it -> {
            if (code == it) res.set(true);
        });
        return res.get();
    }

    /**
     * 获取Bot QQ号
     *
     * @param id BotID
     * @return BotCode/0
     */
    public long getBotCode(@NotNull String id) {
        return mirai_bot.getLong("list." + id);
    }

    /**
     * 对比Bot是否是指定Bot
     *
     * @param code QQ号
     * @param id   BotID
     * @return true/false
     */
    public boolean checkBot(long code, @NotNull String id) {
        boolean it = getBotCode(id) == code;
        Logger.logDebug("检查Bot, 事件BotCode: " + code + " | BotID: " + id + ", 结果: " + it);
        return it;
    }

    /**
     * 获取群号
     *
     * @param id GroupID
     * @return GroupCode/0
     */
    public long getGroupCode(@NotNull String id) {
        return mirai_group.getLong("list." + id);
    }

    /**
     * 对比Group是否是指定Group
     *
     * @param code QQ号
     * @param ids  GroupID
     * @return true/false
     */
    public boolean checkGroup(long code, @NotNull List<String> ids) {
        AtomicBoolean is = new AtomicBoolean(false);
        ids.forEach(id ->
                is.set(getGroupCode(id) == code)
        );
        Logger.logDebug("检查Group, 事件GroupCode: " + code + " | GroupIDs: " + ids + ", 结果: " + is.get());
        return is.get();
    }

    public interface USESID {
        String CHAT_FORWARD = "chat-forward";
        String SEND_PLAYER_JOIN_QUIT = "send-player-join-quit";
        String SEND_PLAYER_DEATH = "send-player-death";
        String NEW_GROUP_MEMBER_NOTIFICATION = "new-group-member-notification";
        String SEND_ADVANCEMENT = "send-advancement";
        String GET_TPS = "get-tps";
        String GET_ONLINE_LIST = "get-online-list";
        String QWHITELIST = "qwhitelist";
        String GROUP_MEMBER_LEAVE_NOTIFICATION = "group-member-leave-notification";
        String QCMD = "qcmd";
        String INFO_PLACEHOLDER = "info-placeholder";
    }

    // 数据库相关
    // 由于涉及到数据库，不允许重载此内容
    public interface SQL {
        String TYPE = ext_sql.getString("type");

        interface JDBC {
            String sub_node = "jdbc" + ".";

            String SQLITE_CLASS_NAME = ext_sql.getString(sub_node + "sqlite-class-name");
            String MYSQL_CLASS_NAME = ext_sql.getString(sub_node + "mysql-class-name");
        }

        interface MYSQL {
            String sub_node = "mysql-config" + ".";

            String HOST = ext_sql.getString(sub_node + "host");
            int PORT = ext_sql.getInt(sub_node + "port");
            String USERNAME = ext_sql.getString(sub_node + "username");
            String PASSWORD = ext_sql.getString(sub_node + "password");
            String TIMEZONE = ext_sql.getString(sub_node + "timezone");
            boolean UNICODE = ext_sql.getBoolean(sub_node + "unicode");
            String ENCODING = ext_sql.getString(sub_node + "encoding");
            boolean SSL = ext_sql.getBoolean(sub_node + "ssl");
            String DATABASE = ext_sql.getString(sub_node + "database");
        }
    }

    public interface OTHER {
        File floodgate_file = new File(INSTANCE.getDataFolder().getParent() + "/floodgate", "config.yml");
        FileConfiguration floodgate_config = YamlConfiguration.loadConfiguration(floodgate_file);
    }
}
