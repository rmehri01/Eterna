package model.characters.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GhostTest {

    Ghost testGhost;

    @BeforeEach
    public void setup() {
        testGhost = new Ghost(1, 5);
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("H", testGhost.stringRepresentation());
    }
}
