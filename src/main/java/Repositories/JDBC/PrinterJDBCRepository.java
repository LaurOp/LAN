package Repositories.JDBC;

import Entities.DTOs.PrinterDTO;
import Entities.Models.Hardware.Printer;
import Services.JDBC.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrinterJDBCRepository {
    private ConnectionManager connectionManager;

    public PrinterJDBCRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    //C
    public void createNewPrinter(String ip, String br, String mo, int pag) throws SQLException {
        String sql = "INSERT INTO printers (ip, brand, model, pagesPerMinute) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, ip);
        statement.setString(2, br);
        statement.setString(3, mo);
        statement.setString(4, Integer.toString(pag));
        statement.executeUpdate();
    }

    //R
    public List<PrinterDTO> findAllPrinters() throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from printers");
        ResultSet rs = stmt.executeQuery();
        List<PrinterDTO> result = new ArrayList<>();
        while (rs.next()) {
            PrinterDTO p = new PrinterDTO();
            p.setBrand(rs.getString("ip"));
            p.setBrand(rs.getString("brand"));
            p.setModel(rs.getString("model"));
            p.setPagesPerMinute(rs.getInt("pagesPerMinute"));

            result.add(p);
        }
        return result;
    }


    //U
    public void updatePrinter(PrinterDTO printer) throws SQLException {
        String sql = "UPDATE printers SET brand=?, model=?, pagesPerMinute=? WHERE ip=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, printer.getBrand());
        statement.setString(2, printer.getModel());
        statement.setString(3, Integer.toString(printer.getPagesPerMinute()));
        statement.setString(4, printer.getIP());

        statement.executeUpdate();
    }

    //D
    public void deletePrinter(PrinterDTO printer) throws SQLException{
        String sql = "DELETE FROM printers WHERE ip=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, printer.getIP());

        statement.executeUpdate();
    }



}
