package Services;

import Entities.Hardware.*;
import Repositories.ConnectableRepository;
import Repositories.HardwareRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HardwareService {

    HardwareRepository hardwareRepository = new HardwareRepository();
    ConnectableRepository connectableRepository = new ConnectableRepository();

    public double totalValueOfPC(Hardware hardware){
        double total = 0;

        for (PcComponent comp : hardware.getSet_of_components())
            total += comp.getPrice();

        return total;
    }

    public boolean canRenderImages(Hardware hardware){
        for (PcComponent comp : hardware.getSet_of_components())
            if (comp instanceof GraphicsCard)
                return true;

        return false;
    }

    public void updateNrOfPorts(Hardware hardware){
        int newVal = 0;

        for(PcComponent comp : hardware.getSet_of_components()){
            if(comp instanceof NetworkAdapter){
                newVal += ((NetworkAdapter) comp).getNrOfPorts();
            }
        }

        hardware.setNrOfPorts(newVal);
    }

    public void updateNrOfOccupiedPorts(Hardware hardware){
        hardware.setOccupiedPorts(hardware.getConnections().size());
    }

    public void addConnection(Hardware hardware, Connectable con) throws Exception {
        connectableRepository.add(con);
        var poz = -1;
        for(int i = 0; i< hardwareRepository.getStorage().length; i++){
            if(hardwareRepository.getStorage()[i] == hardware){
                poz = i;
                break;
            }
        }
        //System.out.println(poz);
        if (poz == -1){
            throw new Exception("hardware not found");
        }

        var x = hardwareRepository.getStorage()[poz];
        x.addConnection(con);
        var y = hardwareRepository.getStorage();
        y[poz] = x;
        hardwareRepository.setStorage(y);

    }

    public void addHardware(Hardware hardware){
        if (!hardwareRepository.isIn(hardware))
            hardwareRepository.add(hardware);
    }

    public ArrayList<Pair<Integer, String>> getAllConnections(Hardware hardware) throws Exception {
        ArrayList<Pair<Integer, String>> rez = new ArrayList<>();

        var poz = -1;
        for(int i = 0; i< hardwareRepository.getStorage().length; i++){
            if(hardwareRepository.getStorage()[i] == hardware){
                poz = i;
                break;
            }
        }
        if (poz == -1){
            throw new Exception("hardware not found");
        }
        for (int i = 0; i < hardwareRepository.getStorage()[poz].getConnections().size(); i++){
            var temp = hardwareRepository.getStorage()[poz].getConnections().get(i);
            if (temp != null){
                if( temp instanceof Printer) {
                    String s = "Printer" + ((Printer) temp).getIP();
                    Pair<Integer, String> pair = new Pair<>(i, s);
                    rez.add(pair);
                }
                if( temp instanceof Switch) {
                    String s = "Switch" + ((Switch) temp).getIP();
                    Pair<Integer, String> pair = new Pair<>(i, s);
                    rez.add(pair);
                }
            }
        }
        return rez;
    }

    public Set<Connectable> getAllConnectionsNoHW(){
        Set<Connectable> rez = new HashSet<>();
        for(var x : hardwareRepository.getStorage()){
            if (x!= null)
                for(var el : x.getConnections()){
                    if (el != null)
                        rez.add(el);
                }
        }
        return rez;
    }

    public void removeConnection(Hardware hardware, int index) throws Exception {
        var poz = -1;
        for(int i = 0; i< hardwareRepository.getStorage().length; i++){
            if(hardwareRepository.getStorage()[i] == hardware){
                poz = i;
                break;
            }
        }
        if (poz == -1){
            throw new Exception("hardware not found");
        }

        var x = hardwareRepository.getStorage()[poz].getConnections();
        x.remove(index);
        var y = hardwareRepository.getStorage();
        y[poz].setConnections(x);
        hardwareRepository.setStorage(y);
    }
}

