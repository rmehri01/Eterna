package model;

import java.util.Random;

/**
 * A random generator that picks items based on percent chance.
 */

public class RandomGenerator {

    // EFFECTS: cannot be instantiated, as this is used for static method utilities
    private RandomGenerator() {
    }

    // EFFECTS: returns a random index that is more biased towards higher probabilities
    //          if probabilities is empty, throws IllegalArgumentException
    public static int chooseFromProbabilities(double[] probabilities) {
        Random random = new Random();
        double chance = random.nextDouble();
        double sumSoFar = 0;
        for (int i = 0; i < probabilities.length; i++) {
            if (chance < sumSoFar + probabilities[i]) {
                return i;
            }
            sumSoFar += probabilities[i];
        }

        if (probabilities.length == 0) {
            throw new IllegalArgumentException("Cannot choose from empty list.");
        } else {
            throw new IllegalArgumentException("Sum of probabilities do not add up to 1.");
        }

    }
}
