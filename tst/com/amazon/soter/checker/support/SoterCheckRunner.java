// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.support;

import com.amazon.soter.checker.Search;
import com.amazon.soter.checker.TestingEngine;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class SoterCheckRunner extends Runner {
    public final Logger logger = Logger.getLogger(Search.class.getName());

    private Class testClass;

    public SoterCheckRunner(Class testClass) {
        super();
        this.testClass = testClass;
    }

    @Override
    public Description getDescription() {
        return Description
                .createTestDescription(testClass, "Soter test runner.");
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            Object testObject = testClass.getDeclaredConstructor().newInstance();
            for (Method method : testClass.getMethods()) {
                if (method.isAnnotationPresent(SoterCheck.class)) {
                    SoterCheck st = method.getAnnotation(SoterCheck.class);

                    SearchType searchType = st.search();
                    StrategyType strategyType = st.strategy();
                    int maxDepth = st.maxDepth();
                    int errorDepth = st.errorDepth();
                    int maxIterations = st.maxIterations();
                    Class expected = st.expected();
                    long randomSeed = st.randomSeed();
                    String previousTraceFile = st.previousTraceFile();

                    notifier.fireTestStarted(Description.createTestDescription(testClass, method.getName()));

                    try {
                        TestingEngine te = new TestingEngine(searchType, strategyType, maxDepth, errorDepth, maxIterations, testObject, method, randomSeed, previousTraceFile);
                        te.setExpectedException(expected);
                        te.run();
                    } catch (Exception e) {
                        notifier.fireTestFailure(new Failure(Description.createTestDescription(testClass, method.getName()), e));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
