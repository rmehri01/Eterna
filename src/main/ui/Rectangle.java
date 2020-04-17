package ui;

import java.util.Arrays;

/**
 * Anything with x and y position, as well as width and height.
 * Can represent the camera (user viewport), player, tiles, etc.
 */

public class Rectangle {
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private int[] pixels;

    // EFFECTS: creates a new rectangle with given x and y positions, as well as width and height
    public Rectangle(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    // EFFECTS: creates a new empty rectangle
    public Rectangle() {
        this(0, 0, 0, 0);
    }

    // getters
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    // setters
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // MODIFIES: this
    // EFFECTS: fills this with the given color
    public void generateGraphics(int color) {
        pixels = new int[width * height];
        Arrays.fill(pixels, color);
    }

    // MODIFIES: this
    // EFFECTS: makes an outline around this with the given color of width borderWidth
    public void generateGraphics(int borderWidth, int color) {
        generateGraphics(GameRunner.ALPHA);

        for (int y = 0; y < borderWidth; y++) {
            for (int x = 0; x < width; x++) {
                // top border
                pixels[x + y * width] = color;
                // bottom border
                pixels[(width * height - borderWidth * width) + x + y * width] = color;
            }
        }

        for (int x = 0; x < borderWidth; x++) {
            for (int y = 0; y < height; y++) {
                // left border
                pixels[x + y * width] = color;
                // right border
                pixels[(width - borderWidth) + x + y * width] = color;
            }
        }

    }

    // EFFECTS: returns true if other intersects with this, false otherwise
    public boolean intersects(Rectangle other) {
        if (positionX > other.getPositionX() + other.getWidth() || other.getPositionX() > positionX + width) {
            return false;
        }
        if (positionY > other.getPositionY() + other.getHeight() || other.getPositionY() > positionY + height) {
            return false;
        }

        return true;
    }
}
