package Entities.DTOs;


public class PrinterDTO {
    private String IP;
    private String brand;
    private String model;
    private int pagesPerMinute;

    public PrinterDTO() {
        this.IP = "";
        this.brand = "";
        this.model = "";
        this.pagesPerMinute = 0;
    }

    public PrinterDTO(String IP, String brand, String model, int pagesPerMinute) {
        this.IP = IP;
        this.brand = brand;
        this.model = model;
        this.pagesPerMinute = pagesPerMinute;
    }

    public String getIP() {
        return IP;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getPagesPerMinute() {
        return pagesPerMinute;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPagesPerMinute(int pagesPerMinute) {
        this.pagesPerMinute = pagesPerMinute;
    }

    @Override
    public String toString() {
        return "PrinterDTO{" +
                "IP='" + IP + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", pagesPerMinute=" + pagesPerMinute +
                '}';
    }
}
