package View;

import Entities.DTOs.PrinterDTO;
import Entities.DTOs.SwitchDTO;
import Entities.Models.Hardware.GraphicsCard;
import Entities.Models.Hardware.NetworkAdapter;
import Entities.Models.Hardware.Printer;
import Entities.Models.Hardware.Switch;
import Services.JDBC.ConnectionManager;
import Services.JDBC.Wrappers.GraphicsCardServiceJDBC;
import Services.JDBC.Wrappers.NetworkAdapterServiceJDBC;
import Services.JDBC.Wrappers.PrinterServiceJDBC;
import Services.JDBC.Wrappers.SwitchServiceJDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

class JDBCmenu extends Main {
    private static final Scanner s = new Scanner(System.in);

    private final PrinterServiceJDBC printerServiceJDBC;
    private final SwitchServiceJDBC switchServiceJDBC;
    private final GraphicsCardServiceJDBC graphicsCardServiceJDBC;
    private final NetworkAdapterServiceJDBC networkAdapterServiceJDBC;

    public JDBCmenu(ConnectionManager connectionManager) throws SQLException, IOException, ClassNotFoundException {
        super();
        options = new int[]{1, 2, 3, 4, 5};
        optionsText = new String[]{"Printers", "Switches", "Graphics cards", "Network adapters", "Exit"};
        printerServiceJDBC = new PrinterServiceJDBC(connectionManager);
        switchServiceJDBC = new SwitchServiceJDBC(connectionManager);
        graphicsCardServiceJDBC = new GraphicsCardServiceJDBC(connectionManager);
        networkAdapterServiceJDBC = new NetworkAdapterServiceJDBC(connectionManager);
    }

