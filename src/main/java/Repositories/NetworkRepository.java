package Repositories;

import Entities.Network;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NetworkRepository implements GenericRepository<Network> {

    private Network[] server = new Network[20];

    @Override
    public void add(Network entity) {

        for (int i=0; i<server.length; i++) {
            if (server[i] == null) {
                server[i] = entity;
                return;
            }
        }

        // increase capacity
        Network[] newStorage = Arrays.<Network, Network>copyOf(server, 10+server.length, Network[].class);

        newStorage[server.length] = entity;
        server = newStorage;
    }

    @Override
    public Network get(int id) {
        return server[id];
    }

    @Override
    public void update(Network entity) {    //bring the updated one first in line
        int poz = 0;
        for (int i=0; i<server.length; i++) {
            if (server[i].equals(entity)) {
                poz = i;
                break;
            }
        }
        Network temp = server[poz];
        server[poz] = server[0];
        server[0] = temp;
    }

    @Override
    public void delete(Network entity) {
        for (int i=0; i<server.length; i++) {
            if (server[i].equals(entity)) {
                server[i] = null;
                return;
            }
        }
    }

    @Override
    public int getSize() {
        return server.length;
    }
}
