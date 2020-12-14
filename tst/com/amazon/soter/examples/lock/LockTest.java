// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.lock;

import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class LockTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1000)
    public void basicTest() {
        Lock l = new Lock();
        l.run();
    }
}
