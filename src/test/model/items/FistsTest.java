package model.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FistsTest {

    Fists fists;

    @BeforeEach
    public void setup() {
        fists = new Fists();
    }

    @Test
    public void testToString() {
        assertEquals("Fists", fists.toString());
    }
}
