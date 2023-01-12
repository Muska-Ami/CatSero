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

public interface WhiteListDatabase {

    /**
     * 初始化数据库
     */
    static void initDatabase() {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "create TABLE if not exists RECORDS " +
                    "(NAME TEXT not NULL," +
                    " CODE LONG not NULL)";
            sm.executeUpdate(cmd);

            sm.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    /**
     * 创建一个连接
     * @return 连接
     * @throws SQLException SQL错误
     */
    static Connection createDatabaseConnection() throws SQLException {

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
     * @return 所有在白名单的玩家名
     */
    static List<String> getNameList() {
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

    /**
     * 新增绑定
     * @param name 名称
     * @param code QQ号
     * @return 是否成功
     */
    static boolean insertList(String name, long code) {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "insert into RECORDS" +
                    " values (\"" + name + "\", \"" + code + "\")";
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

    /**
     * 更新绑定名称
     * @param old_name 旧名称
     * @param new_name 新名称
     * @return 是否成功
     */
    static boolean updateList(String old_name, String new_name) {
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

    /**
     * 更新绑定QQ号
     * @param old_code 旧QQ号
     * @param new_code 新QQ号
     * @return 是否成功
     */
    static boolean updateList(long old_code, long new_code) {
        Connection c;
        Statement sm;
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            String cmd = "update RECORDS" +
                    " set CODE = \"" + new_code + "\"" +
                    " where CODE = \"" + old_code + "\"";
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

    static boolean removeList(String name) {
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

    /**
     * 获取白名单列表
     *
     * @return 所有在白名单的玩家名
     */
    static List<Long> getCodeList() {
        Connection c;
        Statement sm;
        List<Long> list = new ArrayList<>();
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            ResultSet rs = sm.executeQuery("select * from RECORDS;");
            while (rs.next()) {
                list.add(rs.getLong("code"));
            }

            sm.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return list;
    }

    static long getCode(String name) {
        Connection c;
        Statement sm;
        List<Long> list = new ArrayList<>();
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            ResultSet rs = sm.executeQuery("select * from RECORDS where NAME = \"" + name + "\";");
            while (rs.next()) {
                list.add(rs.getLong("code"));
            }

            sm.close();
            c.commit();
            c.close();

            if (list.size() == 1)
                return list.get(0);
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return 0;
    }


    /*
     static boolean insertList(int code) {
         Connection c;
         Statement sm;
         try {
             c = createDatabaseConnection();
             assert c != null;
             sm = c.createStatement();

             String cmd = "insert into RECORDS" +
                     " values (\"" + code + "\")";
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

     static boolean updateList(String old_code, String new_code) {
         Connection c;
         Statement sm;
         try {
             c = createDatabaseConnection();
             assert c != null;
             sm = c.createStatement();

             String cmd = "update RECORDS" +
                     " set NAME = \"" + new_code + "\"" +
                     " where NAME = \"" + old_code + "\"";
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

     static boolean removeList(String name) {
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

    */

}
