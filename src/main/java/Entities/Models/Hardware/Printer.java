package Entities.Models.Hardware;

import Services.ConnectableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Printer implements Connectable {

    private final int nrPorts = 2;
    private List<String> connectedTo;
    private final String IP;
    private String brand;
    private String model;
    private int pagesPerMinute;

    private ConnectableService service = new ConnectableService();

    public String getIP() {
        return IP;
    }

    public List<String> getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(List<String> connectedTo) {
        this.connectedTo = connectedTo;
    }

    {
        Random r = new Random();
        this.IP = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);

        this.connectedTo = new ArrayList<>();
    }

    public Printer() {
        this.brand = "HP";
        this.model = "unu";
        this.pagesPerMinute = 10;

    }

    public Printer(String brand, String model) {
        this.brand = brand;
        this.model = model;
        this.pagesPerMinute = 10;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPagesPerMinute(int pagesPerMinute) {
        this.pagesPerMinute = pagesPerMinute;
    }

}
