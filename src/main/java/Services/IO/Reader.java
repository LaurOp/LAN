package Services.IO;


import Entities.Models.Hardware.*;
import Repositories.NO_JDBC.ConnectableRepository;
import Repositories.NO_JDBC.HardwareRepository;
import Repositories.NO_JDBC.PcComponentRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Singleton
public class Reader {
    private static Reader instance = null;

    private Reader(){}

    public static Reader getInstance()
    {
        if (instance == null)
            instance = new Reader();
        return instance;
    }

    private ConnectableRepository connectableRepository = new ConnectableRepository();
    private PcComponentRepository pcComponentRepository = new PcComponentRepository();
    private HardwareRepository hardwareRepository = new HardwareRepository();

    BufferedReader reader;

    public void readPrinters(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        var names = reader.readLine();

        Hardware defaultHardware = new Hardware();

        String line;
        while((line = reader.readLine()) != null){
            var splitLine = line.split(",");
            Printer newPrinter = new Printer(splitLine[0], splitLine[1]);

            connectableRepository.add(newPrinter);
            defaultHardware.addConnection(newPrinter);
        }

        hardwareRepository.add(defaultHardware);
        reader.close();
    }

    public void readSwitches(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        var names = reader.readLine();

        Hardware defaultHardware = new Hardware();

        String line;
        while((line = reader.readLine()) != null){
            var splitLine = line.split(",");
            Switch newSwitch = new Switch(splitLine[0], Integer.parseInt(splitLine[1])==1);

            connectableRepository.add(newSwitch);
            defaultHardware.addConnection(newSwitch);
        }

        hardwareRepository.add(defaultHardware);
        reader.close();
    }

    public void readGraphicsCards(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        var names = reader.readLine();

        Hardware defaultHardware = new Hardware();

        String line;
        while((line = reader.readLine()) != null){
            var splitLine = line.split(",");
            GraphicsCard newGraphics = new GraphicsCard(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1])==1, Double.parseDouble(splitLine[2]));

            pcComponentRepository.add(newGraphics);
            defaultHardware.addPcComponent(newGraphics);
        }

        hardwareRepository.add(defaultHardware);
        reader.close();
    }

    public void readNetworkAdapters(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        var names = reader.readLine();

        Hardware defaultHardware = new Hardware();

        String line;
        while((line = reader.readLine()) != null){
            var splitLine = line.split(",");
            NetworkAdapter newNetworkAdapter = new NetworkAdapter(Integer.parseInt(splitLine[0]),Double.parseDouble(splitLine[1]));

            pcComponentRepository.add(newNetworkAdapter);
            defaultHardware.addPcComponent(newNetworkAdapter);
        }

        hardwareRepository.add(defaultHardware);
        reader.close();
    }

}
