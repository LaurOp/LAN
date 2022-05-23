package Entities.DTOs;

public class GraphicsCardDTO {
    String name;
    private int videoMemory;
    private boolean forMining;
    private int price;

    public GraphicsCardDTO() {
        this.videoMemory = 0;
        this.forMining = false;
        this.price = 0;
        this.name = "";
    }

    public GraphicsCardDTO(String name, int videoMemory, boolean forMining, int price) {
        this.videoMemory = videoMemory;
        this.forMining = forMining;
        this.price = price;
        this.name = name;
    }

    public int getVideoMemory() {
        return videoMemory;
    }

    public boolean isForMining() {
        return forMining;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setVideoMemory(int videoMemory) {
        this.videoMemory = videoMemory;
    }

    public void setForMining(boolean forMining) {
        this.forMining = forMining;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GraphicsCardDTO{" +
                "videoMemory=" + videoMemory +
                ", forMining=" + forMining +
                ", price=" + price +
                '}';
    }
}
