package View;

import Entities.DTOs.PrinterDTO;
import Entities.Models.Hardware.Printer;
import Services.JDBC.ConnectionManager;
import Services.JDBC.Wrappers.PrinterServiceJDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

class JDBCmenu extends Main {
    private static final Scanner s = new Scanner(System.in);
    private final PrinterServiceJDBC printerServiceJDBC;
    public JDBCmenu(ConnectionManager connectionManager) throws SQLException, IOException, ClassNotFoundException {
        super();
        options = new int[]{1, 2, 3, 4, 5};
        optionsText = new String[]{"Printers", "Switches", "Graphics cards", "Network adapters", "Exit"};
        printerServiceJDBC = new PrinterServiceJDBC(connectionManager);
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
            return;
        }
        if (text.equals("Read Printers")){
            var printers = printerServiceJDBC.getAllPrinters();
            for(var pr : printers){
                System.out.println(pr);
            }
            return;
        }
        if (text.equals("Read Printer By IP")){
            System.out.println("ip:");
            String ip = s.nextLine();

            var rez = printerServiceJDBC.getPrinterByIP(ip);
            System.out.println(rez);

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

            return;
        }
        if (text.equals("Delete Printer")){
            System.out.println("ip of the printer to be deleted:");
            String ip = s.nextLine();

            printerServiceJDBC.deletePrinter(new Printer(ip, "", "", 0));

            return;
        }
        if (text.equals("Switches")) {

//            options = new int[]{1, 2, 3, 4, 5, 6};
//            optionsText = new String[]{"Create", "Read", "Update", "Delete", "Back", "Exit"};
            return;
        }
        if (text.equals("Graphics cards")) {

            return;
        }
        if (text.equals("Network adapters")) {

            return;
        }

    }


}
