// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.exceptions.InvalidStrategyException;
import com.amazon.soter.checker.strategies.Generator;
import com.amazon.soter.checker.strategies.RandomGenerator;
import com.amazon.soter.checker.strategies.RoundRobinGenerator;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Interface into the strategies that determines how to handle and capture nondeterminism.
 */
public class Strategy {
    private final StrategyType type;
    private java.util.Random random;

    public Strategy(StrategyType type) {
        this.type = type;
    }

    public int getNondetInt() {
        if (type == StrategyType.RandomStrategy) {
            if (random == null) {
                random = new java.util.Random();
            }
            return random.nextInt();
        }
        throw new RuntimeException("Should not get nondeterministic Integer (without bound) with non-Random strategy");
    }

    public Generator<Task> getTaskInstance(Collection<Task> choices) {
        switch (type) {
            case RandomStrategy:
                return new RandomGenerator<>(choices);
            case RoundRobinStrategy:
                return new RoundRobinGenerator<>(choices);
            default:
                throw new InvalidStrategyException();
        }
    }

     public Generator<Integer> getIntInstance(int bound) {
         List<Integer> choices = new ArrayList<>(bound);
         for (int i = 0; i < bound; i++) {
             choices.add(i);
         }
         switch (type) {
             case RandomStrategy:
                 return new RandomGenerator<>(choices);
             case RoundRobinStrategy:
                 return new RoundRobinGenerator<>(choices);
             default:
                 throw new InvalidStrategyException();
         }
     }

     public Generator<Boolean> getBoolInstance() {
        List<Boolean> choices = new ArrayList<>(2);
        choices.add(true);
        choices.add(false);
        switch (type) {
            case RandomStrategy:
                return new RandomGenerator<>(choices);
            case RoundRobinStrategy:
                return new RoundRobinGenerator<>(choices);
            default:
                throw new InvalidStrategyException();
        }
     }
}
