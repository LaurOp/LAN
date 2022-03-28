package Entities.Hardware;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Hardware {
    private int nrOfPorts;
    private int occupiedPorts;

    ArrayList<Connectable> connections = new ArrayList<Connectable>();
    Set<PcComponent> set_of_components = new HashSet<PcComponent>();

    public double totalValueOfPC(){
        double total = 0;

        for (PcComponent comp : set_of_components)
            total += comp.getPrice();

        return total;
    }

    public boolean canRenderImages(){
        for (PcComponent comp : set_of_components)
            if (comp instanceof GraphicsCard)
                return true;

        return false;
    }

    public void updateNrOfPorts(){
        int newVal = 0;

        for(PcComponent comp : set_of_components){
            if(comp instanceof NetworkAdapter){
                newVal += ((NetworkAdapter) comp).getNrOfPorts();
            }
        }

        this.nrOfPorts = newVal;
    }

    public void updateNrOfOccupiedPorts(){
        occupiedPorts = connections.size();
    }

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


    public void addPcComponent(PcComponent comp){
        set_of_components.add(comp);
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

}
