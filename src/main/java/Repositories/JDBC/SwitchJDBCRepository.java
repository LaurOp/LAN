package Repositories.JDBC;

import Entities.DTOs.PrinterDTO;
import Entities.DTOs.SwitchDTO;
import Entities.Models.Hardware.Switch;
import Services.JDBC.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class SwitchJDBCRepository {
    private final ConnectionManager connectionManager;

    public SwitchJDBCRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    //C
    public void createNewSwitch(String ip, String br, boolean giga) throws SQLException {
        String sql = "INSERT INTO switches (ip, brand, giga) VALUES (?, ?, ?)";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, ip);
        statement.setString(2, br);
        if (giga){
            statement.setString(3, "1");
        }
        else{
            statement.setString(3, "0");
        }

        try {
            statement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Already in database!");
        }
    }

    //R
    public List<SwitchDTO> findAllSwitches() throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from switches");
        ResultSet rs = stmt.executeQuery();
        List<SwitchDTO> result = new ArrayList<>();
        while (rs.next()) {
            SwitchDTO s = new SwitchDTO();
            s.setIP(rs.getString("ip"));
            s.setBrand(rs.getString("brand"));
            s.setGigabitEthernet(rs.getBoolean("giga"));

            result.add(s);
        }
        return result;
    }

    public SwitchDTO getSwitchByIP(String ip) throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from switches WHERE ip=?");
        stmt.setString(1, ip);
        ResultSet rs = stmt.executeQuery();
        try {
            rs.next();
            SwitchDTO s = new SwitchDTO();
            s.setIP(rs.getString("ip"));
            s.setBrand(rs.getString("brand"));
            s.setGigabitEthernet(rs.getBoolean("giga"));
            return s;
        }
        catch (Exception e){
            System.out.println("Not in the database!");
            return new SwitchDTO();
        }
    }

    //U
    public void updateSwitch(SwitchDTO switchToChange, SwitchDTO switchDTO) throws SQLException {
        String sql = "UPDATE switches SET brand=?, giga=? WHERE ip=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, switchDTO.getBrand());
        if (switchDTO.isGigabitEthernet()){
            statement.setString(2, "1");
        }
        else{
            statement.setString(2, "0");
        }
        statement.setString(3, switchToChange.getIP());

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Not in the database!");
        }
    }

    //D
    public void deleteSwitch(SwitchDTO switchDTO) throws SQLException{
        String sql = "DELETE FROM switches WHERE ip=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, switchDTO.getIP());

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Not in the database!");
        }
    }

}
