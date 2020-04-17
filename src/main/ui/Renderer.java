package ui;

import ui.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Represents a utility for rendering pixels to the screen.
 */

public class Renderer {
    private BufferedImage view;
    private Rectangle camera;
    private int[] pixels;

    // EFFECTS: creates a renderer with given viewport width and height
    public Renderer(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        camera = new Rectangle(0, 0, width, height);
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    }

    // getters
    public Rectangle getCamera() {
        return camera;
    }

    // EFFECTS: renders the empty view to the screen
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    // MODIFIES: this
    // EFFECTS: renders the given sprite at the given x and y positions and zoom, taking into account if it is fixed
    public void renderSprite(Sprite sprite, int positionX, int positionY, int zoomX, int zoomY, boolean fixed) {
        renderArray(sprite.getPixels(),
                sprite.getWidth(),
                sprite.getHeight(),
                positionX, positionY,
                zoomX, zoomY, fixed);
    }

    // MODIFIES: this
    // EFFECTS: renders the given rectangle at the given x and y positions and zoom, taking into account if it is fixed
    public void renderRectangle(Rectangle rectangle, Rectangle offsetRectangle, int zoomX, int zoomY, boolean fixed) {
        int[] rectanglePixels = rectangle.getPixels();
        if (rectanglePixels != null) {
            renderArray(rectanglePixels,
                    rectangle.getWidth(),
                    rectangle.getHeight(),
                    rectangle.getPositionX() + offsetRectangle.getPositionX(),
                    rectangle.getPositionY() + offsetRectangle.getPositionY(),
                    zoomX, zoomY, fixed);
        } else {
            System.out.println("Rectangle has no generated pixels!");
        }
    }

    // MODIFIES: this
    // EFFECTS: puts imagePixels inside the pixels of this using the render height and width,
    //          as well as x and y positions, zoom, and accounts for fixed pixels
    public void renderArray(int[] imagePixels,
                            int renderWidth,
                            int renderHeight,
                            int positionX,
                            int positionY,
                            int zoomX,
                            int zoomY,
                            boolean fixed) {
        for (int y = 0; y < renderHeight; y++) {
            for (int x = 0; x < renderWidth; x++) {
                for (int zoomYPosition = 0; zoomYPosition < zoomY; zoomYPosition++) {
                    for (int zoomXPosition = 0; zoomXPosition < zoomX; zoomXPosition++) {
                        int currentPixel = imagePixels[y * renderWidth + x];
                        int pixelX = positionX + (x * zoomX) + zoomXPosition;
                        int pixelY = positionY + (y * zoomY) + zoomYPosition;
                        setPixel(currentPixel, pixelX, pixelY, fixed);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the pixel at the given x and y to the one given, and takes into account if it is fixed
    private void setPixel(int pixel, int x, int y, boolean fixed) {
        int pixelIndex = 0;
        if (!fixed) {
            if (x >= camera.getPositionX()
                    && y >= camera.getPositionY()
                    && x <= camera.getPositionX() + camera.getWidth()
                    && y <= camera.getPositionY() + camera.getWidth()) {
                pixelIndex = (x - camera.getPositionX()) + (y - camera.getPositionY()) * view.getWidth();
            }
        } else {
            if (x >= 0 && y >= 0 && x <= camera.getWidth() && y <= camera.getHeight()) {
                pixelIndex = x + y * view.getWidth();
            }
        }

        if (pixelIndex < pixels.length && pixel != GameRunner.ALPHA) {
            pixels[pixelIndex] = pixel;
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all pixels from this
    public void clear() {
        Arrays.fill(pixels, 0);
    }
}
