package Services;


import Entities.Models.Hardware.*;
import Repositories.NO_JDBC.ConnectableRepository;
import Repositories.NO_JDBC.HardwareRepository;
import Repositories.NO_JDBC.PcComponentRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HardwareService {

    HardwareRepository hardwareRepository = new HardwareRepository();
    ConnectableRepository connectableRepository = new ConnectableRepository();
    PcComponentRepository pcComponentRepository = new PcComponentRepository();

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

    public void addComponent(Hardware hardware, PcComponent component) throws Exception{
        pcComponentRepository.add(component);
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

        var x = hardwareRepository.getStorage()[poz];
        x.addPcComponent(component);
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

    public ArrayList<Pair<Integer, String>> getAllComponents(Hardware hardware) throws Exception {
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
        var i = 0;
        for (var el : hardwareRepository.getStorage()[poz].getSet_of_components()){
            if (el != null){
                if( el instanceof GraphicsCard) {
                    String s = "GraphicsCard" + ((GraphicsCard) el).getVideoMemory();
                    Pair<Integer, String> pair = new Pair<>(i, s);
                    rez.add(pair);
                }
                if( el instanceof NetworkAdapter) {
                    String s = "NetworkAdapter" + ((NetworkAdapter) el).getNrOfPorts();
                    Pair<Integer, String> pair = new Pair<>(i, s);
                    rez.add(pair);
                }
            }
            i++;
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
    public Set<PcComponent> getAllComponentsNoHW(){
        Set<PcComponent> rez = new HashSet<>();
        for(var x : hardwareRepository.getStorage()){
            if (x!= null)
                for(var el : x.getSet_of_components()){
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

    public void removeComponent(Hardware hardware, int index) throws Exception {
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

        var x = hardwareRepository.getStorage()[poz].getSet_of_components();

        var poz2 = 0;
        for (var el : x){
            if (poz2 == index){
                x.remove(el);
                break;
            }
            poz2++;
        }

        var y = hardwareRepository.getStorage();
        y[poz].setSet_of_components(x);
        hardwareRepository.setStorage(y);
    }
}

