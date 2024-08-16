package zombicide.util;

import java.util.Random;

public class Dice {
    Random random;
    int nbFaces;

    /**
     * @param nbFaces the number of faces for the dice
     */
    public Dice(int nbFaces) {
        this.random = new Random();
        this.nbFaces = nbFaces;
    }

    /**
     * @return a random number who simulates the dice roll
     */
    public int roll() {
        int result = random.nextInt(1, nbFaces + 1);
        System.out.println("Rolling dice... " + "Result is " + result);
        return result;
    }
}
