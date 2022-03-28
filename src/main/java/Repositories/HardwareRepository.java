package Repositories;

import Entities.Hardware.Hardware;
import Entities.Network;

import java.util.Arrays;

public class HardwareRepository implements GenericRepository<Hardware> {
    private Hardware[] storage = new Hardware[20];

    @Override
    public void add(Hardware entity) {

        for (int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = entity;
                return;
            }
        }

        // increase capacity
        Hardware[] newStorage = Arrays.<Hardware, Hardware>copyOf(storage, 10+storage.length, Hardware[].class);

        newStorage[storage.length] = entity;
        storage = newStorage;
    }

    @Override
    public Hardware get(int id) {
        return storage[id];
    }

    @Override
    public void update(Hardware entity, Hardware newEntity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i] == entity) {
                storage[i] = newEntity;
                return;
            }
        }
    }

    @Override
    public void delete(Hardware entity) {
        for (int i=0; i<storage.length; i++) {
            if (storage[i].equals(entity)) {
                storage[i] = null;
                return;
            }
        }
    }

    @Override
    public int getSize() {
        return storage.length;
    }

    @Override
    public boolean isIn(Hardware entity) {
        for (int i = 0; i < storage.length; i++)
            if (storage[i] == entity) {
                return true;
            }

        return false;
    }
}
