// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer.threads;

import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

/** Reader thread for the bounded buffer. */
public class Reader implements Runnable {
    private BoundedBuffer buffer;
    private int iterations;

    public Reader(BoundedBuffer buffer, int iterations) {
        this.iterations = iterations;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                Object x = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
