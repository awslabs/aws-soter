// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.config.Config;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.exceptions.InterruptedTerminateException;
import com.amazon.soter.checker.exceptions.UpdateMismatchException;
import com.amazon.soter.checker.updates.NoopUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Checker {
    private List<Task> tasks;
    private Task running = null;
    private State state;
    private Search search;

    private int abnormalExits = 0;

    private long randomSeed;

    private ForkJoinPool threadPool = new ForkJoinPool(100);
    private boolean shutdown = false;
    private final Logger logger = Logger.getLogger(Checker.class.getName());
    private Map<Task, Condition> runConditions;
    private ReentrantLock run = new ReentrantLock();

    private static Checker instance = null;

    private static boolean runtimeControlled = false;

    public static Checker getChecker() {
        return instance;
    }

    public static boolean isRuntimeControlled() {
        return runtimeControlled;
    }

    public long getRandomSeed() {
        return this.randomSeed;
    }

    public ForkJoinPool getThreadPool() {
        return this.threadPool;
    }

    public static Checker createChecker(Strategy strat, State state, Search search, long randomSeed) {
        /* Create new instance of the checker using factory, assign to static. */
        Checker c = new Checker(strat, state, search, randomSeed);
        Checker.instance = c;
        Checker.runtimeControlled = true;

        /* Gradle is using it's own logger and overriding the custom logger, this somehow is
           causing java to not read the logging.properties file, so for now, just manually
           switch the logging level in the configuration.

           This is discussed in issue GRADLE-2524, I tried what the issue recommended using a build.gradle
           configuration but that removed all logging, so this is good enough for now and works until
           we need something better.
         */
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Config.LOG_LEVEL);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Config.LOG_LEVEL);
        }

        return c;
    }

    public boolean isTaskAlive(Task t) {
        return tasks.contains(t);
    }

    public void stop() {
        run.lock();
        try {
            logger.fine("Shutting down...");
            threadPool.shutdownNow();
            shutdown = true;
        } finally {
            run.unlock();
        }
        while (!threadPool.isTerminated()) {
            // Nothing for the moment.
        }
        logger.fine("Shutdown complete");
    }

    public Checker(Strategy strat, State state, Search search, long randomSeed) {
        this.tasks = new ArrayList<Task>();
        this.runConditions = new HashMap<Task, Condition>();
        this.state = state;
        this.search = search;
        this.randomSeed = randomSeed;
    }

    public Task getRunningTask() {
        return running;
    }

    public void startExternalTask(Task task) {
        run.lock();
        try {
            System.out.println("Task " + task + "is now started and schedulable.");
            task.markEnabled();
        } catch (Exception e) {
            throw new InterruptedTerminateException();
        } finally {
            run.unlock();
        }
    }

    public void blockExternalTask(Task task) {
        run.lock();
        try {
            System.out.println("About to block for task: " + task);
            Condition cond = runConditions.get(task);
            cond.await();
            System.out.println("Task " + task.getRawId() + " is unblocked.");
            task.resume();
        } catch (InterruptedException e) {
            throw new InterruptedTerminateException();
        } finally {
            run.unlock();
        }
    }

    public void yieldExternalTask() {
        yieldExternalTask(new NoopUpdate());
    }

    public void yieldExternalTask(Update update) {
        run.lock();
        try {
            Task task = running;
            System.out.println("Task " + task + " is yielding with update " + update);
            running = null;

            try {
                logger.fine("Updating state");
                state.update(task, update);
            } catch (UpdateMismatchException e) {
                e.printStackTrace();
            }

            System.out.println("Attempting to unblock.");
            System.out.println("All tasks: " + tasks);
            System.out.println("Scheduable tasks: " + getEnabled());

            search.unblock();
            System.out.println("Unblocked.");

            if (task == null) {
                // System.out.println("Task is null, returning.");
                return;
            }

            // Right here, running is set to NULL when bug occurs.
            // System.out.println("Running is now: " + running);
            // System.out.println("Task is now: " + task.getRawId());

            Condition cond = runConditions.get(task);
            // Current
            while (running == null || !running.equals(task)) {
                System.out.println("Awaiting for task: " + task.getRawId());
                cond.await();
                task.resume();
            }
        } catch (Exception e) {
            throw new InterruptedTerminateException();
        } finally {
            System.out.println("Out of the yield.");
            run.unlock();
        }
    }

    public void cancelExternalTask(Task task) {
        run.lock();
        try {
            // Remove from schedulable tasks.
            tasks.remove(task);

            // Update state (if necessary.)
            state.removeThread(task);

            // Remove condition variable for this thread.
            runConditions.remove(task);

            // Unblock someone who was waiting, then exit.
            search.unblock();
        } finally {
            run.unlock();
        }
    }

    public void abnormalExitExternalTask() {
        abnormalExits++;
        exitExternalTask();
    }

    public void exitExternalTask() {
        run.lock();
        try {
            Task task = running;

            if (running == null) {
                logger.fine("This is BAD BAD BAD, the process exiting is not scheduled to run!");
            }

            logger.fine("Task " + task + " is exiting!");
            running = null;

            // Mark task as finished.
            if (task != null) {
                task.markFinished();
            }

            // Remove from schedulable tasks.
            tasks.remove(task);

            // Update state (if necessary.)
            state.removeThread(task);

            // Remove condition variable for this thread.
            runConditions.remove(task);

            // Unblock someone who was waiting, then exit.
            search.unblock();
        } finally {
            run.unlock();
        }
    }

    public void yield(Update update) {
        run.lock();
        try {
            Task task = running;
            logger.fine("Task " + task + " is yielding with update " + update);
            running = null;

            try {
                logger.fine("Updating state");
                state.update(task, update);
            } catch (UpdateMismatchException e) {
                e.printStackTrace();
            }

            logger.fine("Attempting to unblock.");
            logger.fine("All tasks: " + tasks);
            logger.fine("Scheduable tasks: " + getEnabled());

            search.unblock();
            logger.fine("Unblocked.");

            if (task == null) {
                logger.fine("Task is null, returning.");
                return;
            }

            // Right here, running is set to NULL when bug occurs.
            logger.fine("Running is now: " + running);
            logger.fine("Task is now: " + task);

            Condition cond = runConditions.get(task);
            // Current
            while (running == null || !running.equals(task)) {
                logger.fine("Awaiting for task " + task);
                cond.await();
                task.resume();
            }
        } catch (InterruptedException e) {
            throw new InterruptedTerminateException();
        } finally {
            run.unlock();
        }
    }

    public void updateState(Update update) {
        run.lock();
        try {
            try {
                Task task = running;
                state.update(task, update);
            } catch (UpdateMismatchException e) {
                e.printStackTrace();
            }
        } finally {
            run.unlock();
        }
    }

    boolean isEnabled(Task thread) {
        return thread.isEnabled() && state.isEnabled(thread);
    }

    public int numAbnormalExits() {
        return this.abnormalExits;
    }

    public void createTask(Task task) {
        createTask(task, new NoopUpdate());
    }

    public void createTask(Task task, Update initOp) {
        run.lock();
        try {
            logger.fine("Creating task " + task);
            tasks.add(task);
            state.addThread(task, initOp);
            runConditions.put(task, run.newCondition());
        } finally {
            logger.fine("Tasks is now: " + tasks);
            logger.fine("Enabled tasks is now: " + getEnabled());
            run.unlock();
        }
    }

    public boolean tasksStillAlive(List<Task> tasks) {
        boolean result = false;

        for (Task e : tasks) {
            if (this.tasks.contains(e)) {
                result = true;
                break;
            }
        }

        return result && !shutdown;
    }

    public void exitTask(Update update) {
        run.lock();
        try {
            Task task = running;

            if (running == null) {
                logger.fine("This is BAD BAD BAD, the process exiting is not scheduled to run!");
            }

            logger.fine("Task " + task + " is exiting!");
            running = null;

            // Mark task as finished.
            if (task != null) {
                task.markFinished();
            }

            // Remove from schedulable tasks.
            tasks.remove(task);

            // Update state (if necessary.)
            state.removeThread(task);

            // Remove condition variable for this thread.
            runConditions.remove(task);

            // Unblock someone who was waiting, then exit.
            search.unblock();
        } finally {
            run.unlock();
        }
    }

    public void abnormalExitTask(Update update) {
        abnormalExits++;
        exitTask(update); // Eventually do something else special for crashes.
    }

    public void run(Task task) {
        run.lock();
        try {
            logger.fine("Task " + task + " is scheduled.");
            running = task;

            // If the task is already running, then signal the condition variable.
            if (task.isRunning()) {
                logger.fine("Running is set to task " + task);
                runConditions.get(task).signal();
            } else {
                logger.fine("Task " + task + " is not running yet, starting.");
                logger.fine("Current queued task count: " + this.threadPool.getQueuedTaskCount());
                logger.fine("Current running task count: " + this.threadPool.getRunningThreadCount());
                logger.fine("Current queued submissions count: " + this.threadPool.getQueuedSubmissionCount());
                logger.fine("Current pool size: " + this.threadPool.getPoolSize());
                logger.fine("Thread pool: " + this.threadPool);

                task.run();
            }

        } finally {
            run.unlock();
        }
    }

    public State getState() {
        run.lock();
        try {
            return state;
        } finally {
            run.unlock();
        }
    }

    public List<Task> getTasks() {
        run.lock();
        try {
            return tasks;
        } finally {
            run.unlock();
        }
    }


    public List<Task> getEnabled() {
        run.lock();
        try {
            List<Task> enabled = tasks.stream().filter(t -> isEnabled(t)).collect(Collectors.toList());
            logger.fine("Enabled are: " + enabled);
            return enabled;

        } finally {
            run.unlock();
        }
    }


    public int nextInt() {
        return search.getNextInteger();
    }

    public int nextInt(int bound) {
        return search.getNextInteger(bound);
    }

    public boolean nextBoolean() {
        return search.getNextBoolean();
    }

    /** Look up a current task in the Checker with the same ID
     * as the provided task.
     *
     * @param t The task whose ID the looked-up task should share
     * @return The current task in the Checker with the same ID
     */
    public Task lookupByID(Task t) {
        run.lock();
        try {
            for (Task task : tasks) {
                if (t.getTaskId().equals(task.getTaskId())) {
                    return task;
                }
            }
            return null;
        } finally {
            run.unlock();
        }
    }

    public Task lookupByID(long l) {
        run.lock();
        try {
            for (Task task : tasks) {
                if (l == task.getRawId()) {
                    return task;
                }
            }
            return null;
        } finally {
            run.unlock();
        }
    }
}
