// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.exceptions.UpdateMismatchException;
import com.amazon.soter.checker.tasks.Task;

/**
 * Representation of program state.
 */
public interface State {

    void update(Update update) throws UpdateMismatchException;

    void update(Task task, Update update) throws UpdateMismatchException;

    /** Is a particular task enabled? */
    boolean isEnabled(Task t);

    void addThread(Task t, Update initOp);

    void removeThread(Task t);

    boolean isDeadlocked();
}
