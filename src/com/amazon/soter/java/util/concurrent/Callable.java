package com.amazon.soter.java.util.concurrent;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.exceptions.InterruptedTerminateException;
import com.amazon.soter.checker.tasks.JavaTask;

/**
 * Soter implementation of java.util.concurrent.Callable.
 *
 * @param <T> type of the return value.
 */
public class Callable<T> implements java.util.concurrent.Callable {
    private Checker checker;
    private java.util.concurrent.Callable callable;
    private Update exitUpdate;
    private ForkJoinPool pool;

    public Callable(java.util.concurrent.Callable callable, Checker c, Update exitUpdate) {
        this.checker = c;
        this.callable = callable;
    }

    public Callable(java.util.concurrent.Callable callable, Checker c, Update exitUpdate, ForkJoinPool pool) {
        this.checker = c;
        this.callable = callable;
        this.pool = pool;
    }

    public T call() throws Exception {
        T value = null;

        try {
            value = (T) callable.call();
            checker.exitTask(exitUpdate);
        } catch (InterruptedTerminateException e) {
            checker.exitTask(exitUpdate);
        } catch (Exception e) {
            throw e;
        }

        return value;
    }

    public void markFinished(JavaTask task) {
        if (this.pool != null) {
            pool.markFinished(task);
        }
    }

    public boolean isEnabled(JavaTask task) {
        if (this.pool != null) {
            return pool.isEnabled(task);
        }

        return true;
    }
}












































