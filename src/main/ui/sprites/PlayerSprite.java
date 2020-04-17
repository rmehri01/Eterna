package ui.sprites;

import model.Direction;
import model.characters.Player;
import ui.GameRunner;
import ui.Rectangle;

/**
 * The sprite that represents the player.
 */

public class PlayerSprite extends EntitySprite {

    // EFFECTS: creates a new player sprite with animations and the player it represents
    public PlayerSprite(AnimatedSprite animatedSprite, Player player) {
        super(animatedSprite, player);
    }

    // setters
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // MODIFIES: gamerunner, this
    // EFFECTS: updates the rectangle position and animations of this, and camera of gameRunner
    @Override
    public void update(GameRunner gameRunner) {
        super.update(gameRunner);
        Rectangle camera = gameRunner.getRenderer().getCamera();
        camera.setPositionX(entityRectangle.getPositionX() - (camera.getWidth() / 2));
        camera.setPositionY(entityRectangle.getPositionY() - (camera.getHeight() / 2));
    }

    // EFFECTS: always false, since there is no effect for being clicked
    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: updates the animations of this based on direction faced
    @Override
    public void updateDirection() {
        switch (direction) {
            case RIGHT:
                animatedSprite.setAnimationRange(0, 7);
                break;
            case LEFT:
                animatedSprite.setAnimationRange(8, 15);
                break;
            case UP:
                animatedSprite.setAnimationRange(16, 23);
                break;
            case DOWN:
                animatedSprite.setAnimationRange(24, 31);
                break;
        }
    }
}
