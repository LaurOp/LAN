package Entities.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Switch implements Connectable {

    private final int nrPorts = 24;
    private final String IP;
    private String brand;
    private boolean gigabitEthernet;
    private List<String> connectedTo;

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
    public List<Integer> getFreePorts() {
        List<Integer> rez = new ArrayList<>();

        for(int i = 0; i<24-this.connectedTo.size(); i++){
            rez.add(i);
        }

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
    public void disconnectPort(int port) {
        this.connectedTo.remove(port);
    }

    @Override
    public Integer ping(String ip) {
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
