// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.external;

import com.amazon.soter.checker.TestingEngine;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.targets.ExternalTestingTarget;
import com.amazon.soter.checker.targets.TestingTarget;

import org.junit.Test;

import com.amazon.soter.checker.exceptions.CounterexampleFoundException;

public class ExternalTest {
    public int MAX_DEPTH = 1000;
    public int ERROR_DEPTH = 10000;
    public StrategyType STRATEGY_TYPE = StrategyType.RandomStrategy;

    // This test will fail if you do not have Python3 installed.
    // On Amazon Linux, this will be fine because the SoterWIP/release-1 version set will
    // automatically install the interpreter and put it on the path.
    @Test
    public void externalPythonIterationTest() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("python/iteration.py");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    // This test will fail if you do not have Python3 installed.
    // On Amazon Linux, this will be fine because the SoterWIP/release-1 version set will
    // automatically install the interpreter and put it on the path.
    //
    // This test is the same as the test above, but is here to ensure that the gRPC server properly resets
    // itself in between executions.
    @Test
    public void externalPythonSecondIterationTest() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("python/iteration.py");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    // This test will fail if you do not have Python3 installed.
    // On Amazon Linux, this will be fine because the SoterWIP/release-1 version set will
    // automatically install the interpreter and put it on the path.
    @Test
    public void externalPythonRaceTest() {
        int numIterations = 5;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("python/race.py");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    // This test will fail if you do not have Python3 installed.
    // On Amazon Linux, this will be fine because the SoterWIP/release-1 version set will
    // automatically install the interpreter and put it on the path.
    @Test
    public void externalPythonRandomBooleanTest() {
        int numIterations = 100;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("python/randbool.py");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    // This test will fail if you do not have Python3 installed.
    // On Amazon Linux, this will be fine because the SoterWIP/release-1 version set will
    // automatically install the interpreter and put it on the path.
    @Test
    public void externalPythonRandomIntegerTest() {
        int numIterations = 100;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("python/randint.py");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void externalRustRace1AsyncTest() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/race1-async");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void externalRustRace2AsyncTest() {
        int numIterations = 5;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/race2-async");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void externalRustRace1SyncTest() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/race1-sync");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    // TODO: This should be failing, but it's not?
    @Test
    public void externalRustRace2SyncTest() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/race2-sync");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void externalRustRace1Test() {
        int numIterations = 10;
        SearchType ssSearchType = SearchType.StatelessSearch;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/race1");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void externalRustRandTest() {
        int numIterations = 200;
        SearchType ssSearchType = SearchType.DFS;
        TestingTarget ssTestingTarget = new ExternalTestingTarget("rust/soter/target/debug/rand");
        TestingEngine ssTestingEngine = new TestingEngine(ssSearchType, STRATEGY_TYPE, MAX_DEPTH, ERROR_DEPTH, numIterations, ssTestingTarget);
        ssTestingEngine.setExpectedException(CounterexampleFoundException.class);
        long ssStartTime = System.nanoTime();
        ssTestingEngine.run();
        long ssEndTime = System.nanoTime();
        long ssDuration = (ssEndTime - ssStartTime) / 1000000;

        System.out.println("");
        System.out.println(ssSearchType + " max Soter iterations: " + numIterations);
        System.out.println(ssSearchType + " Soter duration: " + ssDuration + "ms.");
        System.out.println(ssSearchType + " mean duration per Soter iteration: " + (ssDuration / numIterations) + "ms.");
    }

    @Test
    public void dummyTest() {
        assert(true);
    }
}