package ui.sprites;

import java.awt.image.BufferedImage;

/**
 * Represents a collection of sprites on a sheet.
 */

public class SpriteSheet {
    private BufferedImage image;
    private Sprite[][] loadedSprites;
    public final int scaledSizeX;
    public final int scaledSizeY;

    // EFFECTS: creates a new sprite sheet with an image and the size of each individual sprite
    public SpriteSheet(BufferedImage sheetImage, int spriteSizeX, int spriteSizeY) {
        this.image = sheetImage;

        int sheetSizeY = sheetImage.getHeight();
        int sheetSizeX = sheetImage.getWidth();
        scaledSizeY = sheetSizeY / spriteSizeY;
        scaledSizeX = sheetSizeX / spriteSizeX;

        loadedSprites = new Sprite[scaledSizeY][scaledSizeX];
        for (int y = 0; y < sheetSizeY; y += spriteSizeY) {
            for (int x = 0; x < sheetSizeX; x += spriteSizeX) {
                loadedSprites[y / spriteSizeY][x / spriteSizeX] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
            }
        }
    }

    // getters
    public Sprite[][] getLoadedSprites() {
        return loadedSprites;
    }

    public BufferedImage getImage() {
        return image;
    }

    // EFFECTS: returns the sprite at the given x and y position on the sheet
    public Sprite getSprite(int x, int y) {
        if (loadedSprites != null) {
            if (y < scaledSizeY && x < scaledSizeX) {
                return loadedSprites[y][x];
            } else {
                System.out.println("Sprite ID out of bounds!");
            }
        } else {
            System.out.println("Sprites not loaded yet!");
        }

        return null;
    }
}
