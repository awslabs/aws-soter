// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer.harnesses;

import com.amazon.soter.examples.boundedbuffer.implementations.BoundedBufferWithLocksAndConditionsBroken;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;
import com.amazon.soter.examples.boundedbuffer.threads.Reader;
import com.amazon.soter.examples.boundedbuffer.threads.Writer;

import java.util.concurrent.TimeUnit;

/** Example of the smallest possible configuration that's problematic with the broken bounded buffer implementation. */
public class MinimalDeadlockBufferInBrokenBufferHarness {
    public void awaitAll(ForkJoinPool customThreadPool) {
        // Required, otherwise the loop will not terminate as the thread pool is still accepting jobs.
        customThreadPool.shutdown();

        while (true) {
            try {
                if (customThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        BoundedBufferWithLocksAndConditionsBroken buffer = new BoundedBufferWithLocksAndConditionsBroken(1);
        System.out.println("Testing buffer size=1, reader=2, writer=1, iterations=10");
        ForkJoinPool customThreadPool = new ForkJoinPool(3);
        customThreadPool.execute(new Reader(buffer, 5));
        customThreadPool.execute(new Reader(buffer, 5));
        customThreadPool.execute(new Writer(buffer, 10));
        awaitAll(customThreadPool);
    }
}
