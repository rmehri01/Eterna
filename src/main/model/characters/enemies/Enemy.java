package model.characters.enemies;

import model.Interactable;
import model.characters.Character;
import model.characters.Player;

/**
 * An enemy that appears in the game.
 * Tries to kill the player.
 */

// TODO: fits more of an abstract class now
// TODO: can add specific behaviour to each enemy now
public class Enemy extends Character implements Interactable {

    // EFFECTS: creates a new enemy with the given name, health, attackDamage, armor, and position
    public Enemy(String name, int maxHealth, int attackDamage, int armor, int positionX, int positionY) {
        super(name, maxHealth, attackDamage, armor, positionX, positionY);
    }

    // MODIFIES: this
    // EFFECTS: the player attacks this
    @Override
    public void interact(Player player) {
        player.attack(this);
    }

    // EFFECTS: returns true if this is not alive, false otherwise
    public boolean shouldRemove() {
        return !isAlive();
    }

    // EFFECTS: returns the string representation of this for the board
    @Override
    public String stringRepresentation() {
        return "λ";
    }
}
