// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.random;

import com.amazon.soter.java.util.Random;

import java.util.ArrayList;
import java.util.List;

/** Example that combines random numbers with threads. */
public class ThreadedRandomNumber {
    private volatile List<Integer> integers = new ArrayList<>();

    private class RandomThread extends com.amazon.soter.java.lang.Thread {
        private Random random = new Random();

        public void run() {
            int nextInt = random.nextInt(25);
            integers.add(nextInt);
        }
    }

    public void run() {
        RandomThread rt1 = new RandomThread();
        rt1.start();

        RandomThread rt2 = new RandomThread();
        rt2.start();

        RandomThread rt3 = new RandomThread();
        rt3.start();

        try {
            rt3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            rt2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            rt1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (integers.get(0) == 3 && integers.get(1) == 2 && integers.get(2) == 1) {
            throw new RuntimeException("Hit this fake bug!");
        }
    }
}
