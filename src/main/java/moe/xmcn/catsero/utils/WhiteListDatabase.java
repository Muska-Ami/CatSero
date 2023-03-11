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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import moe.xmcn.catsero.CatSero;
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
     *
     * @return 连接
     * @throws SQLException SQL错误
     */
    static Connection createDatabaseConnection() throws SQLException {

        HikariConfig hc = new HikariConfig();

        if (Configuration.SQL.TYPE.equals("sqlite")) {
            // SQLite
            hc.setPoolName("SQLiteConnectionPool");
            hc.setDriverClassName(Configuration.SQL.JDBC.SQLITE_CLASS_NAME);
            hc.setJdbcUrl(
                    "jdbc:sqlite:" +
                            CatSero.INSTANCE.getDataFolder() +
                            "/whitelist.db"
            );
            hc.setAutoCommit(false);
            HikariDataSource ds = new HikariDataSource(hc);
            return ds.getConnection();
        } else if (Configuration.SQL.TYPE.equals("mysql")) {
            // MySQL
            hc.setDriverClassName(Configuration.SQL.JDBC.MYSQL_CLASS_NAME);
            hc.setJdbcUrl(
                    "jdbc:mysql://" +
                            Configuration.SQL.MYSQL.HOST +
                            ":" +
                            Configuration.SQL.MYSQL.PORT +
                            "/" +
                            Configuration.SQL.MYSQL.DATABASE +
                            "?serverTimezone=" +
                            Configuration.SQL.MYSQL.TIMEZONE +
                            "&useUnicode=" +
                            Configuration.SQL.MYSQL.UNICODE +
                            "&characterEncoding=" +
                            Configuration.SQL.MYSQL.ENCODING +
                            "&useSSL=" +
                            Configuration.SQL.MYSQL.SSL
            );
            hc.setUsername(Configuration.SQL.MYSQL.USERNAME);
            hc.setPassword(Configuration.SQL.MYSQL.PASSWORD);
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
     *
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
     *
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
     *
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

    static String getName(long code) {
        Connection c;
        Statement sm;
        List<String> list = new ArrayList<>();
        try {
            c = createDatabaseConnection();
            assert c != null;
            sm = c.createStatement();

            ResultSet rs = sm.executeQuery("select * from RECORDS where CODE = " + code + ";");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }

            sm.close();
            c.commit();
            c.close();

            if (list.size() == 1)
                return list.get(0);
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
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
