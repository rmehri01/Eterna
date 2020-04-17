package ui;

import model.Chamber;
import model.Direction;
import model.EventLogger;
import ui.sprites.PlayerSprite;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents a listener for keyboard actions in the game.
 */

public class KeyEventListener implements KeyListener {
    private GameRunner gameRunner;
    private Map map;

    // EFFECTS: creates a key event listener with a reference to the game
    public KeyEventListener(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        map = gameRunner.getMap();
    }

    // MODIFIES: this
    // EFFECTS: sends actions to the game based on the key pressed
    //          WASD keys move the player, E uses an item, and O opens the option menu
    @Override
    public void keyPressed(KeyEvent e) {
        Chamber chamber = map.getChamber();
        PlayerSprite playerSprite = gameRunner.getPlayerSprite();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                updateChamber(chamber, playerSprite, Direction.UP);
                break;
            case KeyEvent.VK_A:
                updateChamber(chamber, playerSprite, Direction.LEFT);
                break;
            case KeyEvent.VK_S:
                updateChamber(chamber, playerSprite, Direction.DOWN);
                break;
            case KeyEvent.VK_D:
                updateChamber(chamber, playerSprite, Direction.RIGHT);
                break;
            case KeyEvent.VK_E:
                handleUseItem();
                break;
            case KeyEvent.VK_O:
                showOptionPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: uses the item that is currently selected in game
    private void handleUseItem() {
        try {
            map.getChamber().getPlayer().useItem(gameRunner.getSelectedTileID());
        } catch (IllegalArgumentException err) {
            EventLogger.addToLogs(err.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the option panel and allows user to save or load game
    private void showOptionPanel() {
        String response = JOptionPane.showInputDialog(gameRunner,
                "Enter \"save\" to save or \"load\" to load.",
                "Eterna",
                JOptionPane.PLAIN_MESSAGE);
        switch (response.toLowerCase()) {
            case "save":
                gameRunner.handleSaveGame();
                break;
            case "load":
                gameRunner.handleLoadGame();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the player in the given direction and the player sprite correspondingly, then moves enemies
    private void updateChamber(Chamber chamber, PlayerSprite playerSprite, Direction direction) {
        chamber.movePlayer(direction);
        playerSprite.setDirection(direction);
        playerSprite.updateDirection();
        map.getChamber().moveEnemiesTowardsPlayer();
    }

    // EFFECTS: none, not used
    @Override
    public void keyReleased(KeyEvent e) {

    }

    // EFFECTS: none, not used
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
