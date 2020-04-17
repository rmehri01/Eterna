package model.items;

import model.characters.Player;

/**
 * A potion that the player can use to heal.
 */

public class HealthPotion extends Item {
    public static final int HEAL_AMOUNT = 50;

    // EFFECTS: creates a new health potion at the given position
    public HealthPotion(int positionX, int positionY) {
        super(positionX, positionY);
    }

    // EFFECTS: returns the string representation of the health potion on the board
    @Override
    public String stringRepresentation() {
        return "P";
    }

    // MODIFIES: player
    // EFFECTS: heals the player by heal amount
    @Override
    public void use(Player player) {
        player.heal(HEAL_AMOUNT);
    }

    // EFFECTS: produces the string description of the health potion
    @Override
    public String toString() {
        return "Health Potion";
    }
}
