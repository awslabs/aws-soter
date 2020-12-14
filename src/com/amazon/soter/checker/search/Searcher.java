// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.search;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.Search;
import com.amazon.soter.checker.SearchConfiguration;
import com.amazon.soter.checker.Strategy;
import com.amazon.soter.checker.config.Config;

import com.amazon.soter.checker.targets.TargetExecutionType;
import com.amazon.soter.checker.targets.TestingTarget;
import com.amazon.soter.checker.tasks.JavaTask;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.tasks.TaskExecutionModel;
import com.amazon.soter.checker.tasks.TaskId;
import com.amazon.soter.checker.trace.ScheduleTrace;
import com.amazon.soter.service.SoterServerGrpc;

import com.amazon.soter.checker.exceptions.CounterexampleFoundException;
import com.amazon.soter.checker.exceptions.DeadlockDetectedException;
import com.amazon.soter.checker.exceptions.DepthBoundExceededException;
import com.amazon.soter.checker.exceptions.InterruptedTerminateException;
import com.amazon.soter.checker.exceptions.InternalSystemFailureException;
import com.amazon.soter.checker.exceptions.InvalidExecutionTypeException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/** Search abstract class.*/
public abstract class Searcher<T> implements Search {
    /** Logger. */
    public final Logger logger = Logger.getLogger(Search.class.getName());

    /** Lock for the Search running. */
    private ReentrantLock runLock = new ReentrantLock();

    /** Condition that the Search waits on in order to resume running. */
    private Condition runCond = runLock.newCondition();

    /** Is the Search currently running? */
    private boolean isRunning = false;

    /** Strategy used by the Search to pick the next Task. */
    private Strategy strat;

    /** The current Checker for the Search iteration. */
    private Checker checker;

    /** The number of iterations explored so far. */
    private int iteration = 0;

    /** The maximum number of iterations allowed. If negative, no limit. */
    private int maxIterations = -1;

    /** Current iteration's depth */
    private int depth = 0;

    /** Current iteration's number of nondeterministic integer choices made. */
    private int intChoiceDepth = 0;

    /** Current iteration's number of nondeterministic boolean choices made. */
    private int boolChoiceDepth = 0;

    /** The maximum depth per iteration allowed. If negative, no limit. */
    private int maxDepth = -1;

    /**
     * The maximum depth allowed to be reached before an exception is thrown. If
     * negative, no limit.
     */
    private int errorDepth = -1;

    /** Trace for executions. */
    private ScheduleTrace scheduleTrace;

    /** Have we started the servers yet? */
    private boolean serverStarted = false;

    /** gRPC server instance. */
    private SoterServerGrpc soterServerGrpc;

    /**
     * Make a new Searcher instance without max depth per iteration.
     *
     * @param strat The strategy for the search
     */
    public Searcher(Strategy strat) {
        this.strat = strat;
    }

    /**
     * Make a new Searcher instance max depth per iteration.
     *
     * @param strat    The strategy for the search
     * @param maxDepth The maximum depth for the search per iteration
     */
    public Searcher(Strategy strat, int maxDepth) {
        this.strat = strat;
        this.maxDepth = maxDepth;
    }

    /**
     * Make a new Searcher instance max depth per iteration.
     *
     * @param strat         The strategy for the search
     * @param maxDepth      The maximum depth for the search per iteration
     * @param errorDepth    The maximum depth allowed to be reached before an
     *                      exception is thrown
     * @param maxIterations The maximum number of iterations for the search per
     *                      iteration
     */
    public Searcher(Strategy strat, int maxDepth, int errorDepth, int maxIterations) {
        this.strat = strat;
        this.maxDepth = maxDepth;
        this.errorDepth = errorDepth;
        this.maxIterations = maxIterations;
    }

    /**
     * Return the current strategy.
     *
     * @return the current strategy.
     */
    public Strategy getStrat() {
        return strat;
    }

    /**
     * Return the instance of the checker.
     *
     * @return the current checker.
     */
    public Checker getChecker() {
        return checker;
    }

