// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

/** ExternalTask models a task that is running outside of Java (e.g., Rust, Python). */
public class ExternalTask implements Task {
    private boolean running = true;
    private boolean started = true;
    private boolean finished = false;
    private boolean enabled = false;

    private int rawTaskId;

    private TaskId taskId = null;

    public ExternalTask(String id) {
        this.rawTaskId = Integer.parseInt(id);
        this.taskId = new TaskId(rawTaskId);
    }

    @Override
    public long getRawId() {
        return rawTaskId;
    }

    @Override
    public void run() {
        // Do nothing.
    }

    @Override
    public void resume() {
        // Do nothing.
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean hasStarted() {
        return this.started;
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public void markFinished() {
        System.out.println("Marking task " + getRawId() + " as finished.");
        this.finished = true;
    }

    @Override
    public TaskId getTaskId() {
        return taskId;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void markEnabled() {
        enabled = true;
    }
}
