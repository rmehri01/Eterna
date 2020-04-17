package model.items;

import java.io.Serializable;

/**
 * Represents anything that can be equipped to the player.
 */

public interface Equippable extends Serializable {

    // EFFECTS: returns the amount of attack the item gives the player
    int getAdditionalAttack();

    // EFFECTS: returns the amount of defense the item gives the player
    int getAdditionalDefense();
}
