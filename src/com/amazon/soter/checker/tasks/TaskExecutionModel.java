// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

/**
 * Describes how should the task be executed by the checker.
 */
public enum TaskExecutionModel {
    /** Execution via the .execute method. */
    EXECUTE,

    /** Submit via the .submit method. */
    SUBMIT
}
