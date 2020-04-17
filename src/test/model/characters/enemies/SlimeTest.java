package model.characters.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlimeTest {

    Slime testRat;

    @BeforeEach
    public void setup() {
        testRat = new Slime(1, 5);
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("L", testRat.stringRepresentation());
    }
}
