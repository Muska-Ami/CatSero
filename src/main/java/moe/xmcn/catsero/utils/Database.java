package moe.xmcn.catsero.utils;

import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class Database {

    public static class UUIDDatabase {
        private static Connection getDatabase() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + Config.plugin.getDataFolder() + "/database.db");
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, "连接数据库发生异常:\n" + Arrays.toString(e.getStackTrace()));
            }
            return c;
        }

        public static void intTable() throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "CREATE TABLE IF NOT EXISTS UUIDTableMap" +
                    "(NAME TEXT     NOT NULL, " +
                    " UUID TEXT     NOT NULL)";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(String player_name, UUID player_uuid) throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "INSERT INTO UUIDTableMap (NAME, UUID)" +
                    " VALUES ('" + player_name + "', '" + player_uuid + "')";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static class readTable {
            public static UUID getUUID(String name) throws SQLException {
                UUID uuid = null;

                Statement cs = getDatabase().createStatement();
                ResultSet rs = cs.executeQuery("SELECT * FROM UUIDTableMap;");
                while (rs.next()) {
                    uuid = UUID.fromString(rs.getString(name));
                }
                rs.close();
                cs.close();
                return uuid;
            }

            public static String getName(UUID uuid) throws SQLException {
                String name = null;

                Statement cs = getDatabase().createStatement();
                ResultSet rs = cs.executeQuery("SELECT * FROM UUIDTableMap;");
                while (rs.next()) {
                    name = rs.getString(String.valueOf(uuid));
                }
                rs.close();
                cs.close();
                return name;
            }
        }
    }

    public static class BanDatabase {
        private static Connection getDatabase() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + Config.plugin.getDataFolder() + "/database.db");
            } catch (SQLException | ClassNotFoundException e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
            }
            return c;
        }

        public static void intTable() throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "CREATE TABLE IF NOT EXISTS BanTableMap" +
                    "(UUID  TEXT     NOT NULL, " +
                    " ISBAN INT      NOT NULL)";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(UUID player_uuid) throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "INSERT INTO BanTableMap (UUID, ISBAN)" +
                    " VALUES ('" + player_uuid + "', " + 1 + ")";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(Long player_qq) throws SQLException {
            Statement cs = getDatabase().createStatement();
            UUID player_uuid = MiraiMC.getBind(player_qq);
            String ct = "INSERT INTO BanTableMap (UUID, ISBAN)" +
                    " VALUES ('" + player_uuid + "', " + 1 + ")";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void uninsertTable(UUID player_uuid) throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "INSERT INTO BanTableMap (UUID, ISBAN)" +
                    " VALUES ('" + player_uuid + "', " + 0 + ")";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void uninsertTable(Long player_qq) throws SQLException {
            Statement cs = getDatabase().createStatement();
            UUID player_uuid = MiraiMC.getBind(player_qq);
            String ct = "INSERT INTO BanTableMap (UUID, ISBAN)" +
                    " VALUES ('" + player_uuid + "', " + 0 + ")";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static boolean queryBan(UUID player_uuid) throws SQLException {
            boolean result = false;
            Statement cs = getDatabase().createStatement();
            ResultSet rs = cs.executeQuery("SELECT * FROM BanTableMap");
            while (rs.next()) {
                if (Integer.parseInt(rs.getString(String.valueOf(player_uuid))) == 1) {
                    result = true;
                }
            }
            rs.close();
            cs.close();
            return result;
        }

        public static class UUIDRecord implements Listener {
            @EventHandler
            public void record(PlayerJoinEvent pje) {
                String player_name = pje.getPlayer().getName();
                UUID player_uuid = pje.getPlayer().getUniqueId();
                try {
                    Database.UUIDDatabase.insertTable(player_name, player_uuid);
                } catch (SQLException e) {
                    Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
                }
            }
        }

        public static class BanRecord {
            /**
             * 使用玩家UUID添加封禁
             *
             * @param player_uuid 玩家UUID
             */
            public static void record(UUID player_uuid) {
                try {
                    Database.BanDatabase.insertTable(player_uuid);
                } catch (SQLException e) {
                    Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
                }
            }

            /**
             * 使用玩家绑定QQ号添加封禁（需要MiraiMC内置绑定QQ）
             *
             * @param player_qq 玩家QQ号
             */
            public static void record(Long player_qq) {
                try {
                    Database.BanDatabase.insertTable(player_qq);
                } catch (SQLException e) {
                    Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
                }
            }

            /**
             * 使用玩家UUID移除封禁
             *
             * @param player_uuid 玩家UUID
             */
            public static void unrecord(UUID player_uuid) {
                try {
                    Database.BanDatabase.uninsertTable(player_uuid);
                } catch (SQLException e) {
                    Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
                }
            }

            /**
             * 使用玩家绑定QQ号移除封禁（需要MiraiMC内置绑定QQ）
             *
             * @param player_qq 玩家QQ号
             */
            public static void unrecord(Long player_qq) {
                try {
                    Database.BanDatabase.uninsertTable(player_qq);
                } catch (SQLException e) {
                    Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error").replace("%error%", Arrays.toString(e.getStackTrace())));
                }
            }
        }

    }

}
