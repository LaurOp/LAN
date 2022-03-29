package Services;

import Entities.Computer;
import Entities.Hardware.Hardware;
import Entities.Software.Software;
import Repositories.ComputerRepository;

import java.util.ArrayList;

public class ComputerService {
    private ComputerRepository computerRepository = new ComputerRepository();

    private HardwareService hardwareService = new HardwareService();


    public void addComputer(Computer comp){
        if (!computerRepository.isIn(comp))
            computerRepository.add(comp);
    }

    public ArrayList<Integer> getAllComputers(){
        ArrayList<Integer> rez = new ArrayList<>();

        for (var x : computerRepository.getComputers()){
            rez.add(x.getID());
        }

        return rez;
    }

    public Computer getComputerByID(int id){
        for(var el : computerRepository.getComputers()){
            if (el.getID() == id)
                return el;
        }
        return null;
    }

    public void editHardware(Computer comp, Hardware hardware){
        comp.setHardware(hardware);
    }

    public void editSoftware(Computer comp, Software software){
        comp.setSoftware(software);
    }

    public double totalValueOfPC(Computer comp){
        return hardwareService.totalValueOfPC(comp.getHardware());
    }
}

