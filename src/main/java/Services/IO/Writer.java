package Services.IO;

import org.testng.annotations.AfterMethod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class Writer {
    private static Writer instance = null;

    static BufferedWriter writer = null;

    private Writer(){}

    public static Writer getInstance() throws IOException {
        if (instance == null) {
            instance = new Writer();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            writer = new BufferedWriter(new FileWriter("src/main/resources/Audits/audit" + timestamp.getTime() + ".txt"));
        }
        return instance;
    }

    public void writeToAudit(String actionDone) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        writer.write( actionDone+ " " + timestamp.toString() + "\n");


    }

    public void closeStream() throws IOException {
        writer.close();
    }
}
