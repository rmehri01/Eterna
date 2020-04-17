package model.characters;

import model.Direction;
import model.Entity;
import model.EventLogger;

/**
 * Represents a single character within the game.
 * A character is anything living thing that has a name, health, attack, and armor.
 */

public abstract class Character extends Entity {
    public static final double ARMOR_REDUCTION_FACTOR = 0.3;

    protected String name;
    protected int maxHealth;
    protected int health;
    protected int attackDamage;
    protected int armor;
    protected boolean alive;

    // EFFECTS: sets up a character with name, max health, attack damage, and armor at a given position
    public Character(String name, int maxHealth, int attackDamage, int armor, int positionX, int positionY) {
        super(positionX, positionY);
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackDamage = attackDamage;
        this.armor = armor;
        this.alive = true;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getArmor() {
        return armor;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    // MODIFIES: this
    // EFFECTS: reduces the character's health by damage, and if health falls to 0 or below the character dies
    //          does nothing if the character is already dead
    public void takeDamage(int damage) {
        if (health > 0) {
            int damageAfterArmor = Math.max(0, (int) (damage - armor * ARMOR_REDUCTION_FACTOR));
            health -= damageAfterArmor;
            EventLogger.addToLogs(name + " took " + damageAfterArmor + " damage!");
            if (health <= 0) {
                alive = false;
                EventLogger.addToLogs(name + " has been slain!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: heals the character by amount, caps at max health
    public void heal(int amount) {
        int totalAfterHeal = health + amount;
        health = Math.min(totalAfterHeal, maxHealth);
        EventLogger.addToLogs(name + " has healed for " + amount + ".");
    }

    // MODIFIES: other
    // EFFECTS: damages the other character by their attack damage
    public void attack(Character other) {
        other.takeDamage(attackDamage);
    }

    // MODIFIES: this
    // EFFECTS: moves the player in the specified direction, does nothing otherwise
    public void move(Direction direction) {
        switch (direction) {
            case UP:
                positionY--;
                break;
            case LEFT:
                positionX--;
                break;
            case DOWN:
                positionY++;
                break;
            case RIGHT:
            default:
                positionX++;
                break;
        }
    }
}
