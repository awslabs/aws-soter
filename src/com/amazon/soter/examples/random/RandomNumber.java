package com.amazon.soter.examples.random;

import com.amazon.soter.java.util.Random;

/** Example application that uses random numbers. */
public class RandomNumber {
    public void run() {
        Random random = new Random();

        int n1 = random.nextInt(25);
        int n2 = random.nextInt(25);
        int n3 = random.nextInt(25);

        if (n3 == 3 && n2 == 2 && n1 == 1) {
            throw new RuntimeException("Hit this fake bug!");
        }
    }
}
