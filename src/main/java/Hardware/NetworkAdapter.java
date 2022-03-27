package Hardware;

public class NetworkAdapter extends PcComponent {

    private Integer nrOfPorts;

    public Integer getNrOfPorts() {
        return nrOfPorts;
    }

    public void setNrOfPorts(Integer nrOfPorts) {
        this.nrOfPorts = nrOfPorts;
    }

    public NetworkAdapter() {
        super();
        nrOfPorts = 2;
    }

    public NetworkAdapter(int ports) {
        super();
        nrOfPorts = ports;
    }

    public NetworkAdapter(int ports, double price) {
        super(price);
        nrOfPorts = ports;
    }


}
