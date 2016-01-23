package reportsystem.manager;

import de.fabidark.reportsystem.sql.SQL;
import de.fabidark.uuidcache.manager.UUIDManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Fabian on 22.01.2016.
 */

public class ReportManager {

    private static ExecutorService executor;

    static {
        executor = Executors.newCachedThreadPool();
    }

    public static boolean AllreadyReported(String UUID, String TargetUUID) {
        try {
            String qry = "SELECT * FROM " + SQL.Cloud + " WHERE PlayerUUID = ? AND TargetUUID = ?";
            PreparedStatement pstmt = SQL.con.prepareStatement(qry);
            pstmt.setString(1, UUID);
            pstmt.setString(2, TargetUUID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.first()) return true;
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
        }
        return false;
    }

    public static void addNewReport(String UUID, String TargetUUID, String reason) {
        String name = UUIDManager.getPlayerName(UUID);
        String Targetname = UUIDManager.getPlayerName(TargetUUID);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String qry = "INSERT INTO " + SQL.Cloud + " (PlayerUUID, PlayerName, TargetUUID, TargetName, Reason, Status, EditorUUID, EditorName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = SQL.con.prepareStatement(qry);
                    pstmt.setString(1, UUID);
                    pstmt.setString(2, name);
                    pstmt.setString(3, TargetUUID);
                    pstmt.setString(4, Targetname);
                    pstmt.setString(5, reason);
                    pstmt.setInt(6, 0);
                    pstmt.setString(7, null);
                    pstmt.setString(8, null);
                } catch (SQLException e) {
                }
            }
        });
    }

}
