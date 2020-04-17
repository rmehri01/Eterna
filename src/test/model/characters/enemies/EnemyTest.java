package model.characters.enemies;

import model.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    Enemy testEnemy;
    Player testPlayer;

    @BeforeEach
    public void setup() {
        testEnemy = new Enemy("Orc", 100, 20, 0, 1, 1);
        testPlayer = new Player("Xiang", 150, 20);
    }

    @Test
    public void testInteractDies() {
        testEnemy.takeDamage(90);
        testEnemy.interact(testPlayer);
        assertEquals(-10, testEnemy.getHealth());
        assertFalse(testEnemy.isAlive());
    }

    @Test
    public void testInteractDoesntDie() {
        testEnemy.interact(testPlayer);
        assertEquals(80, testEnemy.getHealth());
        assertTrue(testEnemy.isAlive());
    }

    @Test
    public void testShouldRemoveTrue() {
        testEnemy.takeDamage(100);
        assertTrue(testEnemy.shouldRemove());
    }

    @Test
    public void testShouldRemoveFalse() {
        testEnemy.takeDamage(99);
        assertFalse(testEnemy.shouldRemove());
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("λ", testEnemy.stringRepresentation());
    }
}
