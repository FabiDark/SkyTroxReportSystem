package reportsystem.sql;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Fabian on 22.01.2016.
 */

public class SQL {

    public static String host;
    public static String password;
    public static String username;
    public static String port;
    public static String database;
    public static Connection con;

    public static String Cloud = "ReportSystem";

    private static ExecutorService executor;

    static {
        executor = Executors.newCachedThreadPool();
    }

    // Connect to MySQL
    public static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            if(isConnected()) {
                setup();
            }
        } catch (SQLException e) {
        }
    }

    // Disconnect from MySQL
    public static void disconnect() {
        try {
            if(isConnected()) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
        }
    }

    // Test connection
    public static boolean isConnected() {
        try {
            return con !=null && con.isValid(1);
        } catch (SQLException e) {
        }
        return false;
    }

    // Setup Tabels
    private static void setup() {
        if(isConnected()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        {
                            String qry = "CREATE TABLE IF NOT EXISTS " + SQL.Cloud + " (PlayerUUID TEXT, Playername TEXT, TargetUUID TEXT, TargetName TEXT, Reason TEXT, Status TEXT, EditorUUID TEXT, EditorName TEXT, id INT auto_increment, PRIMARY KEY(id))";
                            PreparedStatement stmt;
                            stmt = con.prepareStatement(qry);
                            stmt.executeUpdate();
                            stmt.close();
                        }
                    } catch (SQLException e) {
                    }
                }
            });
        }
    }

    // MySQL result
    public static ResultSet getResult(String query) {
        if(isConnected()) {
            try {
                return con.prepareStatement(query).executeQuery();
            } catch (SQLException e) {
            }
        }
        return null;
    }

}
