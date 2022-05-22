package Services.JDBC.Wrappers;

import Entities.DTOs.PrinterDTO;
import Entities.Models.Hardware.Printer;
import Repositories.JDBC.PrinterJDBCRepository;
import Services.JDBC.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrinterServiceJDBC {

    private final PrinterJDBCRepository  printerJDBCRepository;

    public PrinterServiceJDBC(ConnectionManager connectionManager) {
        printerJDBCRepository = new PrinterJDBCRepository(connectionManager);
    }

    // C
    public void addPrinter(Printer pr) throws SQLException {
        printerJDBCRepository.createNewPrinter(pr.getIP(), pr.getBrand(), pr.getModel(), pr.getPagesPerMinute());
    }

    // R
    public List<Printer> getAllPrinters() throws SQLException {
        var res = printerJDBCRepository.findAllPrinters();
        var result = new ArrayList<Printer>();
        for(var p : res){
            Printer pr = new Printer();
            pr.setIP(p.getIP());
            pr.setBrand(p.getBrand());
            pr.setModel(p.getModel());
            pr.setPagesPerMinute(p.getPagesPerMinute());

            result.add(pr);
        }

        return result;
    }

    public Printer getPrinterByIP(String ip) throws SQLException {
        var p = printerJDBCRepository.getPrinterByIP(ip);

        Printer pr = new Printer();
        pr.setIP(p.getIP());
        pr.setBrand(p.getBrand());
        pr.setModel(p.getModel());
        pr.setPagesPerMinute(p.getPagesPerMinute());

        return pr;
    }

    // U
    public void updatePrinter(Printer toBeChanged, Printer newPrinter) throws SQLException {
        PrinterDTO toBeChanged2= new PrinterDTO();
        PrinterDTO newPrinter2 = new PrinterDTO();

        toBeChanged2.setIP(toBeChanged.getIP());
        toBeChanged2.setBrand(toBeChanged.getBrand());
        toBeChanged2.setModel(toBeChanged.getModel());
        toBeChanged2.setPagesPerMinute(toBeChanged.getPagesPerMinute());

        newPrinter2.setIP(newPrinter.getIP());
        newPrinter2.setBrand(newPrinter.getBrand());
        newPrinter2.setModel(newPrinter.getModel());
        newPrinter2.setPagesPerMinute(newPrinter.getPagesPerMinute());

        printerJDBCRepository.updatePrinter(toBeChanged2, newPrinter2);

    }

    // D
    public void deletePrinter(Printer p) throws SQLException {
        PrinterDTO pr = new PrinterDTO();
        pr.setIP(p.getIP());
        pr.setBrand(p.getBrand());
        pr.setModel(p.getModel());
        pr.setPagesPerMinute(p.getPagesPerMinute());

        printerJDBCRepository.deletePrinter(pr);
    }

}
