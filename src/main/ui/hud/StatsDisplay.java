package ui.hud;

import model.EventLogger;
import model.characters.Player;
import model.items.Equippable;
import ui.UserInterfaceObject;
import ui.GameRunner;
import ui.Rectangle;
import ui.Renderer;
import ui.sprites.Sprite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player statistics display.
 */

public class StatsDisplay implements UserInterfaceObject {

    private Sprite backgroundSprite;
    private List<Icon> inventoryButtons;
    private ui.Rectangle rectangle;
    private boolean fixed;
    private String lastSeenMessage = "";

    // EFFECTS: creates a stats display with given background sprite, with x and y positions, and whether it is fixed
    public StatsDisplay(Sprite backgroundSprite, int x, int y, boolean fixed) {
        this.backgroundSprite = backgroundSprite;
        this.inventoryButtons = new ArrayList<>();
        this.fixed = fixed;
        rectangle = new ui.Rectangle();
        rectangle.setPositionX(x);
        rectangle.setPositionY(y);
        if (backgroundSprite != null) {
            rectangle.setWidth(backgroundSprite.getWidth());
            rectangle.setHeight(backgroundSprite.getHeight());
        }
    }

    // EFFECTS: creates a stats display with no background sprite, with x and y positions, and whether it is fixed
    public StatsDisplay(int x, int y, boolean fixed) {
        this(null, x, y, fixed);
    }

    // getters
    public List<Icon> getInventoryButtons() {
        return inventoryButtons;
    }

    // MODIFIES: this
    // EFFECTS: shows the player stats, such as health, armor, attack damage, and inventory to the screen
    public void showPlayerStats(Player player, Graphics graphics) {
        setUpFont(graphics);

        int posX = rectangle.getPositionX();
        int posY = rectangle.getPositionY();

        String currentEvent = EventLogger.getLogsString();
        if (!currentEvent.isEmpty()) {
            lastSeenMessage = currentEvent;
        }

        int y = 75;
        String[] lines = lastSeenMessage.split(EventLogger.SPLIT_STRING);
        for (String line : lines) {
            graphics.drawString(line, posX, y);
            y += 50;
        }

        graphics.setFont(graphics.getFont().deriveFont(12f));

        String name = player.getName();
        graphics.drawString(name + (name.endsWith("s") ? "' Stats:" : "'s Stats:"), posX, posY - 175);
        Equippable equipped = player.getEquipped();
        graphics.drawString("Equipped Items: " + equipped, posX, posY - 125);
        graphics.drawString("Health: " + player.getHealth() + "/" + player.getMaxHealth(), posX, posY - 75);
        graphics.drawString("Armor: " + player.getArmor() + " + " + equipped.getAdditionalDefense(),
                posX + 190, posY - 75);
        graphics.drawString("Attack Damage: " + player.getAttackDamage() + " + " + equipped.getAdditionalAttack(),
                posX, posY - 25);
    }

    // MODIFIES: graphics
    // EFFECTS: sets up the font and size of graphics
    private void setUpFont(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        try {
            File fontFile = new File("assets/PixelFJVerdana12pt.ttf");
            Font pixelVerdana = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(15f);
            graphics.setFont(pixelVerdana);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: renders the stats and all buttons in the inventory
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY) {
        if (backgroundSprite != null) {
            renderer.renderSprite(backgroundSprite,
                    rectangle.getPositionX(),
                    rectangle.getPositionY(),
                    zoomX, zoomY, fixed);
        }

        inventoryButtons.forEach(button -> button.render(renderer, zoomX, zoomY, rectangle));
    }

    // MODIFIES: this
    // EFFECTS: updates all inventory buttons
    @Override
    public void update(GameRunner gameRunner) {
        for (Icon button : inventoryButtons) {
            button.update(gameRunner);
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if the stats display triggers a mouse event and activates the event, false otherwise
    @Override
    public boolean handleMouseClick(ui.Rectangle mouseRectangle, ui.Rectangle camera, int zoomX, int zoomY) {
        boolean stopped = false;
        if (!fixed) {
            int newX = mouseRectangle.getPositionX() + camera.getPositionX();
            int newY = mouseRectangle.getPositionY() + camera.getPositionY();
            mouseRectangle = new ui.Rectangle(newX, newY, 1, 1);
        } else {
            mouseRectangle = new Rectangle(mouseRectangle.getPositionX(), mouseRectangle.getPositionY(), 1, 1);
        }

        if (rectangle.getHeight() == 0 || rectangle.getWidth() == 0 || mouseRectangle.intersects(rectangle)) {
            mouseRectangle.setPositionX(mouseRectangle.getPositionX() - rectangle.getPositionX());
            mouseRectangle.setPositionY(mouseRectangle.getPositionY() - rectangle.getPositionY());
            for (Icon button : inventoryButtons) {
                boolean result = button.handleMouseClick(mouseRectangle, camera, zoomX, zoomY);
                if (!stopped) {
                    stopped = result;
                }
            }
        }
        return stopped;
    }

}
