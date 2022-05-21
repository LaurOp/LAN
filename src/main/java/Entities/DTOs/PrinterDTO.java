package Entities.DTOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrinterDTO {
    private String IP;
    private String brand;
    private String model;
    private int pagesPerMinute;

    {
        Random r = new Random();
        this.IP = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
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
