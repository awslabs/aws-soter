// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.lockacquisition;

import com.amazon.soter.checker.exceptions.DeadlockDetectedException;
import com.amazon.soter.checker.exceptions.DepthBoundExceededException;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.support.SoterCheck;
import com.amazon.soter.checker.support.SoterCheckRunner;
import org.junit.runner.RunWith;

@RunWith(SoterCheckRunner.class)
public class LockAcquisitionTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 10000, expected = DepthBoundExceededException.class)
    public void basicTest() {
        LockAcquisition la = new LockAcquisition();
        la.run();
    }

    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 100, expected = DeadlockDetectedException.class)
    public void deadlockTest() {
        LockAcquisitionDeadlock la = new LockAcquisitionDeadlock();
        la.run();
    }
}
