package model.characters.enemies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {
    
    EnemyFactory testFactory;
    
    @BeforeEach
    public void setup() {
        testFactory = new EnemyFactory();
    }
    
    @Test
    public void testGetRandomEnemy() {
        boolean hasSlime = false;
        boolean hasGoblin = false;
        boolean hasGhost = false;
        boolean hasDemon = false;

        for (int i = 0; i < 10000; i++) {
            Enemy randomEnemy = testFactory.getRandomEnemy(1, 0, 0);
            switch (randomEnemy.getName()) {
                case "Slime":
                    hasSlime = true;
                    break;
                case "Goblin":
                    hasGoblin = true;
                    break;
                case "Ghost":
                    hasGhost = true;
                    break;
                case "Demon":
                    hasDemon = true;
                    break;
            }
        }

        assertTrue(hasSlime);
        assertTrue(hasGoblin);
        assertTrue(hasGhost);
        assertTrue(hasDemon);
    }


}
