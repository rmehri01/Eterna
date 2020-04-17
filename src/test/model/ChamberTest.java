package model;

import model.characters.Player;
import model.characters.enemies.Enemy;
import model.items.HealthPotion;
import model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Chamber.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChamberTest {
    Chamber testChamber;
    Player player;
    Item potion;
    Enemy orc;
    Enemy skeleton;
    Enemy slime;

    @BeforeEach
    public void setup() {
        player = new Player("James", 10, 5);
        potion = new HealthPotion(1, 0);
        orc = new Enemy("Orc", 15, 2, 0, 1, 1);
        skeleton = new Enemy("Skeleton", 10, 1, 0, 3, 3);
        slime = new Enemy("Slime", 7, 3, 0, 2, 0);
        testChamber = new Chamber(player);
        testChamber.clearGrid();
    }

    @Test
    public void testConstructor() {
        assertEquals(player, testChamber.getPlayer());
        assertTrue(testChamber.getEnemySet().isEmpty());
        assertTrue(testChamber.getItemSet().isEmpty());
        assertEquals(CHAMBER_HEIGHT - 1, testChamber.getExitLocationY());
        assertEquals(CHAMBER_WIDTH - 1, testChamber.getExitLocationX());
    }

    @Test
    public void testGenerateInteractablesOnSetup() {
        testChamber.generateInteractables();
        testChamber.generateInteractables();
        testChamber.generateInteractables();
        assertFalse(testChamber.getEnemySet().isEmpty());
        assertFalse(testChamber.getItemSet().isEmpty());
    }

    @Test
    public void testGenerateInteractablesDoNotOverlap() {
        for (int i = 0; i < 1000; i++) {
            testChamber.generateInteractables();
        }
        int totalInteractables = testChamber.getEnemySet().size() + testChamber.getItemSet().size();
        assertTrue(totalInteractables <= CHAMBER_WIDTH * CHAMBER_HEIGHT - 2);
    }


    @Test
    public void testAddEnemiesNone() {
        testChamber.addEnemies();
        assertTrue(testChamber.getEnemySet().isEmpty());
    }

    @Test
    public void testAddEntitiesMultiple() {
        testChamber.addEnemies(orc, skeleton);
        testChamber.addItems(potion);
        assertTrue(testChamber.containsEnemy(orc));
        assertTrue(testChamber.containsEnemy(skeleton));
        assertTrue(testChamber.containsItem(potion));
    }

    @Test
    public void testContainsEntityEmptyGrid() {
        assertFalse(testChamber.containsEnemy(skeleton));
        assertFalse(testChamber.containsEnemy(orc));
    }

    @Test
    public void testContainsEntityTrue() {
        testChamber.addEnemies(skeleton);
        assertTrue(testChamber.containsEnemy(skeleton));
        assertFalse(testChamber.containsEnemy(orc));
    }

    @Test
    public void testIsValidPositionTrue() {
        assertTrue(testChamber.isValidPosition(0, 0));
    }

    @Test
    public void testIsValidPositionTooFarLeft() {
        assertFalse(testChamber.isValidPosition(-1, 0));
    }

    @Test
    public void testIsValidPositionTooFarRight() {
        assertFalse(testChamber.isValidPosition(CHAMBER_WIDTH, 0));
    }

    @Test
    public void testIsValidPositionTooFarUp() {
        assertFalse(testChamber.isValidPosition(0, -1));
    }

    @Test
    public void testIsValidPositionTooFarDown() {
        assertFalse(testChamber.isValidPosition(0, CHAMBER_HEIGHT));
    }

    @Test
    public void testMovePlayerIntoItemAndPickUp() {
        testChamber.addItems(potion);
        testChamber.movePlayer(Direction.RIGHT);
        assertFalse(testChamber.containsItem(potion));
        assertTrue(testChamber.getItemSet().isEmpty());
    }

    @Test
    public void testMovePlayerIntoEnemyNotRemoved() {
        testChamber.addEnemies(orc);
        testChamber.movePlayer(Direction.DOWN);
        testChamber.movePlayer(Direction.RIGHT);
        assertEquals(0, player.getPositionX());
        assertEquals(1, player.getPositionY());
        assertTrue(testChamber.containsEnemy(orc));
    }

    @Test
    public void testMovePlayerIntoEnemyRemoved() {
        testChamber.addEnemies(new Enemy("Low Hp Orc", 5, 1, 0, 1, 1));
        testChamber.movePlayer(Direction.DOWN);
        testChamber.movePlayer(Direction.RIGHT);
        assertFalse(testChamber.containsEnemy(orc));
        assertTrue(testChamber.getEnemySet().isEmpty());
    }

    @Test
    public void testMovePlayerIntoExitAndGenerateNewFloor() {
        player.setPositionX(CHAMBER_WIDTH - 2);
        player.setPositionY(CHAMBER_HEIGHT - 1);
        testChamber.movePlayer(Direction.RIGHT);
        assertEquals(2, testChamber.getFloorNumber());
        assertEquals(0, player.getPositionX());
        assertEquals(0, player.getPositionY());
        assertFalse(testChamber.getItemSet().isEmpty());
        assertFalse(testChamber.getEnemySet().isEmpty());
    }

    @Test
    public void testMovePlayerUpEmpty() {
        player.setPositionX(5);
        player.setPositionY(5);
        testChamber.movePlayer(Direction.UP);
        assertEquals(5, player.getPositionX());
        assertEquals(4, player.getPositionY());
    }

    @Test
    public void testMovePlayerNotEmpty() {
        testChamber.movePlayer(Direction.UP);
        assertEquals(0, player.getPositionX());
        assertEquals(0, player.getPositionY());
    }

    @Test
    public void testMovePlayerLeftEmpty() {
        player.setPositionX(5);
        player.setPositionY(5);
        testChamber.movePlayer(Direction.LEFT);
        assertEquals(4, player.getPositionX());
        assertEquals(5, player.getPositionY());
    }

    @Test
    public void testMovePlayerDownEmpty() {
        player.setPositionX(5);
        player.setPositionY(5);
        testChamber.movePlayer(Direction.DOWN);
        assertEquals(5, player.getPositionX());
        assertEquals(6, player.getPositionY());
    }

    @Test
    public void testMovePlayerRightEmpty() {
        player.setPositionX(5);
        player.setPositionY(5);
        testChamber.movePlayer(Direction.RIGHT);
        assertEquals(6, player.getPositionX());
        assertEquals(5, player.getPositionY());
    }

    @Test
    public void testMovePlayerToRightNoExit() {
        player.setPositionX(testChamber.getExitLocationX() - 1);
        player.setPositionY(0);
        testChamber.movePlayer(Direction.RIGHT);
        assertEquals(testChamber.getExitLocationX(), player.getPositionX());
        assertEquals(0, player.getPositionY());
        assertEquals(1, testChamber.getFloorNumber());
    }

    @Test
    public void testMovePlayerDownNoExit() {
        player.setPositionX(0);
        player.setPositionY(testChamber.getExitLocationY() - 1);
        testChamber.movePlayer(Direction.DOWN);
        assertEquals(0, player.getPositionX());
        assertEquals(testChamber.getExitLocationY(), player.getPositionY());
        assertEquals(1, testChamber.getFloorNumber());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerUp() {
        testChamber.addEnemies(skeleton);
        player.setPositionX(3);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(3, skeleton.positionX);
        assertEquals(2, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerDown() {
        testChamber.addEnemies(skeleton);
        player.setPositionX(3);
        player.setPositionY(7);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(3, skeleton.positionX);
        assertEquals(4, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerLeft() {
        testChamber.addEnemies(skeleton);
        player.setPositionY(3);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(2, skeleton.positionX);
        assertEquals(3, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerRight() {
        testChamber.addEnemies(skeleton);
        player.setPositionX(2 + ENEMY_SIGHT_DISTANCE);
        player.setPositionY(3);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(4, skeleton.positionX);
        assertEquals(3, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerOutOfRangeHorizontalShouldNotMove() {
        testChamber.addEnemies(skeleton);
        player.setPositionX(3 + ENEMY_SIGHT_DISTANCE);
        player.setPositionY(3);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(3, skeleton.positionX);
        assertEquals(3, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerOutOfRangeVerticalShouldNotMove() {
        testChamber.addEnemies(skeleton);
        player.setPositionX(3);
        player.setPositionY(3 + ENEMY_SIGHT_DISTANCE);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(3, skeleton.positionX);
        assertEquals(3, skeleton.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerMultipleEnemies() {
        testChamber.addEnemies(skeleton, orc);
        player.setPositionY(2);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(2, skeleton.positionX);
        assertEquals(3, skeleton.positionY);
        assertEquals(0, orc.positionX);
        assertEquals(1, orc.positionY);
    }

    @Test
    public void testMoveEnemiesTowardsPlayerEmpty() {
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(0, testChamber.getEnemySet().size());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerHitsItemShouldDoNothing() {
        testChamber.addItems(potion);
        testChamber.addEnemies(slime);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(2, slime.getPositionX());
        assertEquals(0, slime.getPositionY());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerHitsAnotherEnemyShouldDoNothing() {
        testChamber.addEnemies(slime, orc);
        orc.setPositionY(0);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(2, slime.getPositionX());
        assertEquals(0, slime.getPositionY());
        assertEquals(1, orc.getPositionX());
        assertEquals(0, orc.getPositionY());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerHitsExitShouldDoNothing() {
        testChamber.addEnemies(orc);
        player.setPositionX(testChamber.getExitLocationX() + 1);
        player.setPositionY(testChamber.getExitLocationY());
        orc.setPositionX(testChamber.getExitLocationX() - 1);
        orc.setPositionY(testChamber.getExitLocationY());
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(testChamber.getExitLocationX() - 1, orc.getPositionX());
        assertEquals(testChamber.getExitLocationY(), orc.getPositionY());
    }

    @Test
    public void testMoveEnemiesTowardsBottomDoesntHitExitShouldMove() {
        testChamber.addEnemies(orc);
        player.setPositionX(0);
        player.setPositionY(testChamber.getExitLocationY() + 1);
        orc.setPositionX(0);
        orc.setPositionY(testChamber.getExitLocationY() - 1);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(testChamber.getExitLocationY(), orc.getPositionY());
    }

    @Test
    public void testMoveEnemiesTowardsRightDoesntHitExitShouldMove() {
        testChamber.addEnemies(orc);
        player.setPositionX(testChamber.getExitLocationX() + 1);
        player.setPositionY(0);
        orc.setPositionX(testChamber.getExitLocationX() - 1);
        orc.setPositionY(0);
        testChamber.moveEnemiesTowardsPlayer();
        assertEquals(testChamber.getExitLocationX(), orc.getPositionX());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerAndAttacks() {
        testChamber.addEnemies(orc);
        player.setPositionY(1);
        testChamber.moveEnemiesTowardsPlayer();
        assertNotEquals(10, player.getHealth());
    }

    @Test
    public void testMoveEnemiesTowardsPlayerIllegalEnemyPosition() {
        testChamber.addEnemies(orc);
        orc.setPositionX(0);
        orc.setPositionY(0);
        assertThrows(IllegalArgumentException.class, () -> testChamber.moveEnemiesTowardsPlayer());
    }

    @Test
    public void testGetEntities() {
        testChamber.addEnemies(orc, skeleton);
        testChamber.addItems(potion);
        assertTrue(testChamber.getEntities().contains(orc));
        assertTrue(testChamber.getEntities().contains(skeleton));
        assertTrue(testChamber.getEntities().contains(potion));
    }

}
