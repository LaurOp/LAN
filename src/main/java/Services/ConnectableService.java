package Services;

import Entities.Hardware.Connectable;
import Entities.Hardware.Hardware;
import Entities.Hardware.Printer;
import Entities.Hardware.Switch;
import Repositories.ConnectableRepository;
import Repositories.HardwareRepository;

import java.util.ArrayList;
import java.util.List;

public class ConnectableService {

    private ConnectableRepository connectableRepository = new ConnectableRepository();
    private HardwareRepository hardwareRepository = new HardwareRepository();

    //Auxiliary<Connectable> check = new Auxiliary<>(); not needed anymore sine I implemented isIn in generic repo

    public void createConnectable(boolean isprinter, String brand){
        if (isprinter){
            Printer x = new Printer(brand, "");
            connectableRepository.add(x);
        }
        else {
            Switch x = new Switch(brand, true);
            connectableRepository.add(x);
        }
    }

    public Connectable getConnectable(boolean isprinter, int index){
        if (isprinter){
            return connectableRepository.get(index);
        }
        else {
            return connectableRepository.getSW(index);
        }
    }


    public void connectPCwithPrSw(Hardware hardware, Connectable con){
        hardware.addConnection(con);

        if (con instanceof Printer){
            List<String> x = ((Printer)con).getConnectedTo();
            x.add(hardware.getID());
            ((Printer)con).setConnectedTo(x);

            ArrayList<Connectable> y = hardware.getConnections();
            y.add(con);
            hardware.setConnections(y);
        }
        if (con instanceof Switch){
            var x = ((Switch)con).getConnectedTo();
            x.add(hardware.getID());
            ((Switch)con).setConnectedTo(x);

            var y = hardware.getConnections();
            y.add(con);
            hardware.setConnections(y);
        }

        if (!hardwareRepository.isIn(hardware)){
            hardwareRepository.add(hardware);
        }
        if (!connectableRepository.isIn(con)){
            connectableRepository.add(con);
        }


    }

    public List<Integer> getFreePorts(Connectable con) {
        List<Integer> rez = new ArrayList<>();

        if (!connectableRepository.isIn(con)){
            connectableRepository.add(con);
        }

        if (con instanceof Printer){
            for(int i = 0; i<2-((Printer) con).getConnectedTo().size(); i++){
                rez.add(i);
            }
        }
        if (con instanceof Switch){
            for(int i = 0; i<24-((Switch) con).getConnectedTo().size(); i++){
                rez.add(i);
            }
        }


        return rez;
    }


    public boolean checkIfOnline(Connectable con) {
        if (!connectableRepository.isIn(con)){
            connectableRepository.add(con);
        }
        if (con instanceof Printer){
            if (((Printer) con).getIP().equals("")){return false;}

            if (((Printer) con).getConnectedTo().size() == 0){
                return false;
            }
            return true;
        }
        if (con instanceof Switch){
            if (((Switch) con).getIP().equals("")){return false;}

            if (((Switch) con).getConnectedTo().size() == 0){
                return false;
            }
            return true;
        }

        return false;
    }


    public List<Connectable> getAllOnline(){
        var x = connectableRepository.getBothSizes();
        List<Connectable> rez = new ArrayList<>();

        for(int i = 0; i<x.get("printers"); i++){
            if (checkIfOnline(connectableRepository.get(i)))
                rez.add(connectableRepository.get(i));
        }
        for(int i = 0; i<x.get("switches"); i++){
            if (checkIfOnline(connectableRepository.getSW(i)))
                rez.add(connectableRepository.getSW(i));
        }

        return rez;
    }



    public void disconnectPort(Connectable con, int port) {
        if (!connectableRepository.isIn(con)){
            connectableRepository.add(con);
        }
        if (con instanceof Printer){
            var x = ((Printer) con).getConnectedTo();
            x.remove(port);
            ((Printer) con).setConnectedTo(x);
        }
        if (con instanceof Switch){
            var x = ((Switch) con).getConnectedTo();
            x.remove(port);
            ((Switch) con).setConnectedTo(x);
        }
    }

    public void removeAllWithoutConnections(){
        var x = connectableRepository.getBothSizes();

        for(int i = 0; i<x.get("printers"); i++){
            if (getFreePorts(connectableRepository.get(i)).size() == 2)
                connectableRepository.delete(connectableRepository.get(i));
        }
        for(int i = 0; i<x.get("switches"); i++){
            if (getFreePorts(connectableRepository.getSW(i)).size() == 2)
                connectableRepository.delete(connectableRepository.getSW(i));
        }

    }


    public Integer ping(Connectable con, String ip) {
        if (!connectableRepository.isIn(con)){
            connectableRepository.add(con);
        }
        if (con instanceof Printer){
            if (((Printer) con).getConnectedTo().size() == 0){
                return -2;
            }

            for(String conn : ((Printer) con).getConnectedTo()){
                if (conn.equals(ip)){
                    return 0;
                }
            }
        }
        if (con instanceof Switch){
            if (((Switch) con).getConnectedTo().size() == 0){
                return -2;
            }

            for(String conn : ((Switch) con).getConnectedTo()){
                if (conn.equals(ip)){
                    return 0;
                }
            }
        }



        return -1;
    }

}


class Auxiliary<T>{
    public boolean notIn(T val, ArrayList<T> lista){

        for (T el : lista){
            if (val == el)
                return true;
        }

        return false;
    }
}