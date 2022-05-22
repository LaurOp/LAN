package Services.JDBC.JDBC_IO;

import Services.JDBC.ConnectionManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class JDBCaudit {
    private static JDBCaudit instance = null;
    private static ConnectionManager connectionManager = null;

    private JDBCaudit(ConnectionManager connectionManager){
        JDBCaudit.connectionManager = connectionManager;
    }

    public static JDBCaudit getInstance(ConnectionManager connectionManager2) throws IOException {
        if (instance == null) {
            instance = new JDBCaudit(connectionManager2);
        }
        return instance;
    }

    public static void updateConnection(ConnectionManager connectionManager2){
        JDBCaudit.connectionManager = connectionManager2;
    }

    public void writeToAudit(String actionDone) throws IOException, SQLException {
        String sql = "INSERT INTO audit (action) VALUES (?)";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, actionDone);

        try {
            statement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Table not found!");
        }
    }

    public void clearAudit() throws SQLException {
        String sql = "DELETE FROM audit";
        PreparedStatement statement = connectionManager.prepareStatement(sql);

        try {
            statement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Table not found!");
        }
    }
}
