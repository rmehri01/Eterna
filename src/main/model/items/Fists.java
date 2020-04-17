package model.items;

/**
 * Represents the equippable the player starts with.
 * Gives no additional stats.
 */

public class Fists implements Equippable {

    // EFFECTS: returns 0, no additional attack
    @Override
    public int getAdditionalAttack() {
        return 0;
    }

    // EFFECTS: returns 0, no additional defense
    @Override
    public int getAdditionalDefense() {
        return 0;
    }

    // EFFECTS: returns the description of this
    @Override
    public String toString() {
        return "Fists";
    }
}
