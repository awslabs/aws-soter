// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.java.util.concurrent.locks;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.support.YieldlessLock;
import com.amazon.soter.checker.exceptions.NotImplementedException;
import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Soter implementation of java.util.concurrent.locks.ReentrantLock.
 */
public class ReentrantLock extends java.util.concurrent.locks.ReentrantLock implements Resource, YieldlessLock {
    private Lock lock;
    private boolean isHeld = false;
    private HashMap<Task, Condition> waiting = new HashMap<Task, Condition>();

    public ReentrantLock() {
        if (!Checker.isRuntimeControlled()) {
            this.lock = new java.util.concurrent.locks.ReentrantLock();
        }
    }

    public boolean isAvailableFor(Task t) {
        return !isHeld;
    }

    public Task getAssociatedTask() {
        return null;
    }

    public boolean hasAssociatedTask() {
        return false;
    }

    public void lock() {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().yield(new ResourceNeededUpdate(this));
            isHeld = true;
            Checker.getChecker().updateState(new ResourceAcquiredUpdate(this));
        } else {
            lock.lock();
        }
    }

    public void lockWithoutYield() {
        Checker.getChecker().updateState(new ResourceNeededUpdate(this));
        isHeld = true;
        Checker.getChecker().updateState(new ResourceAcquiredUpdate(this));
    }

    public void unlock() {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().updateState(new ResourceReleasedUpdate(this));
            isHeld = false;
            Checker.getChecker().yield(new NoopUpdate());
        } else {
            lock.unlock();
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        } else {
            lock.lockInterruptibly();
        }
    }

    public java.util.concurrent.locks.Condition newCondition() {
        if (Checker.isRuntimeControlled()) {
            Condition condition = new Condition(this);
            return condition;
        } else {
            return lock.newCondition();
        }
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        } else {
            return lock.tryLock(timeout, unit);
        }
    }

    public boolean tryLock() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        } else {
            return lock.tryLock();
        }
    }
}
