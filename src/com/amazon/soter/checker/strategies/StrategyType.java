// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.strategies;

/** Different strategies used during the search for exploration of nondeterminism. */
public enum StrategyType {

    /** Random number generation during search. */
    RandomStrategy,

    /** Round-robin random choices during search. */
    RoundRobinStrategy
}
