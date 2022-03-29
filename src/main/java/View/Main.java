package View;

import Entities.Computer;
import Entities.Hardware.*;
import Entities.Network;
import Entities.Software.Software;
import Exceptions.BadDataTypeException;
import Repositories.ComputerRepository;
import Repositories.HardwareRepository;
import Repositories.NetworkRepository;
import Services.ComputerService;
import Services.ConnectableService;
import Services.HardwareService;
import Services.HardwareService.*;
import Services.NetworkService;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

import java.util.*;

public class Main {

    private Scanner s = new Scanner(System.in);

    private ConnectableService connectableService = new ConnectableService();
    private NetworkService networkService = new NetworkService();
    private ComputerService computerService = new ComputerService();
    private HardwareService hardwareService = new HardwareService();

    private NetworkRepository networkRepository = new NetworkRepository();
    private ComputerRepository computerRepository = new ComputerRepository();
    private HardwareRepository hardwareRepository = new HardwareRepository();

    private static int[] options;
    private static String[] optionsText;

    static Network currentNet;
    static Computer currentComp;
    static Hardware currentHardw;

    public Main() {
        options = new int[]{1, 2, 3, 4};
        optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
    }

    public static void main(String[] args) {
        Main menu = new Main();
        boolean exitcond = true;
        while(exitcond){
            try{
                menu.printInteractiveMenu();
                int option = menu.getOption();
                menu.pick(optionsText[option-1]);
            }
            catch (Exception e){
                exitcond = false;
            }
        }
    }

    public void printInteractiveMenu(){
        System.out.println("Choose between:");

        for(int i = 0; i<options.length; i++){
            System.out.println(options[i] + ") " + optionsText[i]);
        }

    }

    private int getOption() {
        try {
            int option = Integer.parseInt(s.nextLine());
            if (option >= 1 && option <= options.length) {
                return option;
            }
            else
                throw new BadDataTypeException("erori");
        } catch (BadDataTypeException text) {
            System.out.println("Number is too low or too high, try again");
        }
        return getOption();
    }

