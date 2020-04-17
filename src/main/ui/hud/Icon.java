package ui.hud;

import ui.Rectangle;
import ui.Renderer;
import ui.UserInterfaceObject;
import ui.sprites.Sprite;

/**
 * Represents an icon on the player's HUD.
 */

public abstract class Icon implements UserInterfaceObject {

    protected Sprite sprite;
    protected Rectangle rectangle;
    protected boolean fixed;

    // EFFECTS: creates a new icon with the given sprite, rectangle representing its area, and if it is fixed
    public Icon(Sprite sprite, Rectangle rectangle, boolean fixed) {
        this.sprite = sprite;
        this.rectangle = rectangle;
        this.fixed = fixed;
    }

    // EFFECTS: renders the icon sprite to the screen
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY) {
        renderer.renderSprite(sprite, rectangle.getPositionX(), rectangle.getPositionY(), zoomX, zoomY, fixed);
    }

    public void render(Renderer renderer, int zoomX, int zoomY, Rectangle region) {
        renderer.renderSprite(sprite,
                rectangle.getPositionX() + region.getPositionX(),
                rectangle.getPositionY() + region.getPositionY(),
                zoomX, zoomY, fixed);
    }

    // EFFECTS: returns true if the icon is clicked, thus activating it, false otherwise
    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY) {
        if (mouseRectangle.intersects(rectangle)) {
            activate();
            return false;
        }
        return false;
    }

    // EFFECTS: creates a special effect based on the icon
    public abstract void activate();
}
