package Hardware;

import java.util.List;

public class Printer implements Connectable {
    @Override
    public List<Integer> getFreePorts() {
        return null;
    }

    @Override
    public boolean checkIfOnline() {
        return false;
    }

    @Override
    public void disconnectPort(int port) {

    }

    @Override
    public Integer ping(String ip) {
        return null;
    }
}
