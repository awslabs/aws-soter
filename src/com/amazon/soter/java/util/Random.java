package com.amazon.soter.java.util;

import com.amazon.soter.checker.Checker;

/**
 * Soter implementation of java.util.Random.
 */
public class Random extends java.util.Random {
    private java.util.Random random;

    public Random() {
        if (Checker.isRuntimeControlled()) {
            long randomSeed = Checker.getChecker().getRandomSeed();

            if (randomSeed != -1) {
                this.random = new java.util.Random(randomSeed);
            } else {
                this.random = new java.util.Random();
            }
        } else {
            this.random = new java.util.Random();
        }
    }

    public int nextInt() {
        if (Checker.isRuntimeControlled()) {
            return Checker.getChecker().nextInt();
        } else {
            return this.random.nextInt();
        }
    }

    public int nextInt(int bound) {
        if (Checker.isRuntimeControlled()) {
            return Checker.getChecker().nextInt(bound);
        } else {
            return this.random.nextInt(bound);
        }
    }

    public boolean nextBoolean() {
        if (Checker.isRuntimeControlled()) {
            return Checker.getChecker().nextBoolean();
        } else {
            return this.random.nextBoolean();
        }
    }
}
