package model;

import model.characters.Player;

/**
 * Represents anything that interacts with the player on the board.
 */

public interface Interactable {

    // MODIFIES: player, this
    // EFFECTS: defines how the interactive should interact with the player
    void interact(Player player);

    // EFFECTS: returns the single character string representation for the interactable
    String stringRepresentation();

    // EFFECTS: returns the x position of the interactive
    int getPositionX();

    // EFFECTS: returns the y position of the interactive
    int getPositionY();
}
