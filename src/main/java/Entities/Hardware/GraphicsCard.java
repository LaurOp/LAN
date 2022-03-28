package Entities.Hardware;

public class GraphicsCard  extends PcComponent {

    private int videoMemory;
    private boolean forMining;

    public int getVideoMemory() {
        return videoMemory;
    }

    public void setVideoMemory(int videoMemory) {
        this.videoMemory = videoMemory;
    }

    public boolean isForMining() {
        return forMining;
    }

    public void setForMining(boolean forMining) {
        this.forMining = forMining;
    }

    public GraphicsCard() {
        super();
        this.videoMemory = 2048;
        this.forMining = false;
    }

    public GraphicsCard(double price) {
        super(price);
    }

    public GraphicsCard(int videoMemo, double price) {
        super(price);
        this.videoMemory = videoMemo;
    }
    public GraphicsCard(int videoMemo, boolean isForMining, double price) {
        super(price);
        this.videoMemory = videoMemo;
        this.forMining = isForMining;
    }

}
