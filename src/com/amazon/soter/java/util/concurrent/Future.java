package com.amazon.soter.java.util.concurrent;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.tasks.JavaTask;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;
import com.amazon.soter.java.util.concurrent.locks.Condition;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Soter implementation of java.util.concurrent.Future.
 *
 * @param <T> type of the returned value
 */
public class Future<T> implements java.util.concurrent.Future, Resource {
    private final Logger logger = Logger.getLogger(Condition.class.getName());

    private Task task;

    public Future(Task t) {
        task = t;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return task.isFinished();
    }

    public T get() throws InterruptedException, ExecutionException {
        JavaTask javaTask = (JavaTask) task;
        java.util.concurrent.Future future = javaTask.getFuture();

        if (!task.hasStarted() || !task.isFinished()) {
            /* Relinquish control until we can get the resource. */
            Checker.getChecker().yield(new ResourceNeededUpdate(this));

            /* de Morgan shows us that when we resume, we will have started and finished.
               Therefore, acquire and release immediately.
             */
            Checker.getChecker().updateState(new ResourceAcquiredUpdate(this));
            Checker.getChecker().updateState(new ResourceReleasedUpdate(this));
        }

        T result = (T) javaTask.getFuture().get();
        return result;
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return get();
    }

    @Override
    public boolean isAvailableFor(Task t) {
        return task.hasStarted() && task.isFinished();
    }

    @Override
    public Task getAssociatedTask() {
        return task;
    }

    @Override
    public boolean hasAssociatedTask() {
        return true;
    }
}
