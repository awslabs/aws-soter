// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.exceptions;

/** Exception thrown when tests should trigger a particular exception, but a different exception was thrown. */
public class ExceptionMismatchAssertionFailure extends RuntimeException {
    public ExceptionMismatchAssertionFailure(String message) {
        super(message);
    }
}
