package Repositories.JDBC;

import Entities.DTOs.NAdapterDTO;
import Entities.DTOs.SwitchDTO;
import Services.JDBC.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class NAdapterJDBCRepository {
    private final ConnectionManager connectionManager;

    public NAdapterJDBCRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    //C
    public void createNewAdapter(int ports, int price) throws SQLException {
        String sql = "INSERT INTO nadapters (ports, price) VALUES (?, ?)";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(ports));
        statement.setString(2, Integer.toString(price));

        try {
            statement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Already in database!");
        }
    }

    //R
    public List<NAdapterDTO> findAllAdapters() throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from nadapters");
        ResultSet rs = stmt.executeQuery();
        List<NAdapterDTO> result = new ArrayList<>();
        while (rs.next()) {
            NAdapterDTO n = new NAdapterDTO();
            n.setNrOfPorts(rs.getInt("ports"));
            n.setPrice(rs.getInt("price"));

            result.add(n);
        }
        return result;
    }

    //U
    public void updateAdaptersPrice(NAdapterDTO nAdapterToChange, int price) throws SQLException {
        String sql = "UPDATE nadapters SET price=? WHERE ports=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(price));

        statement.setString(2, Integer.toString(nAdapterToChange.getNrOfPorts()));

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("None in the database!");
        }
    }

    public void updateAdaptersPorts(NAdapterDTO nAdapterToChange, int ports) throws SQLException {
        String sql = "UPDATE nadapters SET ports=? WHERE price=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(ports));
        statement.setString(2, Integer.toString(nAdapterToChange.getPrice()));


        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("None in the database!");
        }
    }

    //D
    public void deleteAdaptersByPrice(NAdapterDTO nAdapterDTO) throws SQLException{
        String sql = "DELETE FROM nadapters WHERE price=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(nAdapterDTO.getPrice()));

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("None in the database!");
        }
    }

    public void deleteAdaptersByPorts(NAdapterDTO nAdapterDTO) throws SQLException{
        String sql = "DELETE FROM nadapters WHERE ports=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(nAdapterDTO.getNrOfPorts()));

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("None in the database!");
        }
    }


}