    void pickJDBC(String text) throws Exception {
        if (text.equals("Exit")) {
            throw new Exception("\n See you again!");
        }
        if (text.equals("Back")) {
            options = new int[]{1, 2, 3, 4, 5};
            optionsText = new String[]{"Printers", "Switches", "Graphics cards", "Network adapters", "Exit"};
            return;
        }
        if (text.equals("Printers")) {
            options = new int[]{1, 2, 3, 4, 5, 6, 7};
            optionsText = new String[]{"Create Printer", "Read Printers", "Read Printer By IP", "Update Printer", "Delete Printer", "Back", "Exit"};
            jdbcAudit.writeToAudit("Chose Printers");
            return;
        }
        if (text.equals("Create Printer")){
            System.out.println("ip:");
            var ip = s.nextLine();
            System.out.println("brand:");
            var br = s.nextLine();
            System.out.println("model");
            var mo = s.nextLine();
            System.out.println("pages per min:");
            var pa = Integer.parseInt(s.nextLine());

            Printer pr = new Printer(ip, br, mo, pa);

            printerServiceJDBC.addPrinter(pr);

            jdbcAudit.writeToAudit("Added printer to DB");
            return;
        }
        if (text.equals("Read Printers")){
            var printers = printerServiceJDBC.getAllPrinters();
            for(var pr : printers){
                System.out.println(pr);
            }
            jdbcAudit.writeToAudit("Read all printer");
            return;
        }
        if (text.equals("Read Printer By IP")){
            System.out.println("ip:");
            String ip = s.nextLine();

            var rez = printerServiceJDBC.getPrinterByIP(ip);
            System.out.println(rez);
            jdbcAudit.writeToAudit("Read printer by ip");
            return;
        }
        if (text.equals("Update Printer")){
            System.out.println("ip of the printer to be updated:");
            String ip = s.nextLine();
            var toBeChanged = printerServiceJDBC.getPrinterByIP(ip);

            System.out.println("brand of the new printer:");
            var br = s.nextLine();
            System.out.println("model of the new printer:");
            var mo = s.nextLine();
            System.out.println("nr of pages for the new printer:");
            var nr =Integer.parseInt(s.nextLine());

            Printer newPrinter = new Printer(br, mo, nr);

            printerServiceJDBC.updatePrinter(toBeChanged, newPrinter);
            jdbcAudit.writeToAudit("Updated a printer");
            return;
        }
        if (text.equals("Delete Printer")){
            System.out.println("ip of the printer to be deleted:");
            String ip = s.nextLine();

            printerServiceJDBC.deletePrinter(new Printer(ip, "", "", 0));
            jdbcAudit.writeToAudit("Deleted a printer");
            return;
        }

        if (text.equals("Switches")) {
            options = new int[]{1, 2, 3, 4, 5, 6, 7};
            optionsText = new String[]{"Create Switch", "Read Switches", "Read Switch By IP", "Update Switch", "Delete Switch", "Back", "Exit"};
            jdbcAudit.writeToAudit("Chose Switches");
            return;
        }
        if (text.equals("Create Switch")){
            System.out.println("ip:");
            var ip = s.nextLine();
            System.out.println("brand:");
            var br = s.nextLine();
            System.out.println("gigabitEthernet:");
            var gig = Objects.equals(s.nextLine(), "1");

            Switch sw  = new Switch(ip, br, gig);
            switchServiceJDBC.addSwitch(sw);

            jdbcAudit.writeToAudit("Created Switch");
            return;
        }
        if (text.equals("Read Switches")){

            var switches = switchServiceJDBC.getAllSwitches();
            for(var s : switches){
                System.out.println(s);
            }

            jdbcAudit.writeToAudit("Read all switches");
            return;
        }
        if (text.equals("Read Switch By IP")){
            System.out.println("ip:");
            String ip = s.nextLine();

            var rez = switchServiceJDBC.getSwitchByIP(ip);
            System.out.println(rez);

            jdbcAudit.writeToAudit("Read a switch by ip");
            return;
        }
        if (text.equals("Update Switch")){
            System.out.println("ip of the switch to be updated:");
            String ip = s.nextLine();
            var toBeChanged = switchServiceJDBC.getSwitchByIP(ip);

            System.out.println("brand of the new switch:");
            var br = s.nextLine();
            System.out.println("gigabitEthernet?(0/1):");
            var giga = Objects.equals(s.nextLine(), "1");

            Switch newSwitch = new Switch(br, giga);

            switchServiceJDBC.updateSwitch(toBeChanged, newSwitch);

            jdbcAudit.writeToAudit("Updated a switch");
            return;
        }
        if (text.equals("Delete Switch")){
            System.out.println("ip of the switch to be deleted:");
            String ip = s.nextLine();

            switchServiceJDBC.deleteSwitch(new Switch(ip, "", false));
            jdbcAudit.writeToAudit("Deleted a switch");
            return;
        }
        if (text.equals("Graphics cards")) {

            options = new int[]{1, 2, 3, 4, 5, 6, 7};
            optionsText = new String[]{"Create Graphics", "Read Graphics", "Read Graphics By Name", "Update Graphics", "Delete Graphics", "Back", "Exit"};
            jdbcAudit.writeToAudit("Chose Graphics cards");
            return;
        }
        if (text.equals("Create Graphics")){
            System.out.println("name:");
            var nam = s.nextLine();
            System.out.println("video memory:");
            var mem = Integer.parseInt(s.nextLine());
            System.out.println("is for mining?(0/1):");
            var min = Objects.equals(s.nextLine(), "1");
            System.out.println("price:");
            var pric = Integer.parseInt(s.nextLine());

            GraphicsCard gr = new GraphicsCard(nam, mem, min, pric);
            graphicsCardServiceJDBC.addGraphicsCard(gr);

            jdbcAudit.writeToAudit("Created a Graphics card");
            return;
        }
        if (text.equals("Read Graphics")){

            var graphics = graphicsCardServiceJDBC.getAllGraphicsCards();
            for(var g : graphics){
                System.out.println(g);
            }

            jdbcAudit.writeToAudit("Read all graphics cards");
            return;
        }
        if (text.equals("Read Graphics By Name")){
            System.out.println("name:");
            String name = s.nextLine();

            var rez = graphicsCardServiceJDBC.getAllGraphicsCardsByName(name);
            for(var g : rez){
                System.out.println(g);
            }

            jdbcAudit.writeToAudit("Read graphics cards by name");
            return;
        }
        // BUGGED; updated name updates in database but gets displayed as old one
        if (text.equals("Update Graphics")){
            System.out.println("name of the graphics cards to be updated:");
            String name = s.nextLine();
            var aux = graphicsCardServiceJDBC.getAllGraphicsCardsByName(name);
            var toBeChanged = aux.get(0);

            System.out.println("video memory of the new graphics cards:");
            var vid = Integer.parseInt(s.nextLine());
            System.out.println("for mining?(0/1):");
            var min = Objects.equals(s.nextLine(), "1");
            System.out.println("price of the new graphics cards:");
            var pri = Integer.parseInt(s.nextLine());

            GraphicsCard newGraphicsCard = new GraphicsCard(name, vid, min, pri);

            graphicsCardServiceJDBC.updateGraphicsCard(toBeChanged, newGraphicsCard);

            jdbcAudit.writeToAudit("Updated a graphics card");
            return;
        }
        if (text.equals("Delete Graphics")){
            System.out.println("name of the graphics cards to be deleted:");
            String name = s.nextLine();

            graphicsCardServiceJDBC.deleteGraphicsCard(new GraphicsCard(name, 0, false, 0));

            jdbcAudit.writeToAudit("Deleted at least one graphics card");
            return;
        }
        if (text.equals("Network adapters")) {

            options = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
            optionsText = new String[]{"Create NAdapter", "Read NAdapters", "Update NAdapter price", "Update NAdapter ports", "Delete NAdapters by price", "Delete NAdapters by ports", "Back", "Exit"};
            jdbcAudit.writeToAudit("Chose Network adapters");
            return;
        }
        if (text.equals("Create NAdapter")){
            System.out.println("number of ports:");
            var por = Integer.parseInt(s.nextLine());
            System.out.println("price:");
            var pri = Integer.parseInt(s.nextLine());

            NetworkAdapter na = new NetworkAdapter(por, pri);
            networkAdapterServiceJDBC.addNetworkAdapter(na);

            jdbcAudit.writeToAudit("Created a Network adapter");
            return;
        }
        if (text.equals("Read NAdapters")){
            var adapters = networkAdapterServiceJDBC.getAllNAdapters();
            for(var a : adapters){
                System.out.println(a);
            }

            jdbcAudit.writeToAudit("Read all network adapters");
            return;
        }
        if (text.equals("Update NAdapter price")){
            System.out.println("The number of ports of the adapters to change:");
            var nr = Integer.parseInt(s.nextLine());
            System.out.println("New price:");
            var pri = Integer.parseInt(s.nextLine());

            networkAdapterServiceJDBC.updatePrice(new NetworkAdapter(nr, 0), pri);

            jdbcAudit.writeToAudit("Updated network adapter's price");
            return;
        }
        if (text.equals("Update NAdapter ports")){
            System.out.println("The price of the adapters to change:");
            var pri = Integer.parseInt(s.nextLine());
            System.out.println("New number of ports:");
            var por = Integer.parseInt(s.nextLine());

            networkAdapterServiceJDBC.updatePorts(new NetworkAdapter(0, pri), por);

            jdbcAudit.writeToAudit("Updated network adapter's ports");
            return;
        }
        if (text.equals("Delete NAdapters by price")){
            System.out.println("The price of the adapters to be deleted:");
            var pri = Integer.parseInt(s.nextLine());

            networkAdapterServiceJDBC.deleteByPrice(new NetworkAdapter(0,pri));
            jdbcAudit.writeToAudit("Deleted network adapters by price");
            return;
        }
        if (text.equals("Delete NAdapters by ports")){
            System.out.println("The number of ports of the adapters to be deleted:");
            var por = Integer.parseInt(s.nextLine());

            networkAdapterServiceJDBC.deleteByPorts(new NetworkAdapter(por, 0));
            jdbcAudit.writeToAudit("Deleted network adapters by ports");
            return;
        }

    }


}
