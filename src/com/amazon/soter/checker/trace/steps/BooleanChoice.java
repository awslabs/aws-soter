// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace.steps;

import com.amazon.soter.checker.trace.ScheduleStep;
import com.amazon.soter.checker.trace.ScheduleStepType;

/**
 * Represents a boolean choice in a schedule.
 */
public class BooleanChoice extends ScheduleStep {
    private boolean value;

    public BooleanChoice(boolean b) {
        this.value = b;
        this.setStepType(ScheduleStepType.NondeterministicChoice);
    }

    public String toSerializedTraceFormat() {
        return "boolean," + value;
    }

    public boolean getValue() {
        return value;
    }
}