    @Override
    public void unblock() {
        logger.fine("try to unblock");
        runLock.lock();
        try {
            isRunning = true;
            runCond.signal();
        } finally {
            runLock.unlock();
        }
    }

    /** Runs the next Task and blocks the Search */
    void runNextAndBlock() {
        logger.fine("try to block");
        runLock.lock();
        try {
            Task t = getNext();
            scheduleTrace.addSchedulingDecision(t);
            runNext(t);
            depth++;
            isRunning = false;
            while (!isRunning) {
                runCond.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            runLock.unlock();
        }
    }

    /**
     * Set the max depth the search can reach before considering it to have reached
     * an error.
     */
    public void setErrorDepth(int errorDepth) {
        this.errorDepth = errorDepth;
    }

    /** Configure the max depth for the search. */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /** Configure the number of iterations the search should run. */
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int getDepth() {
        return depth;
    }

    public int getBoolChoiceDepth() {
        return boolChoiceDepth;
    }

    public int getIntChoiceDepth() {
        return intChoiceDepth;
    }

    /**
     * Determines whether anything is left to be scheduled
     *
     * @return true iff there is a Task that can be scheduled
     */
    abstract boolean hasNext();

    /** Runs a task. */
    abstract void runNext(Task t);

    /**
     * Get the next task scheduling choice
     *
     * @return The next Task to be scheduled
     */
    abstract Task getNext();

    /** Resets any state and does any bookkeeping tasks in between iterations. */
    abstract void prepareForNextIteration();

    /**
     * Determine whether the Search is done or not.
     *
     * @return true iff the Search should terminate
     */
    boolean isDone() {
        return maxIterations >= 0 && iteration >= maxIterations;
    }

    /**
     * Determine whether the Search is done with this iteration or not.
     *
     * @return true iff the Search should terminate this iteration
     */
    boolean isDoneIteration() {
        return maxDepth >= 0 && depth >= maxDepth;
    }

    /**
     * Determine whether or not the depth has been exceeded.
     *
     * @return true iff the depth has been exceeded.
     */
    boolean depthExceeded() {
        return errorDepth >= 0 && depth > errorDepth;
    }

    /**
     * Get the next Integer choice.
     *
     * @return The next Integer to be chosen
     */
    abstract int getNextIntegerChoice(int bound);

    /**
     * Get the next Boolean choice.
     *
     * @return The next Boolean to be chosen
     */
    abstract boolean getNextBooleanChoice();

    /**
     * Get and log the next Integer choice.
     *
     * @param bound upper bound (exclusive) on the random integer.
     * @return The next Integer to be scheduled
     */
    public int getNextInteger(int bound) {
        int x = getNextIntegerChoice(bound);
        scheduleTrace.addSchedulingDecision(x);
        intChoiceDepth++;
        return x;
    }

    /**
     * Get and log the next Integer choice.
     *
     * @return The next Integer to be scheduled
     */
    public int getNextInteger() {
        int x = getStrat().getNondetInt();
        scheduleTrace.addSchedulingDecision(x);
        intChoiceDepth++;
        return x;
    }

    /**
     * Get and log the next Boolean choice.
     *
     * @return The next Boolean to be chosen
     */
    public boolean getNextBoolean() {
        boolean x = getNextBooleanChoice();
        scheduleTrace.addSchedulingDecision(x);
        boolChoiceDepth++;
        return x;
    }

    /**
     * Determine if the checker has exited because of an abnormal exit.
     *
     * @return true iff at least one thread has had an abnormal exit.
     */
    public boolean abnormalExit() {
        return getChecker().numAbnormalExits() > 0;
    }

    /** Initialize a new trace instance. */
    public void initializeTrace() {
        scheduleTrace = new ScheduleTrace();
    }

    /** Dump the trace to a file. */
    public void dumpTraceToFile() {
        scheduleTrace.dumpToFile();
    }

    /** Read the search configuration. */
    public abstract void updateConfiguration(SearchConfiguration searchConfiguration);

    private void shutdownServer() {
        if (serverStarted) {
            try {
                soterServerGrpc.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.severe("Failed to stop the gRPC server.");
            }
        }
    }

    @Override
    public void doSearch(TestingTarget target, SearchConfiguration searchConfiguration) {
        updateConfiguration(searchConfiguration);
        while (!isDone()) {
            TaskId.reset();
            depth = 0;
            intChoiceDepth = 0;
            boolChoiceDepth = 0;
            logger.fine("------------------------------------------------------------------");
            logger.fine("Starting iteration " + String.valueOf(iteration + 1) + ".");
            System.out.println("Starting iteration " + String.valueOf(iteration + 1) + ".");

            // initialize checker, start running entry, which blocks almost immediately
            this.checker = Checker.createChecker(strat, searchConfiguration.getInitState(),
                    this, searchConfiguration.getRandomSeed());

            Task mainTask = new JavaTask(() -> {

                if (target.getExecutionType() == TargetExecutionType.Java) {
                    checker.yield(searchConfiguration
                            .getInitOp()); /* Yield the main/root program immediately, wait for checker to start it. */
                    logger.fine("Root yield returning. ");
                    logger.fine("------------------------------------------------------------------");
                    try {
                        target.run(checker);
                        checker.exitTask(searchConfiguration.getExitOp());
                    } catch (InterruptedTerminateException e) {
                        // Nothing, just exit.
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        checker.abnormalExitTask(searchConfiguration.getExitOp());
                    } catch (Throwable e) {
                        e.printStackTrace();
                        checker.abnormalExitTask(searchConfiguration.getExitOp());
                    }

                } else if (target.getExecutionType() == TargetExecutionType.External) {
                    if (!serverStarted) {
                        /* Start the Soter GRPC server. */
                        soterServerGrpc = new SoterServerGrpc();
                        try {
                            soterServerGrpc.start();
                            serverStarted = true;
                        } catch (Exception ie) {
                            ie.printStackTrace();
                            throw new InternalSystemFailureException();
                        }
                    }

                    try {
                        /* Yield the main/root program immediately, wait for checker to start it.  */
                        checker.yield(searchConfiguration.getInitOp());
                        target.run(checker);
                        checker.exitTask(searchConfiguration.getExitOp());
                    } catch (Throwable e) {
                        e.printStackTrace();
                        checker.abnormalExitTask(searchConfiguration.getExitOp());
                    }

                } else {
                    throw new InvalidExecutionTypeException();
                }

            }, TaskExecutionModel.EXECUTE);

            checker.createTask(mainTask, searchConfiguration.getInitOp());

            initializeTrace();

            // do the search
            while (!isDoneIteration() && hasNext()) {
                runNextAndBlock();
            }

            if (!hasNext() && Checker.getChecker().getState().isDeadlocked()) {
                /* Look for a legitimate deadlock. */
                dumpTraceToFile();
                shutdownServer();
                throw new DeadlockDetectedException();
            } else if (depthExceeded()) {
                /* Did we exceed the search depth? */

                logger.severe("Error, depth exceeded at iteration " + (iteration + 1));
                if (Config.DEPTH_BOUND_CONSIDERED_BUG) {
                    dumpTraceToFile();
                    shutdownServer();
                    throw new DepthBoundExceededException();
                }
            } else if (abnormalExit()) {
                /* Any of the tasks exited abnormally during the execution, this indicates a problem. */

                if (!(this instanceof ReplaySearch)) {
                    dumpTraceToFile();
                    logger.severe("Error, counterexample identified at iteration "
                            + (iteration + 1) + " and trace file produced.");
                } else {
                    logger.severe("Reproduced failure using counterexample trace.");
                }
                shutdownServer();
                throw new CounterexampleFoundException();
            }

            prepareForNextIteration();
            System.gc();
            this.getChecker().stop();
            logger.fine("Finishing iteration " + String.valueOf(iteration + 1) + ".");
            logger.fine("------------------------------------------------------------------");
            iteration++;
        }

        shutdownServer();
    }
}
