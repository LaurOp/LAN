package Repositories;

import Entities.Hardware.*;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectableRepository implements GenericRepository<Connectable> {

    static ArrayList<Printer> printers = new ArrayList<>();
    static ArrayList<Switch> switches = new ArrayList<>();

    public boolean isIn(Connectable entity){
        if (entity instanceof Printer)
            for (Printer printer : printers) {
                if (printer == entity) {
                    return true;
                }
            }
        if (entity instanceof  Switch)
            for (Switch aSwitch : switches) {
                if (aSwitch == entity) {
                    return true;
                }
            }

        return false;
    }

    @Override
    public void add(Connectable entity) {
        if (entity instanceof Printer)
            printers.add((Printer) entity);
        if (entity instanceof  Switch)
            switches.add((Switch) entity);
    }

    @Override
    public Connectable get(int id) {
        return printers.get(id);
    }

    public Connectable getSW(int id){
        return switches.get(id);
    }

    @Override
    public void update(Connectable entity, Connectable newEntity) {
        if (entity instanceof Printer)
            for (int i=0; i<printers.size(); i++) {
                if (printers.get(i) == entity) {
                    printers.set(i, (Printer) newEntity);
                    return;
                }
            }
        if (entity instanceof  Switch)
            for (int i=0; i<switches.size(); i++) {
                if (switches.get(i) == entity) {
                    switches.set(i, (Switch) newEntity);
                    return;
                }
            }
    }

    @Override
    public void delete(Connectable entity) {
        if (entity instanceof Printer)
            printers.remove((Printer)entity);
        if (entity instanceof  Switch)
            switches.remove((Switch)entity);
    }

    @Override
    public int getSize() {
        return printers.size() + switches.size();
    }

    public Map<String, Integer> getBothSizes(){
        Map<String, Integer> coordinates = new HashMap<>();

        coordinates.put("printers", printers.size());
        coordinates.put("switches", switches.size());

        return coordinates;
    }
}
