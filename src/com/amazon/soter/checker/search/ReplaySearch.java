// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.search;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.SearchConfiguration;
import com.amazon.soter.checker.Strategy;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.trace.ScheduleStep;
import com.amazon.soter.checker.trace.ScheduleTrace;
import com.amazon.soter.checker.trace.exceptions.InvalidOperationException;
import com.amazon.soter.checker.trace.steps.BooleanChoice;
import com.amazon.soter.checker.trace.steps.IntegerChoice;
import com.amazon.soter.checker.trace.steps.SchedulingChoice;

import java.util.logging.Logger;

/** Implementation of deterministic replay for counterexamples. */
public class ReplaySearch<T> extends Searcher<T> {
    private ScheduleTrace previousScheduleTrace;
    private int scheduledSteps;
    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public ReplaySearch(Strategy strat) {
        super(strat);
    }

    /** Load configuration, if necessary. */
    public void updateConfiguration(SearchConfiguration searchConfiguration) {
        loadPreviousTrace(searchConfiguration.getPreviousTraceFile());
    }

    @Override
    boolean hasNext() {
        if (this.scheduledSteps >= this.previousScheduleTrace.size()) {
            return false;
        }

        ScheduleStep nextStep = previousScheduleTrace.get(scheduledSteps);

        if (!(nextStep instanceof SchedulingChoice)) {
            logger.severe("Assuming next step is scheduling choice, but next step is actually " + nextStep);
            throw new InvalidOperationException("Assuming next step is scheduling choice, but next step is actually "
                    + nextStep);
        }

        return true;
    }

    @Override
    Task getNext() {
        if (this.scheduledSteps >= this.previousScheduleTrace.size()) {
            logger.severe("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
            throw new InvalidOperationException("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
        }

        ScheduleStep nextStep = previousScheduleTrace.get(scheduledSteps);

        if (!(nextStep instanceof SchedulingChoice)) {
            logger.severe("Assuming next step is scheduling choice, but next step is actually "
                    + nextStep);
            throw new InvalidOperationException("Assuming next step is scheduling choice, but next step is actually "
                    + nextStep);
        }

        SchedulingChoice sc = (SchedulingChoice) nextStep;

        this.scheduledSteps++;
        Task t = getChecker().lookupByID(sc.getId());
        return t;
    }

    @Override
    void runNext(Task t) {
        getChecker().run(t);
    }

    @Override
    public int getNextInteger() {
        if (this.scheduledSteps >= this.previousScheduleTrace.size()) {
            logger.severe("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
            throw new InvalidOperationException("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
        }

        ScheduleStep nextStep = previousScheduleTrace.get(scheduledSteps);

        if (!(nextStep instanceof IntegerChoice)) {
            logger.severe("Assuming next step is integer choice, but next step is actually " + nextStep);
            throw new InvalidOperationException("Assuming next step is integer choice, but next step is actually "
                    + nextStep);
        }

        IntegerChoice ic = (IntegerChoice) nextStep;
        this.scheduledSteps++;
        return ic.getValue();
    }

    @Override
    public int getNextInteger(int bound) {
        return getNextIntegerChoice(bound);
    }

    @Override
    public boolean getNextBoolean() {
        return getNextBooleanChoice();
    }

    @Override
    int getNextIntegerChoice(int bound) {
        if (this.scheduledSteps >= this.previousScheduleTrace.size()) {
            logger.severe("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
            throw new InvalidOperationException("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
        }

        ScheduleStep nextStep = previousScheduleTrace.get(scheduledSteps);

        if (!(nextStep instanceof IntegerChoice)) {
            logger.severe("Assuming next step is integer choice, but next step is actually " + nextStep);
            throw new InvalidOperationException("Assuming next step is integer choice, but next step is actually "
                    + nextStep);
        }

        IntegerChoice ic = (IntegerChoice) nextStep;
        this.scheduledSteps++;
        return ic.getValue();
    }

    @Override
    boolean getNextBooleanChoice() {
        if (this.scheduledSteps >= this.previousScheduleTrace.size()) {
            logger.severe("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
            throw new InvalidOperationException("Trace file contains " + this.previousScheduleTrace
                    + " steps but the next step would be step " + this.scheduledSteps);
        }

        ScheduleStep nextStep = previousScheduleTrace.get(scheduledSteps);

        if (!(nextStep instanceof BooleanChoice)) {
            logger.severe("Assuming next step is boolean choice, but next step is actually " + nextStep);
            throw new InvalidOperationException("Assuming next step is boolean choice, but next step is actually "
                    + nextStep);
        }

        BooleanChoice bc = (BooleanChoice) nextStep;
        this.scheduledSteps++;
        return bc.getValue();
    }

    @Override
    void prepareForNextIteration() {
        this.scheduledSteps = 0;
    }

    private void loadPreviousTrace(String previousTraceFile) {
        previousScheduleTrace = ScheduleTrace.loadFromFile(previousTraceFile);
        logger.info("Loaded previous trace file: " + previousTraceFile);
    }
}
