package model;

import java.io.Serializable;

/**
 * An entity is anything in the game that has a position.
 * Examples are the player, enemies, items, etc.
 */

public abstract class Entity implements Serializable {
    // (0, 0) is the top left with positive x being right and positive y being down
    protected int positionX;
    protected int positionY;

    // EFFECTS: creates a new entity that has the give x and y positions
    public Entity(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // getters
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    // setters
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
