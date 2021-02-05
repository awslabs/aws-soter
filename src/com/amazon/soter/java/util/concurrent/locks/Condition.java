// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.java.util.concurrent.locks;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.support.YieldlessLock;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.exceptions.NotImplementedException;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

/**
 * Soter implementation of java.util.concurrent.locks.Condition.
 */
public class Condition implements java.util.concurrent.locks.Condition, Resource {
    private final Logger logger = Logger.getLogger(Condition.class.getName());

    private Lock lock;

    private LinkedList<Task> waitQueue;
    private LinkedList<Task> runQueue;

    public Condition(Lock lock) {
        this.lock = lock;
        this.waitQueue = new LinkedList<Task>();
        this.runQueue = new LinkedList<Task>();
    }

    public boolean isAvailableFor(Task t) {
        boolean result = false;
        Resource r = (Resource) this.lock;

        if (this.runQueue.size() > 0) {
            Task t1 = this.runQueue.peekFirst();
            if (t.equals(t1)) {
                result = r.isAvailableFor(t);
            } else {
                result = false;
            }
        } else {
            result = r.isAvailableFor(t) && !this.waitQueue.contains(t);
        }

        logger.fine("Condition isAvailableFor task " + t + " result: " + result);

        return result;
    }

    public Task getAssociatedTask() {
        return null;
    }

    public boolean hasAssociatedTask() {
        return false;
    }

    public void await() throws InterruptedException {
        Task t = Checker.getChecker().getRunningTask();

        /* Add the current task to the waitQueue, update the checker state to indicate we are waiting. */
        this.waitQueue.add(t);
        logger.fine("Condition await task " + t);
        Checker.getChecker().updateState(new ResourceNeededUpdate(this));

        /* Release the lock and yield.
         * This will actually release the lock and notify the checker. */
        lock.unlock();

        logger.fine("Condition received signal for task " + t);
        /* Received signal, there for mark that we no longer are waiting on a resource
         * and we can consider the resource acquired and released.
         */
        Checker.getChecker().updateState(new ResourceAcquiredUpdate(this));
        Checker.getChecker().updateState(new ResourceReleasedUpdate(this));

        /* Lock without yielding. */
        YieldlessLock l = (YieldlessLock) lock;
        l.lockWithoutYield();

        /* Remove ourselves from the runQueue. */
        this.runQueue.remove(t);
    }

    public boolean await(long time, TimeUnit unit) throws InterruptedException {
        throw new NotImplementedException();
    }

    public void awaitUninterruptibly() {
        throw new NotImplementedException();
    }

    public long awaitNanos(long nanosTimeout) throws InterruptedException {
        throw new NotImplementedException();
    }

    public boolean awaitUntil(Date deadline) throws InterruptedException {
        throw new NotImplementedException();
    }

    public void signal() {
        if (this.waitQueue.size() > 0) {
            Task t = this.waitQueue.removeFirst();
            logger.fine("Condition signaled, moved task " + t + " to the run queue.");
            this.runQueue.add(t);
        }
    }

    public void signalAll() {
        while (this.waitQueue.size() > 0) {
            Task t = this.waitQueue.removeFirst();
            logger.fine("Condition signaled ALL, moved task " + t + " to the run queue.");
            this.runQueue.add(t);
        }
    }
}
