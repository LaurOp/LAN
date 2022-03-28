package Repositories;

import Entities.Hardware.GraphicsCard;
import Entities.Hardware.NetworkAdapter;
import Entities.Hardware.PcComponent;
import Entities.Network;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;

public class PcComponentRepository implements GenericRepository<PcComponent> {

    ArrayList<GraphicsCard> videoCards = new ArrayList<>();
    ArrayList<NetworkAdapter> networkAdapters = new ArrayList<>();


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
}
