package model;

import model.characters.Player;
import model.characters.enemies.Enemy;
import model.characters.enemies.EnemyFactory;
import model.items.HealthPotion;
import model.items.Item;
import model.items.Sword;
import persistence.Saveable;

import java.io.*;
import java.util.*;

/**
 * Represents a single chamber at a certain floor within the dungeon.
 * Has randomly generated enemies and an exit that the player can use to progress.
 */

public class Chamber implements Saveable, Serializable {

    // TODO: randomly generated
    private int exitLocationX;
    private int exitLocationY;
    private Set<Enemy> enemySet;
    private Set<Item> itemSet;
    private Player player;
    private int floorNumber;

    public static final int CHAMBER_WIDTH = 30;
    public static final int CHAMBER_HEIGHT = 20;
    public static final int MAX_ITEM_SPAWN = 15;
    public static final int ENEMY_SIGHT_DISTANCE = 5;
    public static final double[] CHANCES = {0.55, 0.4, 0.05};

    // EFFECTS: creates a new chamber with a player and a grid of interactives
    public Chamber(Player player) {
        enemySet = new HashSet<>();
        itemSet = new HashSet<>();
        this.player = player;
        exitLocationX = CHAMBER_WIDTH - 1;
        exitLocationY = CHAMBER_HEIGHT - 1;
        floorNumber = 1;
        generateInteractables();
    }

    // getters
    public int getExitLocationX() {
        return exitLocationX;
    }

    public int getExitLocationY() {
        return exitLocationY;
    }

    public Set<Enemy> getEnemySet() {
        return enemySet;
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }

