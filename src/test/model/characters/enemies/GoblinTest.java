package model.characters.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoblinTest {

    Goblin testGoblin;

    @BeforeEach
    public void setup() {
        testGoblin = new Goblin(1, 5);
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("G", testGoblin.stringRepresentation());
    }
}
