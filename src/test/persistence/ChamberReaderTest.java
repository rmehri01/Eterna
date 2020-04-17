package persistence;

import model.Chamber;
import model.characters.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ChamberReaderTest {

    private ChamberReader testReader;
    private Chamber testChamber;
    private Player testPlayer;

    @BeforeEach
    public void setup() {
        testReader = ChamberReader.getInstance();
        testPlayer = new Player("James", 1, 1);
        testChamber = new Chamber(testPlayer);
    }

    @Test
    public void saveChamberAndRestoreNotSuccessful() {
        assertDoesNotThrow(() -> testChamber.save());
        Assertions.assertEquals("Jamesfloor1.data", testReader.getAllFiles()[0].getName());

        assertThrows(IOException.class, () -> testReader.readChamber("unknownfile.data"));
    }

    @Test
    public void saveChamberAndRestoreSuccessful() {
        assertDoesNotThrow(() -> testChamber.save());
        assertEquals("Jamesfloor1.data", testReader.getAllFiles()[0].getName());

        assertDoesNotThrow(() -> testReader.readChamber("Jamesfloor1.data"));
    }

    @Test
    public void testGetAllFilesMultiple() {
        Chamber secondChamber = new Chamber(testPlayer);
        secondChamber.setFloorNumber(3);
        assertDoesNotThrow(testChamber::save);
        assertDoesNotThrow(secondChamber::save);

        List<String> filesRead = Arrays.stream(testReader.getAllFiles())
                .map(File::getName)
                .collect(Collectors.toList());

        assertTrue(filesRead.contains("Jamesfloor1.data"));
        assertTrue(filesRead.contains("Jamesfloor3.data"));
    }
}
