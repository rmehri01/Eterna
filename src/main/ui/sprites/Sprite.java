package ui.sprites;

/**
 * Represents a sprite with a collection of pixels.
 */

public class Sprite {
    protected int width;
    protected int height;
    protected int[] pixels;

    // EFFECTS: creates a new sprite with the given sprite sheet, starting x and y positions, and width and height
    public Sprite(SpriteSheet spriteSheet, int startX, int startY, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        pixels = spriteSheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);
    }

    // EFFECTS: creates an empty sprite
    public Sprite() {
    }

    // getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
}
