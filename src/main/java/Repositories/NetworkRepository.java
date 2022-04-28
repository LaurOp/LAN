package Repositories;

import Entities.Network;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NetworkRepository implements GenericRepository<Network> {

    static private Network[] server = new Network[20];

    public Network[] getServer() {
        return server;
    }

    public void setServer(Network[] server) {
        NetworkRepository.server = server;
    }

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

    public int getIndex(Network entity) throws Exception {
        for (int i=0; i<server.length; i++) {
            if (server[i] == entity) {
                return i;
            }
        }
        throw new Exception();
    }

    @Override
    public void update(Network entity, Network newEntity) {
        for (int i=0; i<server.length; i++) {
            if (server[i] == entity) {
                server[i] = newEntity;
                return;
            }
        }
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

    @Override
    public boolean isIn(Network entity) {
        for (Network network : server)
            if (network == entity) {
                return true;
            }

        return false;
    }
}
