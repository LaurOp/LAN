import Entities.Computer;
import Entities.Hardware.GraphicsCard;
import Entities.Hardware.Hardware;
import Entities.Hardware.NetworkAdapter;
import Services.ConnectableService;
import Services.HardwareService;
import org.testng.annotations.Test;

public class AutomatedTests {


    ConnectableService connectableService = new ConnectableService();
    HardwareService hardwareService = new HardwareService();

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
}
