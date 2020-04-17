package model.characters.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemonTest {

    Demon testDemon;

    @BeforeEach
    public void setup() {
        testDemon = new Demon(1, 5);
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("D", testDemon.stringRepresentation());
    }
}
