// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.support;

/** Interface locks where locks can be locked without yielding, which is needed in some cases (e.g., Conditions). */
public interface YieldlessLock {
    void lockWithoutYield();
}
