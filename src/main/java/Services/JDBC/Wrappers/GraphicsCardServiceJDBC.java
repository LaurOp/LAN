package Services.JDBC.Wrappers;

import Entities.DTOs.GraphicsCardDTO;
import Entities.Models.Hardware.GraphicsCard;
import Entities.Models.Hardware.Printer;
import Repositories.JDBC.GraphicsCardJDBCRepository;
import Services.JDBC.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GraphicsCardServiceJDBC {
    private final GraphicsCardJDBCRepository graphicsCardJDBCRepository;

    public GraphicsCardServiceJDBC(ConnectionManager connectionManager) {
        graphicsCardJDBCRepository = new GraphicsCardJDBCRepository(connectionManager);
    }

    // C
    public void addGraphicsCard(GraphicsCard gr) throws SQLException {
        graphicsCardJDBCRepository.createNewGraphics(gr.getName(), gr.getVideoMemory(), gr.isForMining(), (int)gr.getPrice());
    }

    // R
    public List<GraphicsCard> getAllGraphicsCards() throws SQLException {
        var res = graphicsCardJDBCRepository.findAllGraphics();
        return getGraphicsCards(res);
    }

    public List<GraphicsCard> getAllGraphicsCardsByName(String name) throws SQLException {
        var res = graphicsCardJDBCRepository.getGraphicsByName(name);
        return getGraphicsCards(res);
    }

    private List<GraphicsCard> getGraphicsCards(List<GraphicsCardDTO> res) {
        var result = new ArrayList<GraphicsCard>();
        for(var g : res){
            GraphicsCard gr = new GraphicsCard();
            gr.setName(g.getName());
            gr.setVideoMemory(g.getVideoMemory());
            gr.setForMining(g.isForMining());
            gr.setPrice(g.getPrice());

            result.add(gr);
        }

        return result;
    }

    // U
    public void updateGraphicsCard(GraphicsCard toBeChanged, GraphicsCard newGraphics) throws SQLException {
        GraphicsCardDTO toBeChanged2 = new GraphicsCardDTO();
        GraphicsCardDTO newGraphics2 = new GraphicsCardDTO();

        toBeChanged2.setName(toBeChanged.getName());
        toBeChanged2.setVideoMemory(toBeChanged.getVideoMemory());
        toBeChanged2.setForMining(toBeChanged.isForMining());
        toBeChanged2.setPrice((int) toBeChanged.getPrice());

        newGraphics2.setName(newGraphics.getName());
        newGraphics2.setVideoMemory(newGraphics.getVideoMemory());
        newGraphics2.setForMining(newGraphics.isForMining());
        newGraphics2.setPrice((int) newGraphics.getPrice());

        graphicsCardJDBCRepository.updateGraphics(toBeChanged2, newGraphics2);
    }

    // D
    public void deleteGraphicsCard(GraphicsCard g) throws SQLException {
        GraphicsCardDTO gr = new GraphicsCardDTO();
        gr.setName(g.getName());
        gr.setVideoMemory(g.getVideoMemory());
        gr.setForMining(g.isForMining());
        gr.setPrice((int) g.getPrice());

        graphicsCardJDBCRepository.deleteGraphics(gr);
    }
}
