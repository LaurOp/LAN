package Entities.Hardware;

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

    {
        Random r = new Random();
        this.IP = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
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
        List<Integer> rez = new ArrayList<>();
        if (connectedTo.size() <= 1){
            rez.add(1);
        }
        if (connectedTo.size() == 0)
            rez.add(0);

        return rez;
    }

    @Override
    public boolean checkIfOnline() {
        if (this.IP.equals("")){return false;}

        if (this.connectedTo.size() == 0){
            return false;
        }

        return true;
    }

    @Override
    public void disconnectPort(int port) {  //0 indexed
        this.connectedTo.remove(port);
    }

    @Override
    public Integer ping(String ip) {
        // 0 = succes, -1 = not found, -2 = error
        if (this.connectedTo.size() == 0){
            return -2;
        }

        for(String con : connectedTo){
            if (con.equals(ip)){
                return 0;
            }
        }

        return -1;
    }
}
