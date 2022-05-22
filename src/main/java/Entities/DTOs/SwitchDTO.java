package Entities.DTOs;

public class SwitchDTO {
    private String IP;
    private String brand;
    private boolean gigabitEthernet;

    public String getIP() {
        return IP;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isGigabitEthernet() {
        return gigabitEthernet;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setGigabitEthernet(boolean gigabitEthernet) {
        this.gigabitEthernet = gigabitEthernet;
    }

    @Override
    public String toString() {
        return "SwitchDTO{" +
                "IP='" + IP + '\'' +
                ", brand='" + brand + '\'' +
                ", gigabitEthernet=" + gigabitEthernet +
                '}';
    }

}
