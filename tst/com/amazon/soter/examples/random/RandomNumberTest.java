// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.random;

import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;

import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class RandomNumberTest {
    @SoterCheck(search = SearchType.DFS, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = -1, expected = RuntimeException.class)
    public void dfsRandomNumberTest() {
        RandomNumber r = new RandomNumber();
        r.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 10000, expected = RuntimeException.class)
    public void statelessSearchRandomNumberTest() {
        RandomNumber r = new RandomNumber();
        r.run();
    }

    @SoterCheck(search = SearchType.ReplaySearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1, expected = RuntimeException.class, previousTraceFile = "counterexamples/randomNumberTest.trace")
    public void replayRandomNumberTest() {
        RandomNumber r = new RandomNumber();
        r.run();
    }
}
