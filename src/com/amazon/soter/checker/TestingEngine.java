// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.exceptions.ExceptionExpectedAssertionFailure;
import com.amazon.soter.checker.exceptions.ExceptionMismatchAssertionFailure;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.states.ResourceState;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.targets.JUnitTestingTarget;
import com.amazon.soter.checker.targets.TestingTarget;
import com.amazon.soter.checker.updates.NoopUpdate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Engine to run a test through JUnit. */
public class TestingEngine {

    /** Qualified namespace for dynamically loading the search classes. */
    public final String searchPackagePrefix = "com.amazon.soter.checker.search.";

    private SearchType searchType;
    private StrategyType strategyType;

    private int maxDepth;
    private int errorDepth;
    private int maxIterations;

    private State state;
    private Update initOp;
    private Update exitOp;

    private Object testObject;
    private Method testMethod;

    private long randomSeed;
    private String previousTraceFile;

    private TestingTarget testingTarget;
    private Class expectedException;

    private TestingEngine(SearchType searchType, StrategyType strategyType,
                          int maxDepth, int errorDepth, int maxIterations) {
        this.searchType = searchType;
        this.strategyType = strategyType;
        this.maxDepth = maxDepth;
        this.errorDepth = errorDepth;
        this.maxIterations = maxIterations;
    }

    public TestingEngine(SearchType searchType, StrategyType strategyType, int maxDepth,
                         int errorDepth, int maxIterations, TestingTarget testingTarget) {
        this(searchType, strategyType, maxDepth, errorDepth, maxIterations);
        this.state = new ResourceState();
        this.initOp = new NoopUpdate();
        this.exitOp = new NoopUpdate();
        this.testingTarget = testingTarget;
        this.randomSeed = -1;
        this.previousTraceFile = "";
    }

    public TestingEngine(SearchType searchType, StrategyType strategyType, int maxDepth, int errorDepth,
                         int maxIterations, TestingTarget testingTarget, String previousTraceFile) {
        this(searchType, strategyType, maxDepth, errorDepth, maxIterations);
        this.state = new ResourceState();
        this.initOp = new NoopUpdate();
        this.exitOp = new NoopUpdate();
        this.testingTarget = testingTarget;
        this.randomSeed = -1;
        this.previousTraceFile = previousTraceFile;
    }

    public TestingEngine(SearchType searchType, StrategyType strategyType, int maxDepth, int errorDepth,
                         int maxIterations, Object testObject, Method testMethod) {
        this(searchType, strategyType, maxDepth, errorDepth, maxIterations);
        this.state = new ResourceState();
        this.initOp = new NoopUpdate();
        this.exitOp = new NoopUpdate();
        this.testObject = testObject;
        this.testMethod = testMethod;
        this.randomSeed = -1;
        this.previousTraceFile = "";
    }

    public TestingEngine(SearchType searchType, StrategyType strategyType, int maxDepth, int errorDepth,
                         int maxIterations, Object testObject, Method testMethod, String previousTraceFile) {
        this(searchType, strategyType, maxDepth, errorDepth, maxIterations);
        this.state = new ResourceState();
        this.initOp = new NoopUpdate();
        this.exitOp = new NoopUpdate();
        this.testObject = testObject;
        this.testMethod = testMethod;
        this.previousTraceFile = previousTraceFile;
    }

    public TestingEngine(SearchType searchType, StrategyType strategyType, int maxDepth, int errorDepth,
                         int maxIterations, Object testObject, Method testMethod, long randomSeed,
                         String previousTraceFile) {
        this(searchType, strategyType, maxDepth, errorDepth, maxIterations);
        this.state = new ResourceState();
        this.initOp = new NoopUpdate();
        this.exitOp = new NoopUpdate();
        this.testObject = testObject;
        this.testMethod = testMethod;
        this.randomSeed = randomSeed;
        this.previousTraceFile = previousTraceFile;
    }

    public void run() {
        Strategy strategy = getStrategyInstance();
        Search search = getSearchInstance(strategy, maxDepth, errorDepth, maxIterations);

        /* Setup the testing target if we haven't explicitly been provided one. */
        if (testingTarget == null) {
            testingTarget = new JUnitTestingTarget(testObject, testMethod);
        }

        /* Setup the search configuration. */
        SearchConfiguration searchConfiguration = new SearchConfiguration(state, initOp, exitOp,
                randomSeed, previousTraceFile);

        /* Perform the search. */
        try {
            search.doSearch(testingTarget, searchConfiguration);

            // If we're expecting and we didn't throw, now we have a problem.
            if (expectedException != null && expectedException != Object.class) {
                throw new ExceptionExpectedAssertionFailure("Expected "
                        + expectedException + " but no exception was thrown!");
            }
        } catch (Exception e) {
            if (expectedException != null && expectedException != Object.class) {
                if (!expectedException.isInstance(e)) {
                    e.printStackTrace();
                    throw new ExceptionMismatchAssertionFailure("Expected "
                            + expectedException + " but " + e + " was thrown instead!");
                } else {
                    // Nothing needed, this is the test passing.
                }
            } else {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public void setExpectedException(Class c) {
        this.expectedException = c;
    }

    private Search getSearchInstance(Strategy strategy, int maxDepth, int errorDepth, int maxIterations) {
        Search search = null;
        Class searchClass = null;

        try {
            searchClass = Class.forName(searchPackagePrefix + searchType.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class[] searchClassArgs = new Class[1];
        searchClassArgs[0] = Strategy.class;

        try {
            search = (Search) searchClass.getDeclaredConstructor(searchClassArgs).newInstance(strategy);
            search.setMaxDepth(maxDepth);
            search.setErrorDepth(errorDepth);
            search.setMaxIterations(maxIterations);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return search;
    }

    private Strategy getStrategyInstance() {
        return new Strategy(strategyType);
    }
}
