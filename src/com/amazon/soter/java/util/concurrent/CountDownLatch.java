// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.java.util.concurrent;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.exceptions.NotImplementedException;
import com.amazon.soter.checker.updates.NoopUpdate;

import java.util.concurrent.TimeUnit;

/** Soter implementation of java.util.concurrent.CountDownLatch. */
public class CountDownLatch {

    private java.util.concurrent.CountDownLatch latch;

    public CountDownLatch(int count) {
        this.latch = new java.util.concurrent.CountDownLatch(count);
    }

    public void await() throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            while (latch.getCount() != 0) {
                Checker.getChecker().yield(new NoopUpdate());
            }
        } else {
            latch.await();
        }
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        } else {
            return latch.await(timeout, unit);
        }
    }

    public void countDown() {
        latch.countDown();
    }

    public long getCount() {
        return latch.getCount();
    }

    public String toString() {
        return latch.toString();
    }

}
