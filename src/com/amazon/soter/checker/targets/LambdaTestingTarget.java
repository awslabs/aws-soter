// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

import java.util.function.Consumer;

/** Models a testing target that is run from a Lambda invocation with an arbitrary body. */
public class LambdaTestingTarget implements TestingTarget {
    private Consumer consumer;
    private TargetExecutionType executionType = TargetExecutionType.Java;

    public LambdaTestingTarget(Consumer consumer) {
        this.consumer = consumer;
    }

    public TargetExecutionType getExecutionType() {
        return TargetExecutionType.Java;
    }

    public void run(Object o) throws Throwable {
        consumer.accept(o);
    }
}
