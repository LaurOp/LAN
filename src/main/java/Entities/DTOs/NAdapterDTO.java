package Entities.DTOs;

public class NAdapterDTO {
    private Integer nrOfPorts;
    private Integer price;

    public NAdapterDTO() {
        this.nrOfPorts = 0;
        this.price = 0;
    }

    public NAdapterDTO(Integer nrOfPorts, Integer price) {
        this.nrOfPorts = nrOfPorts;
        this.price = price;
    }

    public Integer getNrOfPorts() {
        return nrOfPorts;
    }

    public Integer getPrice() {
        return price;
    }

    public void setNrOfPorts(Integer nrOfPorts) {
        this.nrOfPorts = nrOfPorts;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "NAdapterDTO{" +
                "nrOfPorts=" + nrOfPorts +
                ", price=" + price +
                '}';
    }
}

