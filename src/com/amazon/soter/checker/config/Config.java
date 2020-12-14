// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.config;

import java.util.logging.Level;

/**
 * Soter configuration.
*/
public final class Config {
    private Config() {
        // Never called.
    }

    /** Max number of threads for the checker's internal ForkJoinPool. */
    public static final int MAX_THREADS = 1000;

    /** Whether or not we should consider a depth bound exceeded failure as a bug or not. */
    public static final boolean DEPTH_BOUND_CONSIDERED_BUG = true;

    /** Select log level. */
//  public static final Level LOG_LEVEL = Level.FINE; // All debugging.
    public static final Level LOG_LEVEL = Level.INFO; // Only informational and failures.

    /** Trace file. */
    public static final String TRACE_FILE = "counterexample.trace";
}
