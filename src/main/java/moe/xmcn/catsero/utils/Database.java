package moe.xmcn.catsero.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class Database {

    public static class UUIDDatabase {
        private static Connection getDatabase() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:database.db");
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, "连接数据库发生异常:\n" + Arrays.toString(e.getStackTrace()));
            }
            return c;
        }

        public static void intTable() throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "CREATE TABLE IF NOT EXISTS UUIDTableMap" +
                        "(NAME TEXT NOT     NULL, " +
                        " UUID TEXT NOT     NULL)";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(String player_name, UUID player_uuid) throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "INSERT INTO UUIDTableMap (NAME,UUID)" +
                        "VALUES (" + player_name + "," + player_uuid + ")";
            cs.executeUpdate(ct);
            cs.close();
        }
    }

    public static class BanDatabase {
        private static Connection getDatabase() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:database.db");
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, "连接数据库发生异常:\n" + Arrays.toString(e.getStackTrace()));
            }
            return c;
        }

        public static void intTable() throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "CREATE TABLE IF NOT EXISTS BanTableMap" +
                    "(UUID TEXT NOT     NULL, " +
                    " QQ   INT  NOT     NULL)";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(UUID player_uuid, Long player_qq) throws SQLException {
            Statement cs = getDatabase().createStatement();
            String ct = "INSERT INTO BanTableMap (UUID,QQ)" +
                    "VALUES (" + player_uuid + "," + player_qq + ")";
            cs.executeUpdate(ct);
            cs.close();
        }
    }

}
