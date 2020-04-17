package model.characters;

import model.EventLogger;
import model.items.Equippable;
import model.items.Fists;
import model.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player.
 * The player has an inventory of items they can use.
 */

public class Player extends Character {
    // TODO: gold system and purchasing
    private List<Item> inventory;
    private Equippable equipped;

    public static final int MAX_INVENTORY_SIZE = 6;

    // EFFECTS: creates a new player with the given name, health, and attackDamage, as well as having no armor or items
    public Player(String name, int maxHealth, int attackDamage) {
        super(name, maxHealth, attackDamage, 0, 0, 0);
        this.armor = 0;
        this.inventory = new ArrayList<>();
        equipped = new Fists();
    }

    // getters
    public List<Item> getInventory() {
        return inventory;
    }

    public Equippable getEquipped() {
        return equipped;
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the player's inventory or does nothing if full
    public void addItem(Item item) {
        if (inventory.size() < MAX_INVENTORY_SIZE) {
            inventory.add(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: uses the item at slot slotNumber, if there is no item do nothing
    public void useItem(int slotNumber) throws IllegalArgumentException {
        if (slotNumber > inventory.size()) {
            String errorMessage = String.format("There is no item at slot %d!", slotNumber);
            throw new IllegalArgumentException(errorMessage);
        }
        Item item = inventory.get(slotNumber - 1);
        item.use(this);
        inventory.remove(item);
    }

    // MODIFIES: other
    // EFFECTS: deals base damage plus damage of equipped to other
    @Override
    public void attack(Character other) {
        int damageWithAdditional = attackDamage + equipped.getAdditionalAttack();
        other.takeDamage(damageWithAdditional);
    }

    // MODIFIES: this
    // EFFECTS: equips the given item to this
    public void equip(Equippable equippable) {
        equipped = equippable;
        EventLogger.addToLogs(name + " has equipped " + equippable + ".");
    }

}
