// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.strategies;

import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Abstract class for generators for the different search strategies. */
public abstract class Generator<T> {
    private List<T> choices;

    public Generator(Collection<T> choices) {
        this.choices = new ArrayList<>(choices);
    }

    abstract int nextIdx();

    public T getNext() {
        if (choices.size() == 0) return null;
        int idx = nextIdx();
        return (choices.remove(idx));
    }

    public boolean hasNext() {
        return choices.size() != 0;
    }

    public static Generator<Task> getTaskInstance(Collection<Task> choices) {
        throw new NotImplementedException();
    }

    public static Generator<Integer> getIntInstance(int bound) {
        throw new NotImplementedException();
    }

    public static Generator<Boolean> getBoolInstance() {
        throw new NotImplementedException();
    }

    public List<T> getChoices() {
        return choices;
    }
}
