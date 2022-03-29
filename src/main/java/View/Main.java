package View;

import Entities.Computer;
import Entities.Hardware.GraphicsCard;
import Entities.Hardware.Hardware;
import Entities.Hardware.NetworkAdapter;
import Entities.Network;
import Exceptions.BadDataTypeException;
import Repositories.ComputerRepository;
import Repositories.HardwareRepository;
import Repositories.NetworkRepository;
import Services.ConnectableService;
import Services.NetworkService;
import org.testng.annotations.Test;

import java.util.*;

public class Main {

    private Scanner s = new Scanner(System.in);
    private ConnectableService connectableService = new ConnectableService();
    private NetworkService networkService = new NetworkService();


    private NetworkRepository networkRepository = new NetworkRepository();
    private ComputerRepository computerRepository = new ComputerRepository();
    private HardwareRepository hardwareRepository = new HardwareRepository();

    private static int[] options;
    private static String[] optionsText;

    static Network currentNet;

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
            options = new int[]{1,2,3,4,5};
            optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Back to networks", "Exit"};

            return;
        }
        if (text.equals("List all networks")){
            networkService.getAllNetworks();
            return;
        }
        if (text.equals("Add computer to network")){
            Computer comp = new Computer();
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

            if (todelete > Collections.max(networkService.getComputers(currentNet))){
                System.out.println("Don't know that computer");
                return;
            }


            try{
                networkService.removeComputer(currentNet, todelete);
            }
            catch (Exception e){
                System.out.println("Not found");
            }
            return;
        }
        if (text.equals("Back to networks")){
            options = new int[]{1, 2, 3, 4};
            optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
            return;
        }
        if (text.equals("Edit a network")) {
            System.out.println("Which network do you want to edit? (0-indexed)");
            var x = Integer.parseInt(s.nextLine());

            if (x > Collections.max(networkService.getAllNetworks())) {
                System.out.println("bad input");

                options = new int[]{1, 2, 3, 4};
                optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
            } else {
                currentNet = networkService.getNthNetwork(x);
                System.out.println(currentNet);

                options = new int[]{1, 2, 3, 4, 5};
                optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Back to networks", "Exit"};

                return;
            }
        }
    }

}
