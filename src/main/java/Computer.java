import Hardware.Hardware;
import Software.Software;

public class Computer {

    private final int ID;
    private Hardware hardware;
    private Software software;

    static int counter;
    static {
        counter = 0;
    }

    public Computer() {
        ID = counter;
        counter++;
    }

    public int getID() {
        return ID;
    }

    public Computer(int id, Hardware hardware, Software software) {
        ID = id;
        this.hardware = hardware;
        this.software = software;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public double totalValueOfPC(){
        return hardware.totalValueOfPC();
    }
}
