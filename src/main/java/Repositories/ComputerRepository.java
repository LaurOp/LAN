package Repositories;

import Entities.Computer;
import Entities.Hardware.Connectable;
import Entities.Hardware.Printer;
import Entities.Hardware.Switch;
import Entities.Network;

import java.util.Arrays;

public class ComputerRepository implements GenericRepository<Computer> {

    private Computer[] computers = new Computer[100];

    public Computer[] getComputers() {
        return computers;
    }

    public void setComputers(Computer[] computers) {
        this.computers = computers;
    }

    @Override
    public void add(Computer entity) {

        for (int i = 0; i < computers.length; i++) {
            if (computers[i] == null) {
                computers[i] = entity;
                return;
            }
        }

        // increase capacity
        Computer[] newStorage = Arrays.<Computer, Computer>copyOf(computers, 10 + computers.length, Computer[].class);

        newStorage[computers.length] = entity;
        computers = newStorage;
    }

    @Override
    public Computer get(int id) {
        return computers[id];
    }

    @Override
    public void update(Computer entity, Computer newEntity) {
        for (int i = 0; i < computers.length; i++) {
            if (computers[i] == entity) {
                computers[i] = newEntity;
                return;
            }
        }
    }

    @Override
    public void delete(Computer entity) {
        for (int i = 0; i < computers.length; i++) {
            if (computers[i].equals(entity)) {
                computers[i] = null;
                return;
            }
        }
    }

    @Override
    public int getSize() {
        return computers.length;
    }

    @Override
    public boolean isIn(Computer entity) {
        for (int i = 0; i < computers.length; i++)
            if (computers[i] == entity) {
                return true;
            }

            return false;

    }
}