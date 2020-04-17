package model.characters;

import model.Direction;
import model.characters.enemies.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Chamber.CHAMBER_HEIGHT;
import static model.Chamber.CHAMBER_WIDTH;
import static model.characters.Character.ARMOR_REDUCTION_FACTOR;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character testCharacter;

    @BeforeEach
    public void setup() {
        testCharacter = new Enemy("Goblin", 10, 2, 0, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Goblin", testCharacter.getName());
        assertEquals(10, testCharacter.getHealth());
        assertEquals(10, testCharacter.getMaxHealth());
        assertEquals(2, testCharacter.getAttackDamage());
        assertEquals(0, testCharacter.getArmor());
        assertTrue(testCharacter.isAlive());
    }

    @Test
    public void testTakeDamageOnce() {
        testCharacter.takeDamage(2);
        assertEquals(8, testCharacter.getHealth());
    }

    @Test
    public void testTakeDamageTwiceAndNotDie() {
        testCharacter.takeDamage(3);
        assertEquals(7, testCharacter.getHealth());
        assertTrue(testCharacter.isAlive());
        testCharacter.takeDamage(5);
        assertEquals(2, testCharacter.getHealth());
        assertTrue(testCharacter.isAlive());
    }

    @Test
    public void testTakeDamageAndJustDie() {
        testCharacter.takeDamage(10);
        assertEquals(0, testCharacter.getHealth());
        assertFalse(testCharacter.isAlive());
    }

    @Test
    public void testTakeDamageAndDieOverkill() {
        testCharacter.takeDamage(100);
        assertEquals(-90, testCharacter.getHealth());
        assertFalse(testCharacter.isAlive());
    }

    @Test
    public void testTakeDamageAlreadyDeadShouldDoNothing() {
        testCharacter.takeDamage(10);
        testCharacter.takeDamage(50);
        assertEquals(0, testCharacter.getHealth());
        assertFalse(testCharacter.isAlive());
    }

    @Test
    public void testTakeDamageWithArmor() {
        Character armoredCharacter = new Enemy("Goblin", 10, 2, 5, 0, 0);
        armoredCharacter.takeDamage(10);
        assertEquals(10 - (int) (10 - 5 * ARMOR_REDUCTION_FACTOR), armoredCharacter.getHealth());
        assertTrue(testCharacter.isAlive());
    }

    @Test
    public void testHealOnce() {
        testCharacter.takeDamage(5);
        testCharacter.heal(2);
        assertEquals(7, testCharacter.getHealth());
    }

    @Test
    public void testHealTwice() {
        testCharacter.takeDamage(5);
        testCharacter.heal(4);
        assertEquals(9, testCharacter.getHealth());
        testCharacter.heal(1);
        assertEquals(10, testCharacter.getHealth());
    }

    @Test
    public void testHealOverflowShouldBeCapped() {
        testCharacter.takeDamage(3);
        testCharacter.heal(100);
        assertEquals(10, testCharacter.getHealth());
    }

    @Test
    public void testAttackOtherOnce() {
        Character other = new Player("James", 10, 5);
        testCharacter.attack(other);
        assertEquals(8, other.getHealth());
    }

    @Test
    public void testAttackOtherTwice() {
        Character other = new Player("Bob", 15, 2);
        testCharacter.attack(other);
        assertEquals(13, other.getHealth());
        testCharacter.attack(other);
        assertEquals(11, other.getHealth());
    }

    @Test
    public void testMoveRight() {
        testCharacter.setPositionX(CHAMBER_WIDTH - 2);
        testCharacter.move(Direction.RIGHT);
        assertEquals(CHAMBER_WIDTH - 1, testCharacter.getPositionX());
    }

    @Test
    public void testMoveLeft() {
        testCharacter.setPositionX(1);
        testCharacter.move(Direction.LEFT);
        assertEquals(0, testCharacter.getPositionX());
    }

    @Test
    public void testMoveUp() {
        testCharacter.setPositionY(1);
        testCharacter.move(Direction.UP);
        assertEquals(0, testCharacter.getPositionY());
    }

    @Test
    public void testMoveDown() {
        testCharacter.setPositionY(CHAMBER_HEIGHT - 2);
        testCharacter.move(Direction.DOWN);
        assertEquals(CHAMBER_HEIGHT - 1, testCharacter.getPositionY());
    }
}