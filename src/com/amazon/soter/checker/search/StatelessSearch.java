// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.search;

import com.amazon.soter.checker.SearchConfiguration;
import com.amazon.soter.checker.Strategy;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.strategies.Generator;

/** Implementation of stateless search. */
public class StatelessSearch extends Searcher {

    public StatelessSearch(Strategy strat) {
        super(strat);
    }

    /** Load configuration, if necessary. */
    public void updateConfiguration(SearchConfiguration searchConfiguration) { }

    @Override
    boolean hasNext() {
        return getStrat().getTaskInstance(getChecker().getEnabled()).hasNext();
    }

    @Override
    Task getNext() {
        Generator<Task> g = getStrat().getTaskInstance(getChecker().getEnabled());
        return g.getNext();
    }

    @Override
    void runNext(Task t) {
        getChecker().run(t);
    }

    @Override
    void prepareForNextIteration() {
        // no op
    }

    @Override
    int getNextIntegerChoice(int bound) {
        return getStrat().getIntInstance(bound).getNext();
    }

    @Override
    boolean getNextBooleanChoice() {
        return getStrat().getBoolInstance().getNext();
    }

}
