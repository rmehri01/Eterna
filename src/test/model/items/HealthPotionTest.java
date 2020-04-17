package model.items;

import model.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HealthPotionTest {
    private Player testPlayer;
    private Item potion;

    @BeforeEach
    public void setup() {
        potion = new HealthPotion(0, 0);
        testPlayer = new Player("Jordan", 100, 2);
    }

    @Test
    public void testInteract() {
        potion.interact(testPlayer);
        assertTrue(testPlayer.getInventory().contains(potion));
        assertEquals(1, testPlayer.getInventory().size());
    }

    @Test
    public void testUseHealthPotion() {
        testPlayer.takeDamage(testPlayer.getHealth() - 10);
        potion.use(testPlayer);
        assertEquals(10 + HealthPotion.HEAL_AMOUNT, testPlayer.getHealth());
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("P", potion.stringRepresentation());
    }
}
