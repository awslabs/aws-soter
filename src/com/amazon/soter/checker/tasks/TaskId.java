// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.tasks;

/** Represents a unique identifier for a task. */
public class TaskId {
    public static void reset() {
        globalID = 0;
    }

    private static long globalID = 0;

    private static long nextID() {
        return globalID++;
    }

    /** The identity of a task. */
    public final long id;

    public TaskId() {
        id = nextID();
    }

    public TaskId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TaskId) {
            return this.id == ((TaskId) o).id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ID" + id;
    }

    @Override
    public int hashCode() {
        return (Long.valueOf(id)).hashCode();
    }
}
