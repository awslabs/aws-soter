// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

/**
 * Specify the configuration for a given search to be performed by a test.
 */
public class SearchConfiguration {
    private State initState;
    private Update initOp;
    private Update exitOp;
    private long randomSeed;
    private String previousTraceFile;

    public SearchConfiguration(State initState, Update initOp, Update exitOp, long randomSeed, String traceFile) {
        this.initState = initState;
        this.initOp = initOp;
        this.exitOp = exitOp;
        this.randomSeed = randomSeed;
        this.previousTraceFile = traceFile;
    }

    public State getInitState() {
        return this.initState;
    }

    public Update getInitOp() {
        return this.initOp;
    }

    public Update getExitOp() {
        return this.exitOp;
    }

    public long getRandomSeed() {
        return this.randomSeed;
    }

    public String getPreviousTraceFile() {
        return this.previousTraceFile;
    }
}
