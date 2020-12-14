// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.thread;

import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class CountDownLatchTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100)
    public void countDownLatchTest() {
        CountDownLatch m = new CountDownLatch();
        m.run();
    }
}
