package moe.xmcn.catsero.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class Database {

    public static class UUIDDatabase {
        private static Connection getDatabase() throws SQLException, ClassNotFoundException {
            Connection c;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + Config.plugin.getDataFolder() + "/database.db");
            return c;
        }

        public static void intTable() throws SQLException, ClassNotFoundException {
            Statement cs = getDatabase().createStatement();
            String ct = "CREATE TABLE IF NOT EXISTS UUIDRecd" +
                    "(NAME TEXT     NOT NULL, " +
                    " UUID TEXT     NOT NULL)";
            cs.executeUpdate(ct);
            cs.close();
        }

        public static void insertTable(String player_name, UUID player_uuid) throws SQLException, ClassNotFoundException {
            if (queryName(player_uuid) != null && queryUUID(player_name) != null) {
                Statement cs = getDatabase().createStatement();
                String ct = "INSERT INTO UUIDRecd (NAME,UUID)" +
                        "VALUES ('" + player_name + "', '" + player_uuid + "')";
                cs.executeUpdate(ct);
                cs.close();
            }
        }

        public static String queryName(UUID uuid) {
            String ct = "SELECT * FROM UUIDRecd";
            String name = null;
            try {
                Statement cs = getDatabase().createStatement();
                ResultSet rs = cs.executeQuery(ct);
                while (rs.next()) {
                    name = rs.getString(String.valueOf(uuid));
                }
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error"));
            }
            return name;
        }

        public static UUID queryUUID(String name) {
            String ct = "SELECT * FROM UUIDRecd";
            UUID uuid = null;
            try {
                Statement cs = getDatabase().createStatement();
                ResultSet rs = cs.executeQuery(ct);
                while (rs.next()) {
                    uuid = UUID.fromString(rs.getString(name));
                }
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error"));
            }
            return uuid;
        }
    }

    public static class UUIDRecord implements Listener {
        @EventHandler
        public void record(PlayerJoinEvent pje) {
            String player_name = pje.getPlayer().getName();
            UUID player_uuid = pje.getPlayer().getUniqueId();
            try {
                Database.UUIDDatabase.insertTable(player_name, player_uuid);
            } catch (Exception e) {
                Config.plugin.getLogger().log(Level.WARNING, Config.getMsgByMsID("general.sql-error"));
            }
        }
    }

}
