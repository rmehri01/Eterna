package model.characters.enemies;

/**
 * Represents the werewolf enemy in the game.
 */

public class Ghost extends Enemy {

    // EFFECTS: creates a new werewolf at the given x and y location
    public Ghost(int positionX, int positionY) {
        super("Ghost", 100, 22, 15, positionX, positionY);
    }

    // EFFECTS: returns the string representation of this on the board
    @Override
    public String stringRepresentation() {
        return "H";
    }
}
