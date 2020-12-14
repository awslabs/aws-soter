// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.strategies;

import java.util.Collection;

/**
 * Generate that uses the round robin strategy.
 *
 * @param <T> I'm not sure this type parameter is actually used.
 */
public class RoundRobinGenerator<T> extends Generator<T> {

        public RoundRobinGenerator(Collection<T> choices) {
            super(choices);
        }

        @Override
        int nextIdx() {
            return 0;
        }

}
