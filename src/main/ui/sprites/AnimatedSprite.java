package ui.sprites;

import ui.UserInterfaceObject;
import ui.GameRunner;
import ui.Rectangle;
import ui.Renderer;

/**
 * Represents a sprite with animations over time.
 */

public class AnimatedSprite extends Sprite implements UserInterfaceObject {
    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter = 0;

    private int startSprite = 0;
    private int endSprite;

    // EFFECTS: creates a new animated sprite with a sprite sheet and a given speed
    public AnimatedSprite(SpriteSheet spriteSheet, int speed) {
        Sprite[][] loadedSprites = spriteSheet.getLoadedSprites();
        sprites = new Sprite[loadedSprites.length * loadedSprites[0].length];
        for (int y = 0; y < loadedSprites.length; y++) {
            System.arraycopy(loadedSprites[y], 0, sprites, y * loadedSprites[0].length, loadedSprites[0].length);
        }
        this.speed = speed;
        endSprite = sprites.length - 1;
    }

    // getters
    @Override
    public int getWidth() {
        return sprites[currentSprite].getWidth();
    }

    @Override
    public int getHeight() {
        return sprites[currentSprite].getHeight();
    }

    @Override
    public int[] getPixels() {
        return sprites[currentSprite].getPixels();
    }

    // EFFECTS: none, as it is not directly rendered, rather each individual sprite is
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY) {

    }

    // MODIFIES: this
    // EFFECTS: updates the counter and sprite according to speed
    @Override
    public void update(GameRunner gameRunner) {
        counter++;
        if (counter >= speed) {
            counter = 0;
            incrementSprite();
        }
    }

    // EFFECTS: always false, since there is no effect for being clicked
    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: updates the current sprite, if it is out of range of end sprite resets to the start sprite
    private void incrementSprite() {
        currentSprite++;
        if (currentSprite >= endSprite) {
            currentSprite = startSprite;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the start and end sprite to the given values and resets the counter
    public void setAnimationRange(int startSprite, int endSprite) {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();
    }

    // MODIFIES: this
    // EFFECTS: resets the counter to 0 and sprite to start sprite
    private void reset() {
        counter = 0;
        currentSprite = startSprite;
    }
}
