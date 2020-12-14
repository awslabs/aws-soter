// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.benchmark;

import com.amazon.soter.java.util.Random;

import java.util.ArrayList;
import java.util.List;

/** Benchmark random number generation in Soter. */
public class Benchmark {
    public void run() {
        Random random = new Random();

        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++) {
            int r1 = random.nextInt(25);

            for (int j = 0; j < 100; j++) {
                int r2 = random.nextInt(25);
                list.add(r1 * r2);
            }
        }
    }
}
