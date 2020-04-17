package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomGeneratorTest {

    @Test
    public void testChooseFromProbabilitiesEmptyProbabilities() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerator.chooseFromProbabilities(new double[0]));
    }

    @Test
    public void testChooseFromProbabilitiesNonEmptyCheckFullyBiased() {
        double[] chances = {0, 1};
        assertEquals(1, RandomGenerator.chooseFromProbabilities(chances));
    }

    @Test
    public void testChooseFromProbabilitiesNonEmptyShouldBeBiasedOverManyTrials() {
        int count = 0;
        double[] chances = {0.8, 0.2};
        for (int i = 0; i < 100000; i++) {
            if (RandomGenerator.chooseFromProbabilities(chances) == 0) {
                count++;
            } else {
                count--;
            }
        }
        assertTrue(count > 0);
    }

    @Test
    public void testChooseFromProbabilitiesDoNotAddUpTo1() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerator.chooseFromProbabilities(new double[]{0, 0}));
    }
}
