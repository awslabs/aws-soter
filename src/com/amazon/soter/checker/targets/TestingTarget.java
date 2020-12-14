// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

/**
 * Wrapper for executable tests.
 */
public interface TestingTarget {
    void run(Object o) throws Throwable;
    TargetExecutionType getExecutionType();
}
