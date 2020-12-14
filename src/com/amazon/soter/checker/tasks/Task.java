// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

/** Interface for the various types of tasks that Soter will interact with. */
public interface Task {

    String toString();

    long getRawId();

    void run();

    void resume();

    boolean isRunning();

    boolean hasStarted();

    boolean isFinished();

    void markFinished();

    TaskId getTaskId();

    boolean isEnabled();

    void markEnabled();

}
