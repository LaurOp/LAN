import Entities.DTOs.GraphicsCardDTO;
import Entities.DTOs.NAdapterDTO;
import Entities.DTOs.PrinterDTO;
import Entities.DTOs.SwitchDTO;
import Entities.Models.Computer;
import Entities.Models.Hardware.GraphicsCard;
import Entities.Models.Hardware.Hardware;
import Entities.Models.Hardware.NetworkAdapter;
import Repositories.JDBC.GraphicsCardJDBCRepository;
import Repositories.JDBC.NAdapterJDBCRepository;
import Repositories.JDBC.PrinterJDBCRepository;
import Repositories.JDBC.SwitchJDBCRepository;
import Services.ConnectableService;
import Services.HardwareService;
import Services.JDBC.ConnectionManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class AutomatedTests {


    ConnectableService connectableService = new ConnectableService();
    HardwareService hardwareService = new HardwareService();


    static ConnectionManager man;
    private final PrinterJDBCRepository printerJDBCRepository = new PrinterJDBCRepository(man);
    private final SwitchJDBCRepository switchJDBCRepository = new SwitchJDBCRepository(man);
    private final GraphicsCardJDBCRepository graphicsCardJDBCRepository = new GraphicsCardJDBCRepository(man);
    private final NAdapterJDBCRepository nAdapterJDBCRepository = new NAdapterJDBCRepository(man);

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


    @Test
    public void addHardware(){
        Computer comp1 = new Computer();

        Hardware hardw1 = new Hardware();

        NetworkAdapter n1 = new NetworkAdapter();
        n1.setPrice(21.1);
        hardw1.addPcComponent(n1);

        GraphicsCard g1 = new GraphicsCard();
        g1.setPrice(194.2);
        hardw1.addPcComponent(g1);

        comp1.setHardware(hardw1);
        System.out.println(hardwareService.totalValueOfPC(comp1.getHardware()));
    }

    @Test
    public void testPrinterAndSwitchOnline(){
        System.out.println(connectableService.getAllOnline());
        connectableService.createConnectable(true,"Printer1");
        connectableService.createConnectable(true,"Printer2");
        connectableService.createConnectable(true,"Printer3");
        connectableService.createConnectable(true,"Printer4");
        connectableService.createConnectable(false,"Switch1");
        connectableService.createConnectable(false,"Switch2");
        connectableService.createConnectable(false,"Switch3");

        Hardware hardware = new Hardware();
        connectableService.connectPCwithPrSw(hardware, connectableService.getConnectable(true, 1));
        connectableService.connectPCwithPrSw(hardware, connectableService.getConnectable(false, 0));

        System.out.println(connectableService.getAllOnline());

        System.out.println(connectableService.getFreePorts(connectableService.getConnectable(true, 1)));
        System.out.println(connectableService.getFreePorts(connectableService.getConnectable(false, 0)));

    }

    @Test
    public void flowJDBCRepos() throws SQLException {

        // Printer:
        PrinterJDBCRepository printerJDBCRepository = new PrinterJDBCRepository(man);
        printerJDBCRepository.createNewPrinter("1.1.1.1", "brand1", "model1", 24);
        var rez = printerJDBCRepository.getPrinterByIP("1.1.1.1");
        System.out.println(rez);
        printerJDBCRepository.updatePrinter(rez, new PrinterDTO("2.2.2.2", "brand2", "model2", 26));

        rez = printerJDBCRepository.getPrinterByIP("1.1.1.1");
        System.out.println(rez);

        printerJDBCRepository.deletePrinter(rez);

        var listrez = printerJDBCRepository.findAllPrinters();

        // Switch:
        SwitchJDBCRepository switchJDBCRepository = new SwitchJDBCRepository(man);
        switchJDBCRepository.createNewSwitch("1.1.1.1","brand1", true);

        var rez2 = switchJDBCRepository.findAllSwitches();
        for(var s : rez2){
            System.out.println(s);
        }
        var rez3 = switchJDBCRepository.getSwitchByIP("1.1.1.1");
        System.out.println(rez3);

        switchJDBCRepository.updateSwitch(rez3, new SwitchDTO("2.2.2.2","brand2",true));
        rez3 = switchJDBCRepository.getSwitchByIP("21.1.1.1");
        switchJDBCRepository.deleteSwitch(rez3);


        //GraphicsCard:
        GraphicsCardJDBCRepository graphicsCardJDBCRepository = new GraphicsCardJDBCRepository(man);
        graphicsCardJDBCRepository.createNewGraphics(256, false, 2000);

        var rez4 = graphicsCardJDBCRepository.findAllGraphics();
        for (var g : rez4){
            System.out.println(g);
        }

        var rez5 = graphicsCardJDBCRepository.getGraphicsByName("Graphics256");

        graphicsCardJDBCRepository.updateGraphics(rez5.get(0), new GraphicsCardDTO(500, true, 2999));
        rez5 = graphicsCardJDBCRepository.getGraphicsByName("Graphics500");
        graphicsCardJDBCRepository.deleteGraphics(rez5.get(0));


        //NetworkAdapter:

        NAdapterJDBCRepository nAdapterJDBCRepository = new NAdapterJDBCRepository(man);
        nAdapterJDBCRepository.createNewAdapter(3, 200);

        var rez6 = nAdapterJDBCRepository.findAllAdapters();
        for(var n : rez6){
            System.out.println(n);
        }

        nAdapterJDBCRepository.updateAdaptersPorts(rez6.get(0), 8);
        nAdapterJDBCRepository.updateAdaptersPrice(rez6.get(1), 150);

        nAdapterJDBCRepository.deleteAdaptersByPrice(new NAdapterDTO(0, 1000));
        nAdapterJDBCRepository.deleteAdaptersByPorts(new NAdapterDTO(8, 50));
        nAdapterJDBCRepository.deleteAdaptersByPorts(new NAdapterDTO(2, 50));


    }
}
