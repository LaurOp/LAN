import Hardware.Hardware;
import Hardware.NetworkAdapter;
import Hardware.GraphicsCard;
import org.testng.annotations.Test;

public class Main {
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
        System.out.println(comp1.totalValueOfPC());
    }
}
