// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace;

/**
 * Abstract class for a single step.
 */
public abstract class ScheduleStep {
    private ScheduleStepType stepType;

    public abstract String toSerializedTraceFormat();

    public ScheduleStepType getStepType() {
        return stepType;
    }

    public void setStepType(ScheduleStepType stepType) {
        this.stepType = stepType;
    }
}
