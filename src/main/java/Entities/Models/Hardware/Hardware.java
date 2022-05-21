package Entities.Models.Hardware;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Hardware {
    private int nrOfPorts;
    private int occupiedPorts;
    private String ID;

    static int counter;
    static{
        counter = 0;
    }

    {
        byte[] array = new byte[5];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        this.ID = counter + generatedString;
    }

    ArrayList<Connectable> connections = new ArrayList<Connectable>();
    Set<PcComponent> set_of_components = new HashSet<PcComponent>();

    public Hardware() {
        nrOfPorts = 2;
        occupiedPorts = 0;
    }

    public Hardware(ArrayList<Connectable> connections, Set<PcComponent> set_of_components) {
        this.connections = connections;
        this.set_of_components = set_of_components;
        nrOfPorts = 2;
        occupiedPorts = 0;
    }

    public Hardware(int nrOfPorts, int occupiedPorts) {
        this.nrOfPorts = nrOfPorts;
        this.occupiedPorts = occupiedPorts;
    }

    public int getOccupiedPorts() {
        return occupiedPorts;
    }

    public void setOccupiedPorts(int occupiedPorts) {
        this.occupiedPorts = occupiedPorts;
    }

    public void addPcComponent(PcComponent comp){
        set_of_components.add(comp);
    }

    public int getNrOfPorts() {
        return nrOfPorts;
    }

    public void setNrOfPorts(int nrOfPorts) {
        this.nrOfPorts = nrOfPorts;
    }

    public void addConnection(Connectable conn){
        connections.add(conn);
    }

    public Connectable getNthConnection(int n){
        return connections.get(n);
    }

    public ArrayList<Connectable> getConnections() {
        return (ArrayList<Connectable>) connections;
    }

    public void setConnections(ArrayList<Connectable> connections) {
        this.connections = connections;
    }

    public Set<PcComponent> getSet_of_components() {
        return (Set<PcComponent>) set_of_components;
    }

    public void setSet_of_components(Set<PcComponent> set_of_components) {
        this.set_of_components = set_of_components;
    }

    public String getID() {
        return ID;
    }

}
