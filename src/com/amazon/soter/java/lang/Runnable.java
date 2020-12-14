package com.amazon.soter.java.lang;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.exceptions.InterruptedTerminateException;
import com.amazon.soter.checker.tasks.JavaTask;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;

/**
 * Soter implementation of the java.lang.Runnable.
 */
public class Runnable implements java.lang.Runnable {
    private Checker checker;
    private java.lang.Runnable runnable;
    private Update exitUpdate;
    private ForkJoinPool pool;

    public Runnable(java.lang.Runnable runnable, Checker c, Update exitUpdate) {
        this.checker = c;
        this.runnable = runnable;
    }

    public Runnable(java.lang.Runnable runnable, Checker c, Update exitUpdate, ForkJoinPool pool) {
        this.checker = c;
        this.runnable = runnable;
        this.pool = pool;
    }

    public void run() {
        try {
            runnable.run();
            checker.exitTask(exitUpdate);
        } catch (InterruptedTerminateException e) {
            checker.exitTask(exitUpdate);
        } catch (RuntimeException e) {
            e.printStackTrace();
            checker.abnormalExitTask(exitUpdate);
        }
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
