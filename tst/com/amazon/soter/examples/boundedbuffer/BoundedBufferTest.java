// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer;

import com.amazon.soter.checker.exceptions.DepthBoundExceededException;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import com.amazon.soter.examples.boundedbuffer.harnesses.BasicHarness;
import com.amazon.soter.examples.boundedbuffer.harnesses.FindConfigurationsInBrokenBufferHarness;
import com.amazon.soter.examples.boundedbuffer.harnesses.FindConfigurationsInBufferHarness;
import com.amazon.soter.examples.boundedbuffer.harnesses.MinimalDeadlockBufferInBrokenBufferHarness;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class BoundedBufferTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100)
    public void basicTest() {
        BasicHarness bb = new BasicHarness();
        bb.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100)
    public void findConfigurationsTest() {
        FindConfigurationsInBufferHarness bb = new FindConfigurationsInBufferHarness();
        bb.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100, expected = DepthBoundExceededException.class)
    public void findBrokenConfigurationsTest() {
        FindConfigurationsInBrokenBufferHarness bb = new FindConfigurationsInBrokenBufferHarness();
        bb.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100, expected = DepthBoundExceededException.class)
    public void minimalDeadlockTest() {
        MinimalDeadlockBufferInBrokenBufferHarness bb = new MinimalDeadlockBufferInBrokenBufferHarness();
        bb.run();
    }

    @SoterCheck(search = SearchType.ReplaySearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1, expected = DepthBoundExceededException.class, previousTraceFile = "counterexamples/minimalDeadlockTest.trace")
    public void minimalDeadlockReplayTest() {
        MinimalDeadlockBufferInBrokenBufferHarness bb = new MinimalDeadlockBufferInBrokenBufferHarness();
        bb.run();
    }
}