package Services;

import Entities.Computer;
import Entities.Network;
import Repositories.ComputerRepository;
import Repositories.NetworkRepository;

import java.util.ArrayList;

public class NetworkService {

    private NetworkRepository networkRepository = new NetworkRepository();
    private ComputerRepository computerRepository = new ComputerRepository();


    public int createNetwork() throws Exception {
        Network x = new Network();
        networkRepository.add(x);
        return networkRepository.getIndex(x);
    }

    public Network getNetwork(int id){
        return networkRepository.get(id);
    }

    public void addComputer(Network net, Computer comp){
        if (!networkRepository.isIn(net)){
            System.out.println("Error adding computer");
            return;
        }

        var poz = 0;
        for(int i = 0; i< networkRepository.getSize(); i++){
            if (networkRepository.get(i) == net) {
                poz = i;
                break;
            }
        }
        var x = networkRepository.get(poz).getComputers();
        x.add(comp);
        networkRepository.get(poz).setComputers(x);
    }
    public void removeComputer(Network net, int id) throws Exception {
        if (!networkRepository.isIn(net)){
            return;
        }
        var poz = 0;
        for(int i = 0; i< networkRepository.getSize(); i++){
            if (networkRepository.get(i) == net) {
                poz = i;
                break;
            }
        }
        var poz2 = -1;
        var x = networkRepository.get(poz).getComputers();
        for(int i = 0; i< x.size(); i++){
            if (x.get(i).getID() == id) {
                poz2 = i;
                break;
            }
        }
        if (poz2 < 0){
            throw new Exception();
        }
        x.remove(poz2);
        networkRepository.get(poz).setComputers(x);
    }

    public ArrayList<Integer> getComputers(Network net){
        if (!networkRepository.isIn(net)){
            networkRepository.add(net);
        }
        ArrayList<Integer> rez = new ArrayList<>();

        for(int i = 0; i< networkRepository.getSize(); i++){
            if (networkRepository.get(i) == net) {
                for(var x : networkRepository.get(i).getComputers()){
                    rez.add(x.getID());
                }
                break;
            }
        }

        return rez;
    }

    public ArrayList<Integer> getAllNetworks(){
        ArrayList<Integer> rez = new ArrayList<>();
        var i = 0;
        var c = 0;
        for(var x : networkRepository.getServer()){
            if (x != null) {
                System.out.println("Network " + i);
                rez.add(i);
                c = 1;
            }
            i++;
        }
        if (c == 0){
            System.out.println(
                    "No networks yet"
            );
        }
        return rez;
    }

    public Network getNthNetwork(int n){
        if (n < 0 || n >= networkRepository.getSize()){
            return null;
        }
        return networkRepository.get(n);
    }
}
