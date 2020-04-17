package persistence;

import model.Chamber;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Represents a reader that can read data from a file and create a corresponding object.
 */

public class ChamberReader {
    private static ChamberReader instance;

    // EFFECTS: creates a new chamber reader, private since it is a singleton
    private ChamberReader() {}

    public static ChamberReader getInstance() {
        if (instance == null) {
            instance = new ChamberReader();
        }

        return instance;
    }

    // EFFECTS: returns the chamber with given file name, if not found throws IOException
    public Chamber readChamber(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("data/" + fileName)
        );
        return (Chamber) objectInputStream.readObject();
    }

    // EFFECTS: returns an array of all the files in data
    public File[] getAllFiles() {
        final File folder = new File("data/");
        return folder.listFiles();
    }
}
