package model.characters.enemies;

import model.RandomGenerator;

/**
 * Random generator for enemies that does so according to given spawn chances and floor number.
 */

public class EnemyFactory {

    public static final double[] ENEMY_SPAWN_CHANCES = {0.4, 0.3, 0.25, 0.05};

    // EFFECTS: returns a random enemy at given location within the chamber according to spawn chances
    public Enemy getRandomEnemy(int floorNumber, int x, int y) {
        Enemy[] enemyArray = new Enemy[4];
        enemyArray[0] = new Slime(x, y);
        enemyArray[1] = new Goblin(x, y);
        enemyArray[2] = new Ghost(x, y);
        enemyArray[3] = new Demon(x, y);

        return enemyArray[RandomGenerator.chooseFromProbabilities(ENEMY_SPAWN_CHANCES)];
    }
}
