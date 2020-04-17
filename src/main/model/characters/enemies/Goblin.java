package model.characters.enemies;

/**
 * Represents a goblin enemy in the dungeon.
 */

public class Goblin extends Enemy {

    // EFFECTS: creates a new goblin at the given x and y position
    public Goblin(int positionX, int positionY) {
        super("Goblin", 70, 15, 10, positionX, positionY);
    }

    // EFFECTS: returns the string representation of this on the board
    @Override
    public String stringRepresentation() {
        return "G";
    }
}
