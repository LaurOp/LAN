package Hardware;

import java.util.Objects;

public abstract class PcComponent {
    protected String producer;
    protected String model;
    protected Integer prodYear;
    protected Integer powerConsumption;
    protected boolean hasRGB;
    protected boolean hasWarranty;
    protected double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getHasWarranty() {
        return hasWarranty;
    }

    public void setHasWarranty(boolean hasWarranty) {
        this.hasWarranty = hasWarranty;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProdYear() {
        return prodYear;
    }

    public void setProdYear(Integer prodYear) {
        this.prodYear = prodYear;
    }

    public Integer getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public boolean getHasRGB() {
        return hasRGB;
    }

    public void setHasRGB(boolean hasRGB) {
        this.hasRGB = hasRGB;
    }

    @Override
    public String toString() {
        return "Hardware.Hardware.PcComponent{" +
                "producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", prodYear=" + prodYear +
                ", powerConsumption=" + powerConsumption +
                ", hasRGB=" + hasRGB +
                '}';
    }

    public PcComponent() {
    }

    public PcComponent(double price) {
        this.price = price;
    }

    public PcComponent(String producer, String model, Integer prodYear, Integer powerConsumption, boolean hasRGB, boolean hasWarranty, double price) {
        this.producer = producer;
        this.model = model;
        this.prodYear = prodYear;
        this.powerConsumption = powerConsumption;
        this.hasRGB = hasRGB;
        this.hasWarranty = hasWarranty;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PcComponent that = (PcComponent) o;

        if (hasRGB != that.hasRGB) return false;
        if (!Objects.equals(producer, that.producer)) return false;
        if (!Objects.equals(model, that.model)) return false;
        if (!Objects.equals(prodYear, that.prodYear)) return false;
        return Objects.equals(powerConsumption, that.powerConsumption);
    }

}
