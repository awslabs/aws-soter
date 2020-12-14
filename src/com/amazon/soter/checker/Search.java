// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.targets.TestingTarget;

/** Search interface for exploring different schedules. */
public interface Search {
    /** Specify what the max depth should be before considering an error to have been reached.
     *
     * @param errorDepth the error depth
     */
     void setErrorDepth(int errorDepth);

    /** Specify what the max depth should be.
     *
     * @param maxDepth the maximum depth that should be searched.
     */
    void setMaxDepth(int maxDepth);

    /** Set the number of iterations that the search should run.
     *
     * @param maxIterations the maximum number of iterations that should be searched.
     */
    void setMaxIterations(int maxIterations);

    /** Unblock the Search. */
    void unblock();

    /** Perform the Search.
     *
     * @param target The entry point for the program
     * @param searchConfiguration The search configuration
     */
    void doSearch(TestingTarget target, SearchConfiguration searchConfiguration);

    /** Return the next integer based on the search and strategy.
     *
     * @return an integer.
     */
    int getNextInteger();

    /** Return the next integer (within a bound) based on the search and strategy.
     *
     * @param bound upper bound (exclusive) on the integer.
     * @return a integer
     */
    int getNextInteger(int bound);

    /** Return the next boolean based on the search and strategy.
     *
     * @return a boolean choice.
     */
    boolean getNextBoolean();
}
