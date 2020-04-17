package model.characters.enemies;

/**
 * Represents a sewer rat within the dungeon.
 */

public class Slime extends Enemy {

    // EFFECTS: creates a new sewer rat at the given x and y location
    public Slime(int positionX, int positionY) {
        super("Slime", 40, 10, 0, positionX, positionY);
    }

    // EFFECTS: returns the string representation of this on the board
    @Override
    public String stringRepresentation() {
        return "L";
    }
}
