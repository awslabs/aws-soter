package com.amazon.soter.java.util.concurrent;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.config.Config;
import com.amazon.soter.checker.exceptions.NotImplementedException;

import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/** Soter implementation of java.util.concurrent.Executors. */
public final class Executors {
    private Executors() {

    }

    public static java.util.concurrent.Callable<Object> callable(PrivilegedAction<?> action) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.callable(action);
    }

    public static java.util.concurrent.Callable<Object> callable(PrivilegedExceptionAction<?> action) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.callable(action);
    }

    public static java.util.concurrent.Callable<Object> callable(Runnable task) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.callable(task);
    }

    public static <T> java.util.concurrent.Callable<T> callable(Runnable task, T result) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.callable(task, result);
    }

    public static ThreadFactory defaultThreadFactory() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.defaultThreadFactory();
    }

    public static ExecutorService newCachedThreadPool() {
        if (Checker.isRuntimeControlled()) {
            return new ForkJoinPool(Config.MAX_THREADS);
        }

        return java.util.concurrent.Executors.newCachedThreadPool();
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newCachedThreadPool(threadFactory);
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        if (Checker.isRuntimeControlled()) {
            return new ForkJoinPool(nThreads);
        }

        return java.util.concurrent.Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newFixedThreadPool(nThreads, threadFactory);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newScheduledThreadPool(corePoolSize);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

    public static ExecutorService newSingleThreadExecutor() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newSingleThreadExecutor();
    }

    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newSingleThreadExecutor(threadFactory);
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    public static <T> java.util.concurrent.Callable<T> privilegedCallable(Callable<T> callable) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.privilegedCallable(callable);
    }

    public static <T> java.util.concurrent.Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> callable) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.privilegedCallableUsingCurrentClassLoader(callable);
    }

    public static ThreadFactory privilegedThreadFactory() {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.privilegedThreadFactory();
    }

    public static ExecutorService unconfigurableExecutorService(ExecutorService executor) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.unconfigurableExecutorService(executor);
    }

    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService executor) {
        if (Checker.isRuntimeControlled()) {
            throw new NotImplementedException();
        }

        return java.util.concurrent.Executors.unconfigurableScheduledExecutorService(executor);
    }
}
