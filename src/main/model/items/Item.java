package model.items;

import model.Entity;
import model.EventLogger;
import model.Interactable;
import model.characters.Player;

import java.io.Serializable;

/**
 * Represents an item that the player can interact with and use.
 */

// TODO: might want to separate consumable and usable items

public abstract class Item extends Entity implements Interactable, Serializable {
    // EFFECTS: creates an item at the given position
    public Item(int positionX, int positionY) {
        super(positionX, positionY);
    }

    // EFFECTS: creates a certain effect on the player (heal, poison, etc.)
    public abstract void use(Player player);

    // MODIFIES: player
    // EFFECTS: adds this to the player's inventory
    @Override
    public void interact(Player player) {
        EventLogger.addToLogs(player.getName() + " has picked up a " + toString() + ".");
        player.addItem(this);
    }

}