    public Player getPlayer() {
        return player;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    // EFFECTS: returns a list of all entities in the chamber
    public Set<Entity> getEntities() {
        Set<Entity> entities = new HashSet<>();
        entities.addAll(enemySet);
        entities.addAll(itemSet);
        return entities;
    }

    // setters
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    // MODIFIES: this
    // EFFECTS: adds an arbitrary amount of given enemies to the chamber
    public void addEnemies(Enemy... enemies) {
        enemySet.addAll(Arrays.asList(enemies));
    }

    // MODIFIES: this
    // EFFECTS: adds an arbitrary amount of given items to the chamber
    public void addItems(Item... items) {
        itemSet.addAll(Arrays.asList(items));
    }

    // EFFECTS: returns true if the given enemy is in the chamber
    public boolean containsEnemy(Enemy enemy) {
        return enemySet.contains(enemy);
    }

    // EFFECTS: returns true if the given item is in the chamber
    public boolean containsItem(Item item) {
        return itemSet.contains(item);
    }

    // MODIFIES: this
    // EFFECTS: if the space is open, move player
    //          if the space has an item, move to right and pick up the item
    //          if the space has an enemy, attack the enemy and do not move
    //          if the direction is not one of "wasd", do nothing
    public void movePlayer(Direction direction) {
        int playerX = player.getPositionX();
        int playerY = player.getPositionY();
        switch (direction) {
            case UP:
                handlePlayerInteraction(playerX, playerY - 1, direction);
                break;
            case LEFT:
                handlePlayerInteraction(playerX - 1, playerY, direction);
                break;
            case DOWN:
                handlePlayerInteraction(playerX, playerY + 1, direction);
                break;
            case RIGHT:
            default:
                handlePlayerInteraction(playerX + 1, playerY, direction);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves all the enemies towards the player if space is open
    //          if an enemy moves into the player, attack instead of moving
    //          if an enemy moves into another interactable, do nothing
    public void moveEnemiesTowardsPlayer() {
        for (Enemy enemy : enemySet) {
            int enemyX = enemy.getPositionX();
            int enemyY = enemy.getPositionY();
            int playerX = player.getPositionX();
            int playerY = player.getPositionY();
            if (Math.abs(playerX - enemyX) < ENEMY_SIGHT_DISTANCE
                    && Math.abs(playerY - enemyY) < ENEMY_SIGHT_DISTANCE) {
                if (enemyX < playerX) {
                    handleEnemyInteraction(enemy, enemyX + 1, enemyY, Direction.RIGHT);
                } else if (enemyX > playerX) {
                    handleEnemyInteraction(enemy, enemyX - 1, enemyY, Direction.LEFT);
                } else if (enemyY < playerY) {
                    handleEnemyInteraction(enemy, enemyX, enemyY + 1, Direction.DOWN);
                } else if (enemyY > playerY) {
                    handleEnemyInteraction(enemy, enemyX, enemyY - 1, Direction.UP);
                } else {
                    throw new IllegalArgumentException("Enemy should not be on top of the player.");
                }
            }
        }
    }

    // MODIFIES: this, enemy
    // EFFECTS: if given position is not another interactable, either enemy attacks player or moves to that location
    private void handleEnemyInteraction(Enemy enemy, int newEnemyX, int newEnemyY, Direction dir) {
        if (getEnemyAtPlace(newEnemyX, newEnemyY) == null
                && getItemAtPlace(newEnemyX, newEnemyY) == null
                && !(newEnemyX == exitLocationX && newEnemyY == exitLocationY)) {
            if (player.getPositionX() == newEnemyX && player.getPositionY() == newEnemyY) {
                enemy.attack(player);
            } else {
                enemy.move(dir);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if the new position is valid, either interacts with an interactive or moves to that location
    //          does nothing if position is not valid
    private void handlePlayerInteraction(int newPlayerX, int newPlayerY, Direction dir) {
        if (isValidPosition(newPlayerX, newPlayerY)) {
            Enemy enemyAtPlace = getEnemyAtPlace(newPlayerX, newPlayerY);
            Item itemAtPlace = getItemAtPlace(newPlayerX, newPlayerY);
            if (enemyAtPlace != null) {
                enemyAtPlace.interact(player);
                if (enemyAtPlace.shouldRemove()) {
                    enemySet.remove(enemyAtPlace);
                }
            } else if (itemAtPlace != null) {
                itemAtPlace.interact(player);
                itemSet.remove(itemAtPlace);
            } else {
                player.move(dir);
                handleExit();
            }
        }
    }

    // EFFECTS: returns the item at the specified x and y position, null if not found
    private Item getItemAtPlace(int x, int y) {
        for (Item item : itemSet) {
            if (item.getPositionX() == x && item.getPositionY() == y) {
                return item;
            }
        }
        return null;
    }

    // EFFECTS: returns the enemy at the specified x and y position, null if not found
    private Enemy getEnemyAtPlace(int x, int y) {
        for (Enemy enemy : enemySet) {
            if (enemy.getPositionX() == x && enemy.getPositionY() == y) {
                return enemy;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: if the player reaches the exit, go to next floor and generate new enemies
    private void handleExit() {
        if (player.getPositionX() == exitLocationX && player.getPositionY() == exitLocationY) {
            clearGrid();
            floorNumber++;
            player.setPositionX(0);
            player.setPositionY(0);
            EventLogger.addToLogs("Welcome to level " + floorNumber + "!");
            generateInteractables();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all interactables from this
    public void clearGrid() {
        enemySet.clear();
        itemSet.clear();
    }

    // EFFECTS: returns true if the position is a valid one given the current chamber, false otherwise
    public boolean isValidPosition(int x, int y) {
        return x >= 0
                && x < CHAMBER_WIDTH
                && y >= 0
                && y < CHAMBER_HEIGHT;
    }

    // EFFECTS: saves the state of this to the data folder, if data folder doesn't exist, creates it
    @Override
    public void save() throws IOException {
        File directory = new File("data/");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File saveFile = new File("data/" + player.getName() + "floor" + floorNumber + ".data");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
        oos.writeObject(this);
        oos.close();
    }

    // MODIFIES: this
    // EFFECTS: adds randomly generated interactables to the chamber
    protected void generateInteractables() {
        Random random = new Random();
        for (int i = 0; i < MAX_ITEM_SPAWN; i++) {
            int randX = random.nextInt(CHAMBER_WIDTH);
            int randY = random.nextInt(CHAMBER_HEIGHT);
            Runnable[] runnables = new Runnable[3];
            runnables[0] = () -> {
                if (isPositionEmpty(randX, randY)) {
                    itemSet.add(new HealthPotion(randX, randY));
                }
            };
            runnables[1] = () -> {
                if (isPositionEmpty(randX, randY)) {
                    enemySet.add(new EnemyFactory().getRandomEnemy(floorNumber, randX, randY));
                }
            };
            runnables[2] = () -> {
                if (isPositionEmpty(randX, randY)) {
                    itemSet.add(new Sword(randX, randY));
                }
            };
            runnables[RandomGenerator.chooseFromProbabilities(CHANCES)].run();
        }

    }

    // EFFECTS: returns true if there is the position is empty
    //          a position is empty if it does not contain a player, exit, enemy, or item
    private boolean isPositionEmpty(int x, int y) {
        return getItemAtPlace(x, y) == null
                && getEnemyAtPlace(x, y) == null
                && !(x == player.getPositionX() && y == player.getPositionY())
                && !(x == exitLocationX && y == exitLocationY);
    }
}