    public void pick(String text) throws Exception {

        if (text.equals("Exit")){
            throw new Exception();
        }
        if (text.equals("Add new network")){
            int x;
            try{
                x = networkService.createNetwork();
            }
            catch (Exception e){
                System.out.println("failed to create");
                return;
            }
            currentNet = networkService.getNetwork(x);
            options = new int[]{1,2,3,4,5,6};
            optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

            return;
        }
        if (text.equals("List all networks")){
            networkService.getAllNetworks();
            return;
        }
        if (text.equals("Add computer to network")){
            Computer comp = new Computer();
            currentComp = comp;
            computerService.addComputer(comp);
            networkService.addComputer(currentNet, comp);
            return;
        }
        if (text.equals("List all computers")){
            if (networkService.getComputers(currentNet).size() == 0){
                System.out.println("No computers yet");
                return;
            }

            System.out.println(currentNet.getComputers() + "indexed as ");
            for(var x : networkService.getComputers(currentNet)){
                System.out.println(x);
            }
            return;
        }
        if (text.equals("Remove computer from network")){
            if (networkService.getComputers(currentNet).size() == 0){
                System.out.println("No computers yet");
                return;
            }
            System.out.println("Which one to remove? (0-indexed)");
            int todelete = Integer.parseInt(s.nextLine());

            if (networkService.getComputers(currentNet).size() == 0 || todelete > Collections.max(networkService.getComputers(currentNet))){
                System.out.println("Don't know that computer");
                return;
            }


            try{
                networkService.removeComputer(currentNet, todelete);
            }
            catch (Exception e){
                System.out.println("Not found");
            }

            if (!networkService.getComputers(currentNet).contains(currentComp.getID())){
                currentComp = null;
            }
            return;
        }
        if (text.equals("Back to networks")){
            options = new int[]{1, 2, 3, 4};
            optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
            return;
        }
        if (text.equals("Edit a network")) {
            System.out.println("Which network do you want to edit? (which network id)");
            var x = Integer.parseInt(s.nextLine());

            if (!networkService.getAllNetworks().contains(x)) {
                System.out.println("Don't know that network");

                options = new int[]{1, 2, 3, 4};
                optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
            } else {
                currentNet = networkService.getNthNetwork(x);
                System.out.println(currentNet);

                options = new int[]{1, 2, 3, 4, 5, 6};
                optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

                return;
            }
        }
        if (text.equals("Edit a computer")){
            System.out.println("Which computer do you want to edit? (which computer id)");
            var x = Integer.parseInt(s.nextLine());

            if (!networkService.getComputers(currentNet).contains(x)) {
                System.out.println("Don't know that computer");

                options = new int[]{1, 2, 3, 4, 5, 6};
                optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

            }

            var aux = computerService.getComputerByID(x);
            if (aux == null){
                System.out.println("Don't know that computer");
                return;
            }
            currentComp = aux;

            options = new int[]{1, 2, 3};
            optionsText = new String[]{"Edit its hardware", "Edit its software", "Exit"};


            return;
        }
        if (text.equals("Edit its hardware")){
            currentHardw = currentComp.getHardware();

            hardwareService.addHardware(currentHardw);

            options = new int[]{1, 2, 3};
            optionsText = new String[]{"Edit connections", "Edit its components", "Exit"};

            return;
        }
        if (text.equals("Edit its software")){
            Software softAux = currentComp.getSoftware();

            return;
        }
        if (text.equals("Edit connections")){


            options = new int[]{1, 2, 3, 4, 5};
            optionsText = new String[]{"Add connection", "List connections", "Remove connection", "Back to selected network" , "Exit"};
            return;
        }
        if (text.equals("Edit its components")){
            return;
        }
        if (text.equals("Add connection")){
            try{
                Connectable deAdd = readConnectable();
                hardwareService.addConnection(currentHardw, deAdd);
            }
            catch (Exception e){
                System.out.println("Bad hardware " + e);
            }

            return;
        }
        if (text.equals("List connections")){
            var dinservice = hardwareService.getAllConnections(currentHardw);

            for(var el : dinservice){
                System.out.println(el.getL() + " " + el.getR());
            }

            return;
        }
        if (text.equals("Remove connection")){
            try{
                System.out.println("Which connection do you want to remove? (index)");
                var x = Integer.parseInt(s.nextLine());

                var dinservice = hardwareService.getAllConnections(currentHardw);
                var poz = -1;
                for (int i = 0; i< dinservice.size(); i++){
                    if (dinservice.get(i).getL() == x)
                        poz = i;
                }
                if (poz == -1){
                    throw new Exception("not a valid int");
                }
                hardwareService.removeConnection(currentHardw, poz);
            }
            catch (Exception e){
                System.out.println("Don't know that connection");
            }
            return;
        }
        if (text.equals("Back to selected network")){

            options = new int[]{1, 2, 3, 4, 5, 6};
            optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

            currentHardw = null;
            currentComp = null;

            return;
        }
    }


    private Connectable readConnectable(){
        System.out.println("Printer (0) or Switch (1)? ");
        boolean isprinter = Integer.parseInt(s.nextLine()) == 0;

        if(isprinter){
            String br;
            String mod;
            System.out.println("brand:");
            br = s.nextLine();
            System.out.println("model:");
            mod = s.nextLine();

            Printer rez = new Printer(br, mod);

            return rez;
        }
        else {
            String br;
            boolean hasGigabit;
            System.out.println("brand:");
            br = s.nextLine();
            System.out.println("has gigabitEthernet? (0/1):");
            hasGigabit = Objects.equals(s.nextLine(), "1");

            Switch rez = new Switch(br, hasGigabit);

            return rez;
        }

    }
}
