package Services.JDBC.Wrappers;

import Entities.DTOs.NAdapterDTO;
import Entities.Models.Hardware.GraphicsCard;
import Entities.Models.Hardware.NetworkAdapter;
import Repositories.JDBC.NAdapterJDBCRepository;
import Services.JDBC.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetworkAdapterServiceJDBC {
    private final NAdapterJDBCRepository nAdapterJDBCRepository;

    public NetworkAdapterServiceJDBC(ConnectionManager connectionManager){
        nAdapterJDBCRepository = new NAdapterJDBCRepository(connectionManager);
    }

    // C
    public void addNetworkAdapter(NetworkAdapter networkAdapter) throws SQLException {
        nAdapterJDBCRepository.createNewAdapter(networkAdapter.getNrOfPorts(), (int)networkAdapter.getPrice());
    }

    // R
    public List<NetworkAdapter> getAllNAdapters() throws SQLException {
        var res = nAdapterJDBCRepository.findAllAdapters();
        var result = new ArrayList<NetworkAdapter>();
        for(var n : res){
            NetworkAdapter na = new NetworkAdapter();
            na.setNrOfPorts(n.getNrOfPorts());
            na.setPrice(n.getPrice());

            result.add(na);
        }

        return result;
    }

    // U
    public void updatePrice(NetworkAdapter toChange, int price) throws SQLException {
        NAdapterDTO nAdapterDTO = new NAdapterDTO();
        nAdapterDTO.setPrice((int)toChange.getPrice());
        nAdapterDTO.setNrOfPorts(toChange.getNrOfPorts());

        nAdapterJDBCRepository.updateAdaptersPrice(nAdapterDTO, price);
    }

    public void updatePorts(NetworkAdapter toChange, int ports) throws SQLException {
        NAdapterDTO nAdapterDTO = new NAdapterDTO();
        nAdapterDTO.setPrice((int)toChange.getPrice());
        nAdapterDTO.setNrOfPorts(toChange.getNrOfPorts());

        nAdapterJDBCRepository.updateAdaptersPorts(nAdapterDTO, ports);
    }

    // D
    public void deleteByPrice(NetworkAdapter networkAdapter) throws SQLException {
        nAdapterJDBCRepository.deleteAdaptersByPrice(new NAdapterDTO(0, (int)networkAdapter.getPrice()));
    }

    public void deleteByPorts(NetworkAdapter networkAdapter) throws SQLException {
        nAdapterJDBCRepository.deleteAdaptersByPorts(new NAdapterDTO(networkAdapter.getNrOfPorts(), 0));
    }
}
