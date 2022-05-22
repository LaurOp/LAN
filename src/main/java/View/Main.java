package View;

import Entities.DTOs.GraphicsCardDTO;
import Entities.DTOs.PrinterDTO;
import Entities.DTOs.SwitchDTO;
import Entities.Models.Computer;
import Entities.Models.Hardware.*;
import Entities.Models.Network;
import Entities.Models.Software.Software;
import Exceptions.BadDataTypeException;
import Repositories.JDBC.GraphicsCardJDBCRepository;
import Repositories.JDBC.PrinterJDBCRepository;
import Repositories.JDBC.SwitchJDBCRepository;
import Repositories.NO_JDBC.ComputerRepository;
import Repositories.NO_JDBC.HardwareRepository;
import Repositories.NO_JDBC.NetworkRepository;
import Services.ComputerService;
import Services.ConnectableService;
import Services.HardwareService;
import Services.IO.Reader;
import Services.IO.Writer;
import Services.JDBC.ConnectionManager;
import Services.JDBC.JDBC_IO.JDBCaudit;
import Services.NetworkService;
import org.testng.annotations.AfterMethod;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;



public class Main {

    private Scanner s = new Scanner(System.in);

    private final ConnectableService connectableService = new ConnectableService();
    private final NetworkService networkService = new NetworkService();
    private final ComputerService computerService = new ComputerService();
    private final HardwareService hardwareService = new HardwareService();

    // NO JDBC
    private final NetworkRepository networkRepository = new NetworkRepository();
    private final ComputerRepository computerRepository = new ComputerRepository();
    private final HardwareRepository hardwareRepository = new HardwareRepository();

    //JDBC
    static ConnectionManager man;
    private final PrinterJDBCRepository printerJDBCRepository = new PrinterJDBCRepository(man);
    private final SwitchJDBCRepository switchJDBCRepository = new SwitchJDBCRepository(man);

