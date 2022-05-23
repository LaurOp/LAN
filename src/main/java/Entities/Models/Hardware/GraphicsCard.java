package Entities.Models.Hardware;

public class GraphicsCard  extends PcComponent {
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphicsCard() {
        super();
        this.videoMemory = 2048;
        this.forMining = false;
        this.name = "GraphicsDefault";
    }


    public GraphicsCard(double price) {
        super(price);
        this.videoMemory = 2048;
        this.forMining = false;
        this.name = "GraphicsDefault";
    }

    public GraphicsCard(int videoMemo, double price) {
        super(price);
        this.videoMemory = videoMemo;
        this.forMining = false;
        this.name = "Graphics" +videoMemo;
    }
    public GraphicsCard(int videoMemo, boolean isForMining, double price) {
        super(price);
        this.videoMemory = videoMemo;
        this.forMining = isForMining;
        this.name = "Graphics"+videoMemo;
    }

    public GraphicsCard(String name, int videoMemory, boolean forMining, int price) {
        super((int)price);
        this.name = name;
        this.videoMemory = videoMemory;
        this.forMining = forMining;
    }

    @Override
    public String toString() {
        return "GraphicsCard{" +
                "name='" + name + '\'' +
                ", videoMemory=" + videoMemory +
                ", forMining=" + forMining +
                ", price=" + price +
                '}';
    }
}
