// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.search;

import com.amazon.soter.checker.SearchConfiguration;
import com.amazon.soter.checker.Strategy;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.strategies.Generator;

import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/** Implementation of DFS. */
public class DFS<T> extends Searcher<T> {
    private final Logger logger = Logger.getLogger(Searcher.class.getName());

    /** Stack of previously-made scheduling choices */
    private SchedulingChoices<Task> taskChoices = new SchedulingChoices<>();

    /** Stack of previously-made int choices */
    private SchedulingChoices<Integer> intChoices = new SchedulingChoices<>();

    /** Stack of previously-made bool choices */
    private SchedulingChoices<Boolean> boolChoices = new SchedulingChoices<>();

    /** DFS has finished exhaustively searching all scheduling choices */
    private boolean isFullyExploredChoices = false;

    /** DFS has finished exhaustively searching all int choices */
    private boolean isFullyExploredIntChoices = false;

    /** DFS has finished exhaustively searching all boolean choices */
    private boolean isFullyExploredBoolChoices = false;

    /** Scheduling choice made by DFS */
    private class SchedulingChoice<U> {
        /** Scheduling choice made that should be repeated next iteration */
        private U repeat;

        /** Generator representing backtrack set */
        private Generator<U> backtrack;
    }

    /** Scheduling choices made by DFS */
    private class SchedulingChoices<U> extends Stack<SchedulingChoice<U>> {
        private boolean clearEmpty() {
            while (!isEmpty() && !peek().backtrack.hasNext()) {
                pop();
            }
            if (!isEmpty()) {
                peek().repeat = null;
            }
            return isEmpty();
        }

        U getNextChoice(int depth, Callable<Generator<U>> nextGenerator) {
            SchedulingChoice<U> sc;
            if (depth < size()) {
                sc = get(depth);
            } else {
                sc = new SchedulingChoice<>();
                try {
                    sc.backtrack = nextGenerator.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                push(sc);
            }

            if (sc.repeat == null) {
                sc.repeat = sc.backtrack.getNext();
            }
            return sc.repeat;
        }
    }

    /** Load configuration, if necessary. */
    public void updateConfiguration(SearchConfiguration searchConfiguration) { }

    /** Make a new exhaustive DFS.
     *
     * @param strat The search strategy to use
     */
    public DFS(Strategy strat) {
        super(strat);
    }

    @Override
    boolean hasNext() {
        return getNext() != null;
    }

    @Override
    int getNextIntegerChoice(int bound) {
        return intChoices.getNextChoice(getIntChoiceDepth(), () -> getStrat().getIntInstance(bound));
    }

    @Override
    boolean getNextBooleanChoice() {
        return boolChoices.getNextChoice(getBoolChoiceDepth(), () -> getStrat().getBoolInstance());
    }

    @Override
    Task getNext() {
        return taskChoices.getNextChoice(getDepth(), () -> getStrat().getTaskInstance(getChecker().getEnabled()));
    }

    @Override
    void runNext(Task t) {
        // Need to lookup to update the task to the current iteration's version
        t = getChecker().lookupByID(t);
        logger.fine("Choice " + getDepth() + ": " + t.getTaskId());
        getChecker().run(t);
    }

    @Override
    void prepareForNextIteration() {
        isFullyExploredIntChoices = intChoices.clearEmpty();
        if (isFullyExploredIntChoices) {
            isFullyExploredBoolChoices = boolChoices.clearEmpty();
            isFullyExploredIntChoices = false; // reset ints
            if (isFullyExploredBoolChoices) {
                isFullyExploredChoices = taskChoices.clearEmpty();
                isFullyExploredBoolChoices = false; // reset bools
            }
        }
    }

    @Override
    boolean isDone() {
        return super.isDone() || isFullyExploredChoices;
    }
}
