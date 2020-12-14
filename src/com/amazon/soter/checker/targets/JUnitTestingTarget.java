// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Models a testing target that is run from JUnit, such as a JUnit test case. */
public class JUnitTestingTarget implements TestingTarget {
    private Object testObject;
    private Method testMethod;

    public JUnitTestingTarget(Object testObject, Method testMethod) {
        this.testObject = testObject;
        this.testMethod = testMethod;
    }

    public TargetExecutionType getExecutionType() {
        return TargetExecutionType.Java;
    }

    public void run(Object o) throws Throwable {
        try {
            testMethod.invoke(testObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
