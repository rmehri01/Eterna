package model.characters;

import model.characters.enemies.Enemy;
import model.items.Equippable;
import model.items.HealthPotion;
import model.items.Item;
import model.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.characters.Player.MAX_INVENTORY_SIZE;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerSpriteTest {
    Player testPlayer;
    Item potion1;
    Item potion2;
    Equippable sword;

    @BeforeEach
    public void setup() {
        testPlayer = new Player("Ryan", 10, 3);
        potion1 = new HealthPotion(0, 2);
        potion2 = new HealthPotion(0, 1);
        sword = new Sword(5, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlayer.getArmor());
        assertTrue(testPlayer.getInventory().isEmpty());
        assertEquals(0, testPlayer.getEquipped().getAdditionalAttack());
        assertEquals(0, testPlayer.getEquipped().getAdditionalDefense());
    }

    @Test
    public void testAddItemSingle() {
        testPlayer.addItem(potion1);
        assertEquals(1, testPlayer.getInventory().size());
        assertTrue(testPlayer.getInventory().contains(potion1));
    }

    @Test
    public void testAddItemTwice() {
        testPlayer.addItem(potion1);
        testPlayer.addItem(potion2);
        assertEquals(2, testPlayer.getInventory().size());
        assertTrue(testPlayer.getInventory().contains(potion1));
        assertTrue(testPlayer.getInventory().contains(potion2));
    }

    @Test
    public void testAddItemInventoryFull() {
        for (int i = 0; i < MAX_INVENTORY_SIZE; i++) {
            testPlayer.addItem(potion1);
        }
        assertEquals(MAX_INVENTORY_SIZE, testPlayer.getInventory().size());
        testPlayer.addItem(potion2);
        assertEquals(MAX_INVENTORY_SIZE, testPlayer.getInventory().size());
        assertFalse(testPlayer.getInventory().contains(potion2));
    }

    @Test
    public void testUseItem() {
        testPlayer.addItem(potion1);
        testPlayer.addItem(potion2);
        testPlayer.useItem(2);
        assertEquals(1, testPlayer.getInventory().size());
        assertFalse(testPlayer.getInventory().contains(potion2));
    }

    @Test
    public void testUseItemDoesNotExist() {
        testPlayer.addItem(potion1);
        testPlayer.addItem(potion2);
        assertThrows(IllegalArgumentException.class, () -> testPlayer.useItem(3));
        assertEquals(2, testPlayer.getInventory().size());
        assertTrue(testPlayer.getInventory().contains(potion1));
        assertTrue(testPlayer.getInventory().contains(potion2));
    }

    @Test
    public void testEquipItem() {
        testPlayer.equip(sword);
        assertEquals(sword, testPlayer.getEquipped());
    }

    @Test
    public void testAttackAdditionalDamageWithSword() {
        Character testCharacter = new Enemy("training dummy", 100, 0, 0, 6, 6);
        testPlayer.equip(sword);
        testPlayer.attack(testCharacter);
        assertEquals(100 - testPlayer.getAttackDamage() - Sword.ATTACK_STAT, testCharacter.getHealth());
    }
}
