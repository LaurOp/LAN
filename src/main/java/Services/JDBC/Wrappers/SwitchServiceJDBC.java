package Services.JDBC.Wrappers;

import Entities.DTOs.SwitchDTO;
import Entities.Models.Hardware.Printer;
import Entities.Models.Hardware.Switch;
import Repositories.JDBC.SwitchJDBCRepository;
import Services.JDBC.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SwitchServiceJDBC {
    private final SwitchJDBCRepository switchJDBCRepository;

    public SwitchServiceJDBC(ConnectionManager connectionManager) {
        switchJDBCRepository = new SwitchJDBCRepository(connectionManager);
    }

    // C
    public void addSwitch(Switch sw) throws SQLException {
        switchJDBCRepository.createNewSwitch(sw.getIP(), sw.getBrand(), sw.isGigabitEthernet());
    }

    // R
    public List<Switch> getAllSwitches() throws SQLException {
        var res = switchJDBCRepository.findAllSwitches();
        var result = new ArrayList<Switch>();
        for(var s : res){
            Switch sw = new Switch();
            sw.setIP(s.getIP());
            sw.setBrand(s.getBrand());
            sw.setGigabitEthernet(s.isGigabitEthernet());

            result.add(sw);
        }

        return result;
    }

    public Switch getSwitchByIP(String IP) throws SQLException {
        var s = switchJDBCRepository.getSwitchByIP(IP);

        Switch sw = new Switch();
        sw.setIP(s.getIP());
        sw.setBrand(s.getBrand());
        sw.setGigabitEthernet(s.isGigabitEthernet());

        return sw;
    }

    // U
    public void updateSwitch(Switch toBeChanged, Switch newSwitch) throws SQLException {
        SwitchDTO toBeChanged2 = new SwitchDTO();
        SwitchDTO newSwitch2 = new SwitchDTO();

        toBeChanged2.setIP(toBeChanged.getIP());
        toBeChanged2.setBrand(toBeChanged.getBrand());
        toBeChanged2.setGigabitEthernet(toBeChanged.isGigabitEthernet());

        newSwitch2.setIP(newSwitch.getIP());
        newSwitch2.setBrand(newSwitch.getBrand());
        newSwitch2.setGigabitEthernet(newSwitch.isGigabitEthernet());

        switchJDBCRepository.updateSwitch(toBeChanged2, newSwitch2);
    }

    // D
    public void deleteSwitch(Switch s) throws SQLException {
        SwitchDTO sw = new SwitchDTO();
        sw.setIP(s.getIP());
        sw.setBrand(s.getBrand());
        sw.setGigabitEthernet(s.isGigabitEthernet());

        switchJDBCRepository.deleteSwitch(sw);
    }

}
