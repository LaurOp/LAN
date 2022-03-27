package Hardware;

import java.util.List;

public interface Connectable {
    List<Integer> getFreePorts();
    boolean checkIfOnline();
    void disconnectPort(int port);
    Integer ping(String ip);
}
