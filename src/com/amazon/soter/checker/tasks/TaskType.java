// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

/**
 * Describes the type of task we are launching.
 */
public enum TaskType {
    /** java.lang.Runnable. */
    RUNNABLE,

    /** java.util.concurrent.Callable. */
    CALLABLE
}
