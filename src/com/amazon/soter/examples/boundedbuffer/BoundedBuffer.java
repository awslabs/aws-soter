// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer;

/** Interface used for all bounded buffer implementations. */
public interface BoundedBuffer {
    void put(Object x) throws InterruptedException;
    Object take() throws InterruptedException;
}
