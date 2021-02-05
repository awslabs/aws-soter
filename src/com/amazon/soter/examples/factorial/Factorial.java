// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.factorial;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.java.util.Random;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/** Factorial example. */
public class Factorial {
    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public void run() {
        ForkJoinPool executor = new ForkJoinPool(4);
        List<Future<Integer>> resultList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            Integer number = random.nextInt(10);
            FactorialCalculator calculator  = new FactorialCalculator(number);
            Future<Integer> result = executor.submit(calculator);
            resultList.add(result);
        }

        for (Future<Integer> future : resultList) {
            try {
                System.out.println("Future result is - " + " - " + future.get()
                        + "; And Task done is " + future.isDone());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    public static void main(String[] args) {
        Factorial f = new Factorial();
        f.run();
    }
}
