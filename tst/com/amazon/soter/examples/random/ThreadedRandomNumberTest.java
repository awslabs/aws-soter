// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.random;

import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;

import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class ThreadedRandomNumberTest {
    @SoterCheck(search = SearchType.DFS, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = -1, expected = RuntimeException.class)
    public void threadedRandomNumberTest() {
        ThreadedRandomNumber r = new ThreadedRandomNumber();
        r.run();
    }

    @SoterCheck(search = SearchType.DFS, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1, expected = RuntimeException.class, previousTraceFile = "counterexamples/threadedRandomNumberTest.trace")
    public void replayThreadedRandomNumberTest() {
        ThreadedRandomNumber r = new ThreadedRandomNumber();
        r.run();
    }
}
