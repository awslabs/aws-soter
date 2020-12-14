// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.exceptions;

/** Thrown when a test expects an exception of a certain type to be thrown, but no exception is thrown. */
public class ExceptionExpectedAssertionFailure extends RuntimeException {
    public ExceptionExpectedAssertionFailure(String message) {
        super(message);
    }
}
