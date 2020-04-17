package model.characters.enemies;

/**
 * Represents a dragon enemy found in the dungeon.
 */

public class Demon extends Enemy {

    // EFFECTS: creates a new dragon with the given x and y position
    public Demon(int positionX, int positionY) {
        super("Demon", 200, 40, 30, positionX, positionY);
    }

    // EFFECTS: returns the string representation of this on the board
    @Override
    public String stringRepresentation() {
        return "D";
    }
}