    static {
        try {
            man = new ConnectionManager("jdbc:mysql://localhost:3306/pao","root","laur");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void closeConn() throws SQLException {
        man.close();
    }

    private static int[] options;
    private static String[] optionsText;

    static Network currentNet;
    static Computer currentComp;
    static Hardware currentHardw;

    static Writer audit;
    static JDBCaudit jdbcAudit;

    static {
        try {
            audit = Writer.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            jdbcAudit = JDBCaudit.getInstance(man);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main() throws IOException, SQLException, ClassNotFoundException {
        options = new int[]{1, 2, 3, 4};
        optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};

        Reader reader = Reader.getInstance();
        reader.readPrinters("src/main/resources/Seeders/printerSeed.txt");
        reader.readSwitches("src/main/resources/Seeders/switchSeed.txt");
        reader.readGraphicsCards("src/main/resources/Seeders/graphicscardSeed.txt");
        reader.readNetworkAdapters("src/main/resources/Seeders/networkadapterSeed.txt");
    }

    // PROBA PT JDBC
//    public static void main(String[] args) throws SQLException {
//// Printer:
////        PrinterJDBCRepository printerJDBCRepository = new PrinterJDBCRepository(man);
////        printerJDBCRepository.createNewPrinter("1.1.1.1", "brand1", "model1", 24);
////        var rez = printerJDBCRepository.getPrinterByIP("1.1.1.1");
////        System.out.println(rez);
////        printerJDBCRepository.updatePrinter(rez, new PrinterDTO("2.2.2.2", "brand2", "model2", 26));
////
////        rez = printerJDBCRepository.getPrinterByIP("1.1.1.1");
////        System.out.println(rez);
////
////        printerJDBCRepository.deletePrinter(rez);
////
////        var listrez = printerJDBCRepository.findAllPrinters();
//
//// Switch:
////        SwitchJDBCRepository switchJDBCRepository = new SwitchJDBCRepository(man);
////        switchJDBCRepository.createNewSwitch("1.1.1.1","brand1", true);
////
////        var rez = switchJDBCRepository.findAllSwitches();
////        for(var s : rez){
////            System.out.println(s);
////        }
////        var rez2 = switchJDBCRepository.getSwitchByIP("1.1.1.1");
////        System.out.println(rez2);
////
////        switchJDBCRepository.updateSwitch(rez2, new SwitchDTO("2.2.2.2","brand2",true));
////        rez2 = switchJDBCRepository.getSwitchByIP("21.1.1.1");
////        switchJDBCRepository.deleteSwitch(rez2);
//
//
////GraphicsCard:
////        GraphicsCardJDBCRepository graphicsCardJDBCRepository = new GraphicsCardJDBCRepository(man);
////        graphicsCardJDBCRepository.createNewGraphics(256, false, 2000);
////
////        var rez = graphicsCardJDBCRepository.findAllGraphics();
////        for (var g : rez){
////            System.out.println(g);
////        }
////
////        var rez2 = graphicsCardJDBCRepository.getGraphicsByName("Graphics256");
////
////        graphicsCardJDBCRepository.updateGraphics(rez2.get(0), new GraphicsCardDTO(500, true, 2999));
////        rez2 = graphicsCardJDBCRepository.getGraphicsByName("Graphics500");
////        graphicsCardJDBCRepository.deleteGraphics(rez2.get(0));
//
//    }

//    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        Main menu = new Main();
//        boolean exitcond = true;
//        audit.writeToAudit("Opening app");
//        jdbcAudit.writeToAudit("Opening app");
//        while(exitcond){
//            try{
//                menu.printInteractiveMenu();
//                int option = menu.getOption();
//                menu.pick(optionsText[option-1]);
//            }
//            catch (Exception e){
//                System.out.println(e);
//                exitcond = false;
//            }
//        }
//        audit.writeToAudit("Closing app");
//        audit.closeStream();
//        jdbcAudit.writeToAudit("Closing app");
//    }

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
            throw new Exception("\n See you again!");
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
            audit.writeToAudit("Added new network");
            jdbcAudit.writeToAudit("Added new network");
            return;
        }
        if (text.equals("List all networks")){
            networkService.getAllNetworks();
            audit.writeToAudit("Listed all networks");
            jdbcAudit.writeToAudit("Listed all networks");
            return;
        }
        if (text.equals("Add computer to network")){
            Computer comp = new Computer();
            currentComp = comp;
            computerService.addComputer(comp);
            networkService.addComputer(currentNet, comp);
            audit.writeToAudit("Added a new computer to a network");
            jdbcAudit.writeToAudit("Added a new computer to a network");
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

            audit.writeToAudit("Listed all network computers");
            jdbcAudit.writeToAudit("Listed all network computers");
            return;
        }
        if (text.equals("Remove computer from network")){
            if (networkService.getComputers(currentNet).size() == 0){
                System.out.println("No computers yet");
                audit.writeToAudit("Attempted to remove computer from network");
                jdbcAudit.writeToAudit("Attempted to remove computer from network");
                return;
            }
            System.out.println("Which one to remove? (0-indexed)");
            int todelete = Integer.parseInt(s.nextLine());

            if (networkService.getComputers(currentNet).size() == 0 || todelete > Collections.max(networkService.getComputers(currentNet))){
                System.out.println("Don't know that computer");
                audit.writeToAudit("Attempted to remove computer from network");
                jdbcAudit.writeToAudit("Attempted to remove computer from network");
                return;
            }


            try{
                networkService.removeComputer(currentNet, todelete);
            }
            catch (Exception e){
                System.out.println("Not found");
                audit.writeToAudit("Attempted to remove computer from network");
                jdbcAudit.writeToAudit("Attempted to remove computer from network");
            }

            if (!networkService.getComputers(currentNet).contains(currentComp.getID())){
                currentComp = null;
                audit.writeToAudit("Removed computer from network");
                jdbcAudit.writeToAudit("Removed computer from network");
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
                audit.writeToAudit("Attempted to edit a network");
                jdbcAudit.writeToAudit("Attempted to edit a network");
                options = new int[]{1, 2, 3, 4};
                optionsText = new String[]{"Add new network", "List all networks", "Edit a network", "Exit"};
            } else {
                currentNet = networkService.getNthNetwork(x);
                System.out.println(currentNet);

                options = new int[]{1, 2, 3, 4, 5, 6};
                optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

                audit.writeToAudit("Edited a network");
                jdbcAudit.writeToAudit("Edited a network");
                return;
            }
        }
        if (text.equals("Edit a computer")){
            System.out.println("Which computer do you want to edit? (which computer id)");
            var x = Integer.parseInt(s.nextLine());

            if (!networkService.getComputers(currentNet).contains(x)) {
                System.out.println("Don't know that computer");

                audit.writeToAudit("Attempted to edit a computer");
                jdbcAudit.writeToAudit("Attempted to edit a computer");
                options = new int[]{1, 2, 3, 4, 5, 6};
                optionsText = new String[]{"Add computer to network", "List all computers", "Remove computer from network", "Edit a computer", "Back to networks", "Exit"};

            }

            var aux = computerService.getComputerByID(x);
            if (aux == null){
                System.out.println("Don't know that computer");
                audit.writeToAudit("Attempted to edit a computer");
                jdbcAudit.writeToAudit("Attempted to edit a computer");
                return;
            }
            else{
                audit.writeToAudit("Editing a computer");
                jdbcAudit.writeToAudit("Editing a computer");
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
            audit.writeToAudit("Chose hardware");
            jdbcAudit.writeToAudit("Chose hardware");
            return;
        }
        if (text.equals("Edit its software")){
            Software softAux = currentComp.getSoftware();


            audit.writeToAudit("Chose software");
            jdbcAudit.writeToAudit("Chose software");
            return;
        }
        if (text.equals("Edit connections")){


            options = new int[]{1, 2, 3, 4, 5, 6};
            optionsText = new String[]{"Add connection", "Connect to an existing connection", "List its connections", "Remove connection", "Back to selected network" , "Exit"};
            audit.writeToAudit("Chose connections");
            jdbcAudit.writeToAudit("Chose connections");
            return;
        }
        if (text.equals("Edit its components")){
            options = new int[]{1, 2, 3, 4, 5, 6};
            optionsText = new String[]{"Add component", "Connect to an existing component", "List its components", "Remove component", "Back to selected network" , "Exit"};

            audit.writeToAudit("Chose components");
            jdbcAudit.writeToAudit("Chose components");
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
            audit.writeToAudit("Added a new connectable");
            jdbcAudit.writeToAudit("Added a new connectable");
            return;
        }if (text.equals("Add component")){
            try{
                PcComponent deAdd = readComponent();
                hardwareService.addComponent(currentHardw, deAdd);
            }
            catch (Exception e){
                System.out.println("Bad hardware " + e);
            }
            audit.writeToAudit("Added a new connectable");
            jdbcAudit.writeToAudit("Added a new connectable");
            return;
        }if (text.equals("Connect to an existing connection")){
            var dinservice = hardwareService.getAllConnectionsNoHW();

            ArrayList<Printer> printers = new ArrayList<>();
            ArrayList<Switch> switches = new ArrayList<>();

            for(var el : dinservice){
                if(el instanceof  Printer){
                    printers.add((Printer) el);
                }
                if(el instanceof Switch){
                    switches.add((Switch) el);
                }
            }

            var checkLen = printers.size() + switches.size();
            if (checkLen == 0){
                System.out.println("No connectables added yet");
                audit.writeToAudit("Attempted to connect to an existing connectable");
                jdbcAudit.writeToAudit("Attempted to connect to an existing connectable");
                return;
            }

            for(int i = 0; i<printers.size(); i++){
                System.out.println("Printer " + printers.get(i).getIP() + ", id " + i);
            }
            for(int i = 0; i<switches.size(); i++){
                System.out.println("Switch " + switches.get(i).getIP() + ", id " + i);
            }

            System.out.println("Want to select a printer? (0/1)");
            int isprinter = Integer.parseInt(s.nextLine());

            if (isprinter != 0){
                System.out.println("Which printer? (id mentioned in 'list all')");
                int poz = Integer.parseInt(s.nextLine());
                if(poz >= printers.size() || poz < 0){
                    System.out.println("Don't know that printer");
                    audit.writeToAudit("Attempted to connect to an existing connectable");
                    jdbcAudit.writeToAudit("Attempted to connect to an existing connectable");
                    return;
                }
                var deAdd = printers.get(poz);
                audit.writeToAudit("Connected to an existing connectable");
                jdbcAudit.writeToAudit("Connected to an existing connectable");
                hardwareService.addConnection(currentHardw, deAdd);
            }
            else {
                System.out.println("Which switch? (id mentioned in 'list all')");
                int poz = Integer.parseInt(s.nextLine());
                if(poz >= switches.size() || poz < 0){
                    System.out.println("Don't know that switch");
                    audit.writeToAudit("Attempted to connect to an existing connectable");
                    jdbcAudit.writeToAudit("Attempted to connect to an existing connectable");
                    return;
                }
                var deAdd = switches.get(poz);
                audit.writeToAudit("Connected to an existing connectable");
                jdbcAudit.writeToAudit("Connected to an existing connectable");
                hardwareService.addConnection(currentHardw, deAdd);
            }

            return;
        }
        if (text.equals("Connect to an existing component")){
            var dinservice = hardwareService.getAllComponentsNoHW();

            ArrayList<GraphicsCard> graphicsCards = new ArrayList<>();
            ArrayList<NetworkAdapter> networkAdapters = new ArrayList<>();

            for(var el : dinservice){
                if(el instanceof  GraphicsCard){
                    graphicsCards.add((GraphicsCard) el);
                }
                if(el instanceof NetworkAdapter){
                    networkAdapters.add((NetworkAdapter) el);
                }
            }

            var checkLen = graphicsCards.size() + networkAdapters.size();
            if (checkLen == 0){
                System.out.println("No components added yet");
                audit.writeToAudit("Attempted to add an existing component");
                jdbcAudit.writeToAudit("Attempted to add an existing component");
                return;
            }

            for(int i = 0; i<graphicsCards.size(); i++){
                System.out.println("GraphicsCard " + graphicsCards.get(i).getVideoMemory() + ", id " + i);
            }
            for(int i = 0; i<networkAdapters.size(); i++){
                System.out.println("NetworkAdapter" + networkAdapters.get(i).getNrOfPorts() + ", id " + i);
            }

            System.out.println("Want to select a graphics card? (0/1)");
            int isgraph = Integer.parseInt(s.nextLine());

            if (isgraph != 0){
                System.out.println("Which graphics card? (id mentioned in 'list all')");
                int poz = Integer.parseInt(s.nextLine());
                if(poz >= graphicsCards.size() || poz < 0){
                    System.out.println("Don't know that graphics card");
                    audit.writeToAudit("Attempted to connect to an existing component");
                    jdbcAudit.writeToAudit("Attempted to connect to an existing component");
                    return;
                }
                var deAdd = graphicsCards.get(poz);
                audit.writeToAudit("Connected to an existing component");
                jdbcAudit.writeToAudit("Connected to an existing component");
                hardwareService.addComponent(currentHardw, deAdd);
            }
            else {
                System.out.println("Which network adapter? (id mentioned in 'list all')");
                int poz = Integer.parseInt(s.nextLine());
                if(poz >= networkAdapters.size() || poz < 0){
                    System.out.println("Don't know that network adapter");
                    audit.writeToAudit("Attempted to connect to an existing component");
                    jdbcAudit.writeToAudit("Attempted to connect to an existing component");
                    return;
                }
                var deAdd = networkAdapters.get(poz);
                audit.writeToAudit("Connected to an existing component");
                jdbcAudit.writeToAudit("Connected to an existing component");
                hardwareService.addComponent(currentHardw, deAdd);
            }

            return;
        }
        if (text.equals("List its connections")){
            var dinservice = hardwareService.getAllConnections(currentHardw);

            for(var el : dinservice){
                System.out.println(el.getL() + " " + el.getR());
            }
            audit.writeToAudit("Listed current computer connections");
            jdbcAudit.writeToAudit("Listed current computer connections");
            return;
        }
        if (text.equals("List its components")){
            var dinservice = hardwareService.getAllComponents(currentHardw);

            for(var el : dinservice){
                System.out.println(el.getL() + " " + el.getR());
            }
            audit.writeToAudit("Listed current computer components");
            jdbcAudit.writeToAudit("Listed current computer components");
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

                audit.writeToAudit("Removed a connection");
                jdbcAudit.writeToAudit("Removed a connection");
            }
            catch (Exception e){
                System.out.println("Don't know that connection");
                audit.writeToAudit("Attempted to remove connection");
                jdbcAudit.writeToAudit("Attempted to remove connection");
            }
            return;
        }
        if (text.equals("Remove component")){
            try{
                System.out.println("Which component do you want to remove? (index)");
                var x = Integer.parseInt(s.nextLine());

                var dinservice = hardwareService.getAllComponents(currentHardw);
                var poz = -1;
                for (int i = 0; i< dinservice.size(); i++){
                    if (dinservice.get(i).getL() == x)
                        poz = i;
                }
                if (poz == -1){
                    throw new Exception("not a valid int");
                }
                hardwareService.removeComponent(currentHardw, poz);

                audit.writeToAudit("Removed a connection");
                jdbcAudit.writeToAudit("Removed a connection");
            }
            catch (Exception e){
                System.out.println("Don't know that component");
                audit.writeToAudit("Attempted to remove component");
                jdbcAudit.writeToAudit("Attempted to remove component");
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

    private PcComponent readComponent(){
        System.out.println("Graphics Card (0) or Network Adapter (1)? ");
        boolean isgraph = Integer.parseInt(s.nextLine()) == 0;

        if(isgraph){
            int vidMemo;
            boolean forMining;
            double price;

            System.out.println("video memory:");
            vidMemo = Integer.parseInt(s.nextLine());
            System.out.println("is for mining? (0/1):");
            forMining = Integer.parseInt(s.nextLine()) == 1;
            System.out.println("price: ");
            price = Double.parseDouble(s.nextLine());

            GraphicsCard rez = new GraphicsCard(vidMemo, forMining, price);

            return rez;
        }
        else {
            int ports;
            double price;

            System.out.println("ports:");
            ports = Integer.parseInt(s.nextLine());
            System.out.println("price: ");
            price = Double.parseDouble(s.nextLine());

            NetworkAdapter rez = new NetworkAdapter(ports, price);

            return rez;
        }

    }
}
