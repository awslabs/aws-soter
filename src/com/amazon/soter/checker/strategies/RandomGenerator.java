// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.strategies;

import java.util.Collection;

/** Random generator. */
public class RandomGenerator<T> extends Generator<T> {
    private java.util.Random random = new java.util.Random();

    public RandomGenerator(Collection<T> choices) {
        super(choices);
    }

    @Override
    int nextIdx() {
        return random.nextInt(getChoices().size());
    }
}
