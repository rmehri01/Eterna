package ui;

/**
 * Represents an object in the user interface.
 */

public interface UserInterfaceObject {

    // EFFECTS: renders the object to the screen
    void render(Renderer renderer, int zoomX, int zoomY);

    // MODIFIES: this
    // EFFECTS: updates the state of the object with reference to the game
    void update(GameRunner gameRunner);

    // EFFECTS: returns true if a mouse based event has been triggered, false otherwise
    boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY);
}
