package model.items;

import model.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.items.Sword.ATTACK_STAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SwordTest {

    Sword testSword;
    Player testPlayer;

    @BeforeEach
    public void setup() {
        testSword = new Sword(1, 2);
        testPlayer = new Player("Sheeong", 100, 20);
    }

    @Test
    public void testUseItemEquipsToPlayer() {
        assertNotEquals(testSword, testPlayer.getEquipped());
        testSword.use(testPlayer);
        assertEquals(testSword, testPlayer.getEquipped());
    }

    @Test
    public void testGetAdditionalDamage() {
        assertEquals(ATTACK_STAT, testSword.getAdditionalAttack());
    }

    @Test
    public void testGetAdditionalDefense() {
        assertEquals(0, testSword.getAdditionalDefense());
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("S", testSword.stringRepresentation());
    }
}
