// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.benchmark;

import com.amazon.soter.checker.TestingEngine;
import com.amazon.soter.checker.targets.TestingTarget;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.targets.LambdaTestingTarget;
import org.junit.Test;

public class BenchmarkTest {
    private final int MAX_ITERATIONS = 10000;

    private final int MAX_DEPTH = -1;

    private final int ERROR_DEPTH = -1;

    private static String formatSize(long v) {
        if (v < 1024) return v + " B";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double) v / (1L << (z*10)), " KMGTPE".charAt(z));
    }

    private void printStackInformation(SearchType searchType) {
        long heapSize = Runtime.getRuntime().totalMemory();
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        long heapFreeSize = Runtime.getRuntime().freeMemory();

        System.out.println(searchType + " heapSize: " + formatSize(heapSize));
        System.out.println(searchType + " heapMaxSize: " + formatSize(heapMaxSize));
        System.out.println(searchType + " heapFreeSize: " + formatSize(heapFreeSize));
        System.out.println("---");
    }

    @Test
    public void benchmarkTest() {
        TestingTarget dfsTestingTarget = new LambdaTestingTarget((x) -> {
            Benchmark p = new Benchmark();
            p.run();
            printStackInformation(SearchType.DFS);
        });

        TestingEngine dfsTe = new TestingEngine(SearchType.DFS, StrategyType.RandomStrategy, MAX_DEPTH, ERROR_DEPTH, MAX_ITERATIONS, dfsTestingTarget);
        long dfsStartTime = System.nanoTime();
        dfsTe.run();
        long dfsEndTime = System.nanoTime();
        long dfsDuration = (dfsEndTime - dfsStartTime) / 1000000;

        TestingTarget statelessSearchTestingTarget = new LambdaTestingTarget((x) -> {
            Benchmark p = new Benchmark();
            p.run();
            printStackInformation(SearchType.StatelessSearch);
        });

        TestingEngine ssTe = new TestingEngine(SearchType.StatelessSearch, StrategyType.RandomStrategy, MAX_DEPTH, ERROR_DEPTH, MAX_ITERATIONS, statelessSearchTestingTarget);
        long ssStartTime = System.nanoTime();
        ssTe.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println("DFS max Soter iterations: " + MAX_ITERATIONS);
        System.out.println("DFS Soter duration: " + dfsDuration + "ms.");
        System.out.println("DFS mean duration per Soter iteration: " + (dfsDuration / MAX_ITERATIONS) + "ms.");

        System.out.println("");
        System.out.println("SS max Soter iterations: " + MAX_ITERATIONS);
        System.out.println("SS Soter duration: " + ssDuration + "ms.");
        System.out.println("SS mean duration per Soter iteration: " + (ssDuration / MAX_ITERATIONS) + "ms.");
    }
}
