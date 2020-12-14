// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace.steps;

import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.trace.ScheduleStep;
import com.amazon.soter.checker.trace.ScheduleStepType;

/**
 * Represents a single choice made in a schedule.
 */
public class SchedulingChoice extends ScheduleStep {
    private long id;
    private Task task;

    public SchedulingChoice(Task t) {
        this.id = t.getRawId();
        this.task = t;
        this.setStepType(ScheduleStepType.SchedulingChoice);
    }

    public SchedulingChoice(long id) {
        this.id = id;
    };

    public long getId() {
        return this.id;
    }

    public String toSerializedTraceFormat() {
        return "task," + task.getRawId();
    }
}
