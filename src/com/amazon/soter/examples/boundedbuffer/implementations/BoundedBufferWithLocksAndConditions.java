// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer.implementations;

import com.amazon.soter.java.util.concurrent.locks.ReentrantLock;
import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/** Bounded buffer implementation that uses explicit locks and conditions instead of the synchronized keyword. */
public class BoundedBufferWithLocksAndConditions implements BoundedBuffer {
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;

    public BoundedBufferWithLocksAndConditions(int size) {
        this.lock = new ReentrantLock();
        this.notFull = this.lock.newCondition();
        this.notEmpty = this.lock.newCondition();
        this.items = new Object[size];
    }

    final Object[] items;
    private int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
