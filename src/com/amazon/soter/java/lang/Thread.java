package com.amazon.soter.java.lang;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.tasks.JavaTask;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.tasks.TaskExecutionModel;
import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;

/**
 * Soter implementation of java.lang.Thread.
 */
public abstract class Thread implements java.lang.Runnable, Resource {
    private Task task;
    private java.lang.Thread thread;

    public boolean isAvailableFor(Task t) {
        return !Checker.getChecker().isTaskAlive(task);
    }

    public Task getAssociatedTask() {
        return this.task;
    }

    public boolean hasAssociatedTask() {
        return true;
    }

    public void start() {
        if (Checker.isRuntimeControlled()) {
            Runnable wrappedRunnable = new Runnable(this, Checker.getChecker(), new NoopUpdate());
            Task task = new JavaTask(wrappedRunnable, TaskExecutionModel.EXECUTE);
            Checker.getChecker().createTask(task);
            this.task = task;
            Checker.getChecker().yield(new NoopUpdate());
        } else {
            java.lang.Thread t = new java.lang.Thread(this);
            t.start();
            thread = t;
        }
    }

    public void join() throws InterruptedException {
        if (Checker.isRuntimeControlled()) {

            // Here, we will relinquish control until the resource is acquired and released (thread completion.)
            Checker.getChecker().yield(new ResourceNeededUpdate(this));

            // When control resumes, I will have the resource (Thread), but the thread will also be completed.
            Checker.getChecker().updateState(new ResourceAcquiredUpdate(this));
            Checker.getChecker().updateState(new ResourceReleasedUpdate(this));

        } else {
            thread.join();
        }
    }

    public static void sleep(int millis) throws InterruptedException {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().yield(new NoopUpdate());
        } else {
            java.lang.Thread.sleep(millis);
        }
    }
}
