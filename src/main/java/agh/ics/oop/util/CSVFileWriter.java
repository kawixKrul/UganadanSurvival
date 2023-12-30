package agh.ics.oop.util;

import agh.ics.oop.interfaces.MapChangeListener;
import agh.ics.oop.interfaces.WorldMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter implements MapChangeListener {
    private final FileWriter csvFile;

    public CSVFileWriter(String fileName) throws IOException{
        File file = new File("logs");
        if (!file.isDirectory()) {
            if (file.mkdir()) {
                System.out.println("Logs directory created");
            }
        }
        this.csvFile = new FileWriter("logs/"+fileName);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        try {
            this.csvFile.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeFile() throws IOException {
        this.csvFile.close();
    }
}
