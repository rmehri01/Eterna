package ui.sprites;

import model.Direction;
import model.Entity;
import ui.UserInterfaceObject;
import ui.GameRunner;
import ui.Rectangle;
import ui.Renderer;

import static model.Direction.RIGHT;

/**
 * Represents a sprite of any entity in the game.
 */

public class EntitySprite implements UserInterfaceObject {
    protected Entity entity;
    protected Rectangle entityRectangle;
    protected AnimatedSprite animatedSprite;
    protected Direction direction = RIGHT;

    // EFFECTS: creates an entity sprite with the given animations and entity it represents
    public EntitySprite(AnimatedSprite animatedSprite, Entity entity) {
        this.entity = entity;
        this.animatedSprite = animatedSprite;

        updateDirection();
        entityRectangle = new Rectangle(32, 16, 16, 32);
        entityRectangle.generateGraphics(3, 0xFF00FF90);
    }

    // getters
    public Entity getEntity() {
        return entity;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    // EFFECTS: renders the animated sprite to the screen
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY) {
        renderer.renderSprite(animatedSprite,
                entityRectangle.getPositionX(),
                entityRectangle.getPositionY(),
                zoomX, zoomY, false);
    }

    // MODIFIES: this
    // EFFECTS: updates the entity rectangle and the animations of this
    @Override
    public void update(GameRunner gameRunner) {
        entityRectangle.setPositionX(entity.getPositionX() * 16 * GameRunner.ZOOMX);
        entityRectangle.setPositionY(entity.getPositionY() * 16 * GameRunner.ZOOMY);
        animatedSprite.update(gameRunner);
    }

    // EFFECTS: always false, since there is no effect for being clicked
    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes the animations of the sprite based on direction moved
    public void updateDirection() {
        // TODO: make turn around for all sprites
//        switch (direction) {
//            case RIGHT:
//            case UP:
//                animatedSprite.setAnimationRange(0, 7);
//                break;
//            case LEFT:
//            case DOWN:
//                animatedSprite.setAnimationRange(8, 15);
//                break;
//        }
    }
}
