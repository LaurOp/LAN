package Entities.Models;

import Entities.Models.Computer;

import java.util.ArrayList;

public class Network {
    private int ID;
    private ArrayList<Computer> computers = new ArrayList<>();

    static int counter;
    static{
        counter = 0;
    }

    {
        ID = counter++;
    }

    public int getID() {
        return ID;
    }

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
