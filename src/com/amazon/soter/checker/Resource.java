// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.tasks.Task;

/**
 * Interface for resources (e.g., locks, conditions, etc.) to determine whether or not they are available.
 */
public interface Resource {
    boolean isAvailableFor(Task t);
    boolean hasAssociatedTask();
    Task getAssociatedTask();
}
