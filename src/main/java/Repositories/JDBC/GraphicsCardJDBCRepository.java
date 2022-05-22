package Repositories.JDBC;

import Entities.DTOs.GraphicsCardDTO;
import Entities.DTOs.SwitchDTO;
import Services.JDBC.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class GraphicsCardJDBCRepository {
    private final ConnectionManager connectionManager;

    public GraphicsCardJDBCRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    //C
    public void createNewGraphics(int vid, boolean formine, int pr) throws SQLException {
        String sql = "INSERT INTO graphics (name, video, mine, price) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connectionManager.prepareStatement(sql);

        statement.setString(1, "Graphics" + vid);
        statement.setString(2, Integer.toString(vid));
        if (formine){
            statement.setString(3, "1");
        }
        else{
            statement.setString(3, "0");
        }
        statement.setString(4, Integer.toString(pr));

        try {
            statement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Already in database!");
        }
    }


    //R
    public List<GraphicsCardDTO> findAllGraphics() throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from graphics");
        ResultSet rs = stmt.executeQuery();
        List<GraphicsCardDTO> result = new ArrayList<>();
        while (rs.next()) {
            GraphicsCardDTO g = new GraphicsCardDTO();
            g.setPrice(rs.getInt("price"));
            g.setForMining(rs.getBoolean("mine"));
            g.setVideoMemory(rs.getInt("video"));
            g.setName("Graphics" + g.getVideoMemory());

            result.add(g);
        }
        return result;
    }

    public List<GraphicsCardDTO> getGraphicsByName(String name) throws SQLException {
        PreparedStatement stmt = connectionManager.prepareStatement("Select * from graphics WHERE name=?");
        stmt.setString(1,name);
        ResultSet rs = stmt.executeQuery();
        List<GraphicsCardDTO> result = new ArrayList<>();
        while (rs.next()) {
            GraphicsCardDTO g = new GraphicsCardDTO();
            g.setPrice(rs.getInt("price"));
            g.setForMining(rs.getBoolean("mine"));
            g.setVideoMemory(rs.getInt("video"));
            g.setName("Graphics" + g.getVideoMemory());

            result.add(g);
        }
        return result;
    }

    //U
    public void updateGraphics(GraphicsCardDTO graphicsToChange, GraphicsCardDTO graphicsCardDTO) throws SQLException {
        String sql = "UPDATE graphics SET video=?, mine=?, price=?, name=? WHERE name=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, Integer.toString(graphicsCardDTO.getVideoMemory()));

        if (graphicsCardDTO.isForMining()){
            statement.setString(2, "1");
        }
        else{
            statement.setString(2, "0");
        }

        statement.setString(3, Integer.toString(graphicsCardDTO.getPrice()));
        statement.setString(4, graphicsCardDTO.getName());

        statement.setString(5, graphicsToChange.getName());


        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Not in the database!");
        }
    }


    //D
    public void deleteGraphics(GraphicsCardDTO graphicsCardDTO) throws SQLException{
        String sql = "DELETE FROM graphics WHERE name=?";

        PreparedStatement statement = connectionManager.prepareStatement(sql);
        statement.setString(1, graphicsCardDTO.getName());

        try {
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Not in the database!");
        }
    }

}
