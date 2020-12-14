// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

import com.amazon.soter.checker.Checker;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import com.amazon.soter.checker.exceptions.InternalSystemFailureException;

import java.lang.Runnable;
import com.amazon.soter.java.util.concurrent.Callable;

/** Java task controlled by Soter (as opposed to an ExternalTask which models thread in another runtime. */
public class JavaTask implements Task {
    private boolean running = false;
    private boolean started = false;
    private boolean finished = false;

    private String name;
    private Runnable runnable;
    private Callable callable;
    private TaskExecutionModel taskExecutionModel;
    private TaskType taskType;
    private Future future;
    private final TaskId taskId = new TaskId();

    public JavaTask(Runnable runnable, TaskExecutionModel taskExecutionModel) {
        this.running = false;
        this.runnable = runnable;
        this.taskExecutionModel = taskExecutionModel;
        this.taskType = TaskType.RUNNABLE;
    }

    public JavaTask(Callable callable, TaskExecutionModel taskExecutionModel) {
        this.running = false;
        this.callable = callable;
        this.taskExecutionModel = taskExecutionModel;
        this.taskType = TaskType.CALLABLE;
    }

    public String toString() {
        if (this.name != null) {
            return this.name;
        } else {
            return super.toString();
        }
    }

    public Future getFuture() {
        return this.future;
    }

    @Override
    public long getRawId() {
        return this.taskId.id;
    }

    @Override
    public void run() {
        this.running = true;
        this.started = true;

        ForkJoinPool checkerThreadPool = Checker.getChecker().getThreadPool();

        if (taskExecutionModel == TaskExecutionModel.EXECUTE) {
            checkerThreadPool.execute(runnable);
        } else if (taskExecutionModel == TaskExecutionModel.SUBMIT) {
            if (taskType == TaskType.CALLABLE) {
                this.future = checkerThreadPool.submit(callable);
            } else if (taskType == TaskType.RUNNABLE) {
                this.future = checkerThreadPool.submit(runnable);
            }
        }
    }

    @Override
    public void resume() {
        // Eventually, we will have to reacquire locks and things like this.
        // before we can actually resume the task.
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
        this.finished = true;

        switch (taskType) {
            case RUNNABLE:
                if (this.runnable instanceof com.amazon.soter.java.lang.Runnable) {
                    com.amazon.soter.java.lang.Runnable r = (com.amazon.soter.java.lang.Runnable) this.runnable;
                    r.markFinished(this);
                }

                break;
            case CALLABLE:
                this.callable.markFinished(this);
                break;
            default:
                throw new InternalSystemFailureException();
        }
    }

    @Override
    public TaskId getTaskId() {
        return taskId;
    }

    @Override
    public boolean isEnabled() {
        switch (taskType) {
            case RUNNABLE:
                if (this.runnable instanceof com.amazon.soter.java.lang.Runnable) {
                    com.amazon.soter.java.lang.Runnable r = (com.amazon.soter.java.lang.Runnable) this.runnable;
                    return r.isEnabled(this);
                }

                return true;
            case CALLABLE:
                return this.callable.isEnabled(this);
            default:
                throw new InternalSystemFailureException();
        }
    }

    @Override
    public void markEnabled() {
        // Nothing.
    }
}
