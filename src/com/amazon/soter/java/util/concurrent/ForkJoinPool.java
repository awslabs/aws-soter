// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.java.util.concurrent;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.exceptions.NotImplementedException;
import com.amazon.soter.checker.tasks.JavaTask;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.tasks.TaskExecutionModel;
import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.java.lang.Runnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Soter implementation of java.util.concurrent.ForkJoinPool.
 */
public class ForkJoinPool {
    private int parallelism = 100;

    private Update defaultUpdate = new NoopUpdate();
    private List<Task> tasks = new ArrayList<Task>();
    private java.util.concurrent.ForkJoinPool pool;
    private LinkedBlockingQueue<Task> queue;

    private final Logger logger = Logger.getLogger(ForkJoinPool.class.getName());

    public ForkJoinPool(int parallelism) {
        this.parallelism = parallelism;
        this.queue = new LinkedBlockingQueue<Task>();
        this.pool = new java.util.concurrent.ForkJoinPool(parallelism);
    }

    public boolean awaitQuiescence(long timeout, TimeUnit unit) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.awaitQuiescence(timeout, unit);
    }

    public boolean awaitTermination(int timeout, TimeUnit timeunit) throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            logger.fine("Waiting for threads to terminate: " + tasks);
            boolean result = !Checker.getChecker().tasksStillAlive(tasks);
            logger.fine("-> result: " + result);
            Checker.getChecker().yield(defaultUpdate);
            return result;
        } else {
            return pool.awaitTermination(timeout, timeunit);
        }
    }

    public static ForkJoinPool commonPool() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    protected int drainTasksTo(Collection<? super ForkJoinTask<?>> c) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    public void execute(ForkJoinTask<?> task) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        pool.execute(task);
    }

    public void execute(java.lang.Runnable runnable) {
        if (Checker.isRuntimeControlled()) {
            logger.fine("Launching thread...");
            Runnable wrappedRunnable = new Runnable(runnable, Checker.getChecker(), defaultUpdate, this);
            Task task = new JavaTask(wrappedRunnable, TaskExecutionModel.EXECUTE);
            Checker.getChecker().createTask(task);
            tasks.add(task);
            queue.add(task);
            Checker.getChecker().yield(defaultUpdate);
        } else {
            pool.execute(runnable);
        }
    }

    public int getActiveThreadCount() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getActiveThreadCount();
    }

    public boolean getAsyncMode() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getAsyncMode();
    }

    public static int getCommonPoolParallelism() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    public Object getFactory() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getFactory();
    }

    public int getParallelism() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getParallelism();
    }

    public int getPoolSize() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getPoolSize();
    }

    public int getQueuedSubmissionCount() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getQueuedSubmissionCount();
    }

    public long getQueuedTaskCount() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getQueuedTaskCount();
    }

    public int getRunningThreadCount() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getRunningThreadCount();
    }

    public long getStealCount() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getStealCount();
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.getUncaughtExceptionHandler();
    }

    public boolean hasQueuedSubmissions() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.hasQueuedSubmissions();
    }

    public <T> T invoke(ForkJoinTask<T> task) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.invoke(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    public boolean isQuiescent() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.isQuiescent();
    }

    public boolean isShutdown() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.isShutdown();
    }

    public boolean isTerminated() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.isTerminated();
    }

    public boolean isTerminating() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.isTerminating();
    }

    public static void managedBlock(Object blocker) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    protected ForkJoinTask<?> pollSubmission() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        throw new NotImplementedException();
    }

    public void shutdown() {
        if (Checker.isRuntimeControlled()) {
            // Do nothing for the moment, here to keep the thread interface the same
            // between our instrumented thread pool and the user's thread pool.
        } else {
            this.pool.shutdown();
        }
    }

    public List<java.lang.Runnable> shutdownNow() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.shutdownNow();
    }

    public java.util.concurrent.Future submit(java.util.concurrent.Callable callable) {
        if (Checker.isRuntimeControlled()) {
            Callable wrappedCallable = new Callable(callable, Checker.getChecker(), defaultUpdate, this);
            Task task = new JavaTask(wrappedCallable, TaskExecutionModel.SUBMIT);
            Checker.getChecker().createTask(task);
            tasks.add(task);
            queue.add(task);
            return new Future(task);
        } else {
            return pool.submit(callable);
        }
    }

    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.submit(task);
    }

    public java.util.concurrent.Future submit(java.lang.Runnable runnable) {
        if (Checker.isRuntimeControlled()) {
            Runnable wrappedCallable = new Runnable(runnable, Checker.getChecker(), defaultUpdate, this);
            Task task = new JavaTask(wrappedCallable, TaskExecutionModel.SUBMIT);
            Checker.getChecker().createTask(task);
            tasks.add(task);
            queue.add(task);
            return new Future(task);
        } else {
            return pool.submit(runnable);
        }
    }

    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.submit(task, result);
    }

    public String toString() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return pool.toString();
    }

    public void markFinished(Task t) {
        this.queue.remove(t);
    }

    public boolean isEnabled(Task t) {
        logger.fine("Checking if task " + t + " can be scheduled.");

        int position = Arrays.asList(queue.toArray()).indexOf(t);
        logger.fine("=> Arrays.asList(queue): " + Arrays.asList(queue));
        logger.fine("=> Queue: " + queue);
        logger.fine("=> task is at position: " + position + " and parallelism is: " + parallelism);

        if (position < parallelism) {
            return true;
        }

        return false;
    }
}
