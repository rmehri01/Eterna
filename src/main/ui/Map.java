package ui;

import model.Chamber;
import model.Entity;
import model.characters.enemies.Demon;
import model.characters.enemies.Ghost;
import model.characters.enemies.Goblin;
import model.characters.enemies.Slime;
import model.items.HealthPotion;
import model.items.Sword;
import ui.sprites.AnimatedSprite;
import ui.sprites.EntitySprite;
import ui.sprites.Sprite;
import ui.sprites.SpriteSheet;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static model.Chamber.CHAMBER_HEIGHT;
import static model.Chamber.CHAMBER_WIDTH;

/**
 * Represents the map the player is in with the chamber.
 */

public class Map implements UserInterfaceObject {
    private SpriteSheet spriteSheet;
    private Set<MappedTile> mappedTiles;
    private Set<EntitySprite> entitySprites;
    private int currentFloor = 0;
    private Chamber chamber;

    // EFFECTS: creates a new map with the given spritesheet and chamber it represents
    public Map(SpriteSheet spriteSheet, Chamber chamber) {
        this.chamber = chamber;
        this.spriteSheet = spriteSheet;
        mappedTiles = Collections.newSetFromMap(new ConcurrentHashMap<>());
        entitySprites = Collections.newSetFromMap(new ConcurrentHashMap<>());
        addBackground();
    }

    // getters
    public Chamber getChamber() {
        return chamber;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: changes the current chamber and updates the entity sprites associated with it
    public void setChamber(Chamber chamber) {
        this.chamber = chamber;
        setupMapFromChamber();
    }

    // MODIFIES: this
    // EFFECTS: adds the filler tiles and chamber background to this
    private void addBackground() {
        for (int x = 0; x < CHAMBER_WIDTH; x++) {
            // top border
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 0), x, -1));
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 1), x, -0));
            // bottom border
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 0), x, CHAMBER_HEIGHT));
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 1), x, CHAMBER_HEIGHT + 1));
        }

        for (int y = 0; y < CHAMBER_HEIGHT + 1; y++) {
            // left border
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(0, 8), -1, y));
            // right border
            mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 8), CHAMBER_WIDTH, y));
        }

        // fills in edges
        mappedTiles.add(new MappedTile(spriteSheet.getSprite(0, 7), -1, -1));
        mappedTiles.add(new MappedTile(spriteSheet.getSprite(0, 9), -1, CHAMBER_HEIGHT + 1));
        mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 7), CHAMBER_WIDTH, -1));
        mappedTiles.add(new MappedTile(spriteSheet.getSprite(1, 9), CHAMBER_WIDTH, CHAMBER_HEIGHT + 1));
        mappedTiles.add(new MappedTile(spriteSheet.getSprite(5, 11), CHAMBER_WIDTH - 1, CHAMBER_HEIGHT));
    }

    // EFFECTS: renders the map by rendering the filler tile, background chamber,
    //          and any entities at their corresponding positions
    @Override
    public void render(Renderer renderer, int zoomX, int zoomY) {
        int incrementX = 16 * zoomX;
        int incrementY = 16 * zoomY;
        Rectangle camera = renderer.getCamera();

        int initialY = camera.getPositionY() - incrementY - (camera.getPositionY() % incrementY);
        int boundaryY = camera.getPositionY() + camera.getHeight();
        int initialX = camera.getPositionX() - incrementX - (camera.getPositionX() % incrementX);
        int boundaryX = camera.getPositionX() + camera.getWidth();
        for (int y = initialY; y < boundaryY; y += incrementY) {
            for (int x = initialX; x < boundaryX; x += incrementX) {
                renderer.renderSprite(spriteSheet.getSprite(1, 4), x, y, zoomX, zoomY, false);
            }
        }

        for (MappedTile mappedTile : mappedTiles) {
            int posX = mappedTile.positionX * incrementX;
            int posY = mappedTile.positionY * incrementY;
            renderer.renderSprite(mappedTile.sprite, posX, posY, zoomX, zoomY, false);
        }

        for (EntitySprite entitySprite : entitySprites) {
            int posX = entitySprite.getEntity().getPositionX() * incrementX;
            int posY = (entitySprite.getEntity().getPositionY() + 1) * incrementY;
            renderer.renderSprite(entitySprite.getAnimatedSprite(), posX, posY, zoomX, zoomY, false);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the map with entities in the game and updates the sprites of those entities
    @Override
    public void update(GameRunner gameRunner) {
        Set<Entity> entities = chamber.getEntities();
        if (currentFloor != chamber.getFloorNumber()) {
            setupMapFromChamber();
        } else {
            entitySprites = entitySprites.stream()
                    .filter(entitySprite -> entities.contains(entitySprite.getEntity()))
                    .collect(Collectors.toSet());
        }
        entitySprites.forEach(spr -> spr.update(gameRunner));
    }

    // MODIFIES: this
    // EFFECTS: sets the current floor and updates the entity sprites to match the chamber
    private void setupMapFromChamber() {
        currentFloor = chamber.getFloorNumber();
        entitySprites.clear();
        for (Entity entity : chamber.getEntities()) {
            entitySprites.add(new EntitySprite(getSpriteForEntity(entity), entity));
        }
    }

    // EFFECTS: always false, since there is no effect for being clicked
    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int zoomX, int zoomY) {
        return false;
    }

    // EFFECTS: returns a sprite based on the type of entity given
    public AnimatedSprite getSpriteForEntity(Entity entity) {
        AnimatedSprite sprite = null;
        if (entity instanceof Goblin) {
            sprite = new AnimatedSprite(spriteSheet, 5);
            sprite.setAnimationRange(13 * 32 + 25, 32 * 14 - 1);
        } else if (entity instanceof Demon) {
            sprite = new AnimatedSprite(spriteSheet, 5);
            sprite.setAnimationRange(19 * 32 + 25, 32 * 20 - 1);
        } else if (entity instanceof Slime) {
            sprite = new AnimatedSprite(spriteSheet, 10);
            sprite.setAnimationRange(7 * 32 + 27, 7 * 32 + 31);
        } else if (entity instanceof Ghost) {
            sprite = new AnimatedSprite(spriteSheet, 5);
            sprite.setAnimationRange(5 * 32 + 25, 32 * 6 - 1);
        } else if (entity instanceof HealthPotion) {
            sprite = new AnimatedSprite(spriteSheet, 5);
            sprite.setAnimationRange(14 * 32 + 18, 14 * 32 + 18);
        } else if (entity instanceof Sword) {
            sprite = new AnimatedSprite(spriteSheet, 5);
            sprite.setAnimationRange(32 + 18, 32 + 18);
        }
        return sprite;
    }

    /**
     * Represents one tile within the map that contains a sprite at a certain x and y.
     */
    private static class MappedTile {
        public Sprite sprite;
        public int positionX;
        public int positionY;

        // EFFECTS: creates a mapped tile with a sprite, and x and y position
        public MappedTile(Sprite sprite, int positionX, int positionY) {
            this.sprite = sprite;
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
}
