package model.items;

import model.characters.Player;

/**
 * Represents a sword that the player can equip for additional damage.
 */

public class Sword extends Item implements Equippable {
    public static final int ATTACK_STAT = 20;

    // EFFECTS: creates a new sword with the given x and y position
    public Sword(int positionX, int positionY) {
        super(positionX, positionY);
    }

    // MODIFIES: player
    // EFFECTS: equips this to the player
    @Override
    public void use(Player player) {
        player.equip(this);
    }

    // EFFECTS: returns the string representation of this on the board
    @Override
    public String stringRepresentation() {
        return "S";
    }

    // EFFECTS: returns the description of this
    @Override
    public String toString() {
        return "Sword";
    }

    // EFFECTS: returns the amount of additional attack this gives
    @Override
    public int getAdditionalAttack() {
        return ATTACK_STAT;
    }

    // EFFECTS: returns the amount of additional defense this gives
    @Override
    public int getAdditionalDefense() {
        return 0;
    }
}
