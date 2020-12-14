// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace;

/**
 * The type of a single schedule step.
 */
public enum ScheduleStepType {
    /** Scheduling choice, as in which task comes next. */
    SchedulingChoice,

    /** Captures nondeterminism, for example, a random integer. */
    NondeterministicChoice
}
