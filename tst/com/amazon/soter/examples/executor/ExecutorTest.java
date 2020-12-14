// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.executor;

import com.amazon.soter.checker.exceptions.CounterexampleFoundException;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class ExecutorTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 10000, expected = CounterexampleFoundException.class)
    public void fixedThreadPoolExampleTest() {
        FixedThreadPoolExample example = new FixedThreadPoolExample();
        example.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 10000, expected = CounterexampleFoundException.class)
    public void cachedThreadPoolExampleTest() {
        CachedThreadPoolExample example = new CachedThreadPoolExample();
        example.run();
    }
}
