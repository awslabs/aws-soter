// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace.steps;

import com.amazon.soter.checker.trace.ScheduleStep;
import com.amazon.soter.checker.trace.ScheduleStepType;

/**
 * Represents a random integer choice in a schedule.
 */
public class IntegerChoice extends ScheduleStep {
    private int value;

    public IntegerChoice(int value) {
        this.value = value;
        this.setStepType(ScheduleStepType.NondeterministicChoice);
    }

    public String toSerializedTraceFormat() {
        return "int," + value;
    }

    public int getValue() {
        return value;
    }
}
