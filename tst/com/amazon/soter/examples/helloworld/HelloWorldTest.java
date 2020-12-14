// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.helloworld;

import com.amazon.soter.checker.exceptions.CounterexampleFoundException;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class HelloWorldTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1000, expected = CounterexampleFoundException.class)
    public void helloWorldTest() {
        HelloWorld hw = new HelloWorld();
        hw.run();
    }

    @SoterCheck(search = SearchType.ReplaySearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1, expected = CounterexampleFoundException.class, previousTraceFile = "counterexamples/helloWorldTest.trace")
    public void helloWorldReplayTest() {
        HelloWorld hw = new HelloWorld();
        hw.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 100000, errorDepth = 100001, maxIterations = 10000)
    public void helloWorldSingleThreadTest() {
        HelloWorldSingleThread hw = new HelloWorldSingleThread();
        hw.run();
    }
}
