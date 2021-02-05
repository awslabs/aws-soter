// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.boundedbuffer.implementations;

import com.amazon.soter.java.util.concurrent.locks.ReentrantLock;
import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/** Broken bounded buffer implementation with explicit conditions and locks.
 *
 *  This implementation is broken because it uses a single condition variable and only signal's a single waiting
 *  thread; therefore, if the wrong thread is signalled (one that can't proceed yet) the application will deadlock.
 */
public class BoundedBufferWithLocksAndConditionsBroken implements BoundedBuffer {
    private Lock lock;
    private Condition cond;

    public BoundedBufferWithLocksAndConditionsBroken(int size) {
        this.lock = new ReentrantLock();
        this.cond = this.lock.newCondition();
        this.items = new Object[size];
    }

    final Object[] items;
    private int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                cond.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            cond.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                cond.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            cond.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
