package Entities;

import java.util.ArrayList;

public class Network {

    private ArrayList<Computer> computers = new ArrayList<>();

    public Network() {
    }

    public Network(ArrayList<Computer> computers) {
        this.computers = computers;
    }

    public ArrayList<Computer> getComputers() {
        return computers;
    }

    public void setComputers(ArrayList<Computer> computers) {
        this.computers = computers;
    }
}
