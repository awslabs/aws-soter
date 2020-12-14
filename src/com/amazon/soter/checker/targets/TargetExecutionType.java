// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

/** The type of target that we are executing, either external (e.g., Python, Rust) or native (e.g., Java). */
public enum TargetExecutionType {
    /** Java, in the same VM. */
    Java,

    /** External application that must be invoked by the system. */
    External
}
