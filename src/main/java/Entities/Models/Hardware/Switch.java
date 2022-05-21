package Entities.Models.Hardware;

import Services.ConnectableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Switch implements Connectable {

    private final int nrPorts = 24;
    private final String IP;
    private String brand;
    private boolean gigabitEthernet;
    private List<String> connectedTo;

    private ConnectableService service = new ConnectableService();

    public List<String> getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(List<String> connectedTo) {
        this.connectedTo = connectedTo;
    }

    public String getIP() {
        return IP;
    }

    {
        Random r = new Random();
        this.IP = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    public Switch() {
        this.brand = "noname";
        this.gigabitEthernet = false;
        this.connectedTo = new ArrayList<>();
    }

    public Switch(String brand, boolean gigabitEthernet) {
        this.brand = brand;
        this.gigabitEthernet = gigabitEthernet;
        this.connectedTo = new ArrayList<>();
    }

    @Override
    public List<Integer> getFreePorts() {   //hardcoded since I have constant nr of ports
        return service.getFreePorts(this);
    }

    @Override
    public boolean checkIfOnline() {
        return service.checkIfOnline(this);
    }

    @Override
    public void disconnectPort(int port) {  //0 indexed
        service.disconnectPort(this, port);
    }

    @Override
    public Integer ping(String ip) {
        return service.ping(this, ip);
    }

}
