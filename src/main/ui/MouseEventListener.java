package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Represents a listener for mouse actions in the game.
 */

public class MouseEventListener implements MouseListener, MouseMotionListener {
    private GameRunner gameRunner;

    // EFFECTS: creates a mouse listener with a reference to the game
    public MouseEventListener(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }

    // EFFECTS: none, not used
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // MODIFIES: this
    // EFFECTS: if the left mouse button is clicked, triggers a game event at the given x and y mouse positions
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            gameRunner.leftClick(e.getX(), e.getY());
        }
    }

    // EFFECTS: none, not used
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // EFFECTS: none, not used
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    // EFFECTS: none, not used
    @Override
    public void mouseExited(MouseEvent e) {

    }

    // EFFECTS: none, not used
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    // EFFECTS: none, not used
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
