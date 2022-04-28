package Repositories;

import Entities.Hardware.*;
import Entities.Network;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PcComponentRepository implements GenericRepository<PcComponent> {

    static ArrayList<GraphicsCard> videoCards = new ArrayList<>();
    static ArrayList<NetworkAdapter> networkAdapters = new ArrayList<>();


    @Override
    public void add(PcComponent entity) {
        if (entity instanceof GraphicsCard)
            videoCards.add((GraphicsCard) entity);
        if (entity instanceof  NetworkAdapter)
            networkAdapters.add((NetworkAdapter) entity);
    }

    @Override
    public PcComponent get(int id) {
        return videoCards.get(id);
    }

    public PcComponent getNA(int id){
        return networkAdapters.get(id);
    }

    @Override
    public void update(PcComponent entity, PcComponent newEntity) {
        if (entity instanceof GraphicsCard)
            for (int i=0; i<videoCards.size(); i++) {
                if (videoCards.get(i) == entity) {
                    videoCards.set(i, (GraphicsCard) newEntity);
                    return;
                }
            }
        if (entity instanceof  NetworkAdapter)
            for (int i=0; i<networkAdapters.size(); i++) {
                if (networkAdapters.get(i) == entity) {
                    networkAdapters.set(i, (NetworkAdapter) newEntity);
                    return;
                }
            }
    }

    @Override
    public void delete(PcComponent entity) {
        if (entity instanceof GraphicsCard)
            videoCards.remove((GraphicsCard)entity);
        if (entity instanceof  NetworkAdapter)
            networkAdapters.remove((NetworkAdapter)entity);
    }

    @Override
    public int getSize() {
        return videoCards.size() + networkAdapters.size();
    }


    public Map<String, Number> getBothSizes(){
        Map<String, Number> coordinates = new HashMap<>();

        coordinates.put("videoCards", videoCards.size());
        coordinates.put("networkAdapters", networkAdapters.size());

        return coordinates;
    }
    @Override
    public boolean isIn(PcComponent entity) {
        if (entity instanceof GraphicsCard)
            for (GraphicsCard videoCard : videoCards) {
                if (videoCard == entity) {
                    return true;
                }
            }
        if (entity instanceof NetworkAdapter)
            for (NetworkAdapter networkAdapter : networkAdapters) {
                if (networkAdapter == entity) {
                    return true;
                }
            }

        return false;
    }
}
