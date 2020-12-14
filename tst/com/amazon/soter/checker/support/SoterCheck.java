// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.support;

import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SoterCheck {

    /** Specify the type of search to be performed. */
    SearchType search();

    /** Specify the type of strategy that should be used. */
    StrategyType strategy();

    /** Set the maximum depth to be searched. */
    int maxDepth() default -1;

    /** Set the maximum depth to be searched before an error is thrown. */
    int errorDepth() default -1;

    /** Set the number of iterations to perform. */
    int maxIterations() default -1;

    /** Specify the exception that is expected from the test */
    Class expected() default Object.class;

    /** Specify the random seed to be used for the test. */
    long randomSeed() default -1;

    /** Specify the previous trace file to be used if using the replay search. */
    String previousTraceFile() default "";

}