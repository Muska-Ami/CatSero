package moe.xmcn.catsero.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import moe.xmcn.catsero.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WhiteListDatabase {

    /**
     * 初始化数据库
     */
    public static void initDatabase() {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "create TABLE if not exists RECORDS " +
                    "(NAME TEXT not NULL)";
            sm.executeUpdate(cmd);

            sm.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    private static Connection createDatabaseConnection() throws SQLException {

        HikariConfig hc = new HikariConfig();

        if (Configuration.PLUGIN.JDBC.TYPE.equals("sqlite")) {
            // SQLite
            hc.setPoolName("SQLiteConnectionPool");
            hc.setDriverClassName(Configuration.PLUGIN.JDBC.SQLITE_CLASS_NAME);
            hc.setJdbcUrl(
                    "jdbc:sqlite:" +
                            Configuration.plugin.getDataFolder() +
                            "/whitelist.db"
            );
            hc.setAutoCommit(false);
            HikariDataSource ds = new HikariDataSource(hc);
            return ds.getConnection();
        } else if (Configuration.PLUGIN.JDBC.TYPE.equals("mysql")) {
            // MySQL
            hc.setDriverClassName(Configuration.PLUGIN.JDBC.MYSQL_CLASS_NAME);
            hc.setJdbcUrl(
                    "jdbc:mysql://" +
                            Configuration.PLUGIN.JDBC.MYSQL.HOST +
                            ":" +
                            Configuration.PLUGIN.JDBC.MYSQL.PORT +
                            "/" +
                            Configuration.PLUGIN.JDBC.MYSQL.DATABASE +
                            "?serverTimezone=" +
                            Configuration.PLUGIN.JDBC.MYSQL.TIMEZONE +
                            "&useUnicode=" +
                            Configuration.PLUGIN.JDBC.MYSQL.UNICODE +
                            "&characterEncoding=" +
                            Configuration.PLUGIN.JDBC.MYSQL.ENCODING +
                            "&useSSL=" +
                            Configuration.PLUGIN.JDBC.MYSQL.SSL
            );
            hc.setUsername(Configuration.PLUGIN.JDBC.MYSQL.USERNAME);
            hc.setPassword(Configuration.PLUGIN.JDBC.MYSQL.PASSWORD);
            hc.setAutoCommit(false);
            HikariDataSource ds = new HikariDataSource(hc);
            return ds.getConnection();
        }
        return null;
    }

    /**
     * 获取白名单列表
     *
     * @return 所有在白名单的玩家名
     */
    public List<String> getList() {
        Connection c;
        Statement sm;
        List<String> list = new ArrayList<>();
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            ResultSet rs = sm.executeQuery("select * from RECORDS;");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }

            sm.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return list;
    }

    public boolean insertList(String name) {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "insert into RECORDS" +
                    " values (\"" + name + "\")";
            sm.executeUpdate(cmd);

            sm.close();
            c.commit();
            c.close();
            return true;
        } catch (Exception e) {
            Logger.logCatch(e);
            return false;
        }
    }

    public boolean updateList(String old_name, String new_name) {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "update RECORDS" +
                    " set NAME = \"" + new_name + "\"" +
                    " where NAME = \"" + old_name + "\"";
            sm.executeUpdate(cmd);

            sm.close();
            c.commit();
            c.close();
            return true;
        } catch (Exception e) {
            Logger.logCatch(e);
            return false;
        }
    }

    public boolean removeList(String name) {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "delete from RECORDS" +
                    " where NAME = \"" + name + "\"";
            sm.executeUpdate(cmd);

            sm.close();
            c.commit();
            c.close();
            return true;
        } catch (Exception e) {
            Logger.logCatch(e);
            return false;
        }
    }

}
