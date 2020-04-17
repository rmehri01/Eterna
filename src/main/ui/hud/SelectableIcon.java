package ui.hud;

import ui.GameRunner;
import ui.Rectangle;
import ui.Renderer;
import ui.sprites.Sprite;

/**
 * Represents an icon that can be selected by clicking.
 */

public class SelectableIcon extends Icon {

    private GameRunner gameRunner;
    private int tileID;
    public boolean isSelected;

    // EFFECTS: creates a selectable icon that has a tile id, sprite, and rectangle, as well as access to the game
    public SelectableIcon(GameRunner gameRunner, int tileID, Sprite tileSprite, Rectangle rectangle) {
        super(tileSprite, rectangle, true);
        this.gameRunner = gameRunner;
        this.tileID = tileID;
        rectangle.generateGraphics(0xFFDB3D);
    }

    // MODIFIES: this
    // EFFECTS: changes the color of this based on if it is selected
    @Override
    public void update(GameRunner gameRunner) {
        if (tileID == gameRunner.getSelectedTileID()) {
            if (!isSelected) {
                rectangle.generateGraphics(0x67FF3D);
                isSelected = true;
            }
        } else {
            if (isSelected) {
                rectangle.generateGraphics(0xFFDB3D);
                isSelected = false;
            }
        }

    }

    // EFFECTS: renders the background rectangle of this with its sprite on top
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY, Rectangle region) {
        renderer.renderRectangle(rectangle, region, 1, 1, fixed);
        renderer.renderSprite(sprite,
                rectangle.getPositionX() + region.getPositionX() + rectangle.getWidth() / 2 / zoomX,
                rectangle.getPositionY() + region.getPositionY() + rectangle.getHeight() / 2 / zoomY,
                zoomX - 1,
                zoomY - 1,
                fixed);
    }

    // MODIFIES: this
    // EFFECTS: changes the selected tile to be this
    @Override
    public void activate() {
        gameRunner.changeTile(tileID);
    }
}
