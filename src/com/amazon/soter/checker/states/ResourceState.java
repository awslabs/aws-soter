// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.states;

import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.State;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.exceptions.UpdateMismatchException;

import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceNoLongerNeededUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Tracks who is holding what resources and what resources tasks need to proceed with execution. */
public class ResourceState implements State {
    private HashMap<Task, Resource> asks = new HashMap<Task, Resource>();
    private HashMap<Resource, Task> held = new HashMap<Resource, Task>();

    @Override
    public void update(Update update) throws UpdateMismatchException {
        if (update instanceof NoopUpdate) {
            // Nothing.
        } else {
            throw new UpdateMismatchException();
        }
    }

    @Override
    public void update(Task task, Update update) throws UpdateMismatchException {
        if (update instanceof ResourceNeededUpdate) {
            ResourceNeededUpdate u = (ResourceNeededUpdate) update;
            if (asks.containsKey(task)) {
                // This is the case where the same thread is asking for two things.
                // It *shouldn't* happen since threads will execute code in sequence, but
                // it might, so throw exception for now and eventually we need to
                // make the resource asks a list.
                throw new UpdateMismatchException();
            } else {
                asks.put(task, u.getResource());
            }
        } else if (update instanceof ResourceNoLongerNeededUpdate) {
            ResourceNoLongerNeededUpdate u = (ResourceNoLongerNeededUpdate) update;
            if (!asks.containsKey(task)) {
                throw new UpdateMismatchException();
            } else {
                asks.remove(task);
            }
        } else if (update instanceof ResourceAcquiredUpdate) {
            ResourceAcquiredUpdate u = (ResourceAcquiredUpdate) update;

            if (held.containsKey(task)) {
                throw new UpdateMismatchException();
            } else if (!asks.containsKey(task)) {
                throw new UpdateMismatchException();
            } else {
                asks.remove(task);
                held.put(u.getResource(), task);
            }
        } else if (update instanceof ResourceReleasedUpdate) {
            ResourceReleasedUpdate u = (ResourceReleasedUpdate) update;

            if (!held.containsKey(u.getResource())) {
                throw new UpdateMismatchException();
            } else {
                held.remove(u.getResource());
            }
        } else if (update instanceof NoopUpdate) {
            // Nothing.
        } else {
            throw new UpdateMismatchException();
        }
    }

    @Override
    public boolean isEnabled(Task t) {
        if (asks.containsKey(t)) {
            Resource r = asks.get(t);
            return r.isAvailableFor(t);
        } else {
            // If the task doesn't need resources, ready to go.
            return true;
        }
    }

    @Override
    public void addThread(Task t, Update initOp) {
        // no action required
    }

    @Override
    public void removeThread(Task t) {
        // no action required
    }

    @Override
    public boolean isDeadlocked() {
        boolean isDeadlocked = false;

        for (Map.Entry<Task, Resource> entry : asks.entrySet()) {
            ArrayList<Object> visited = new ArrayList<Object>();
            isDeadlocked = isCycle(entry.getValue(), visited);

            if (isDeadlocked) {
                break;
            }
        }

        if (isDeadlocked) {
            System.out.println("---------------- isDeadlocked() START -------------");
            System.out.println("");

            for (Map.Entry<Task, Resource> entry : asks.entrySet()) {
                Resource r = entry.getValue();

                System.out.println("Task " + entry.getKey() + " is waiting on resource: " + r);

                // Resource is already held by another task.
                if (held.containsKey(r)) {
                    System.out.println(" WARNING: Resource is held by: " + held.get(r));
                }

                // Resource itself is another task that may also be holding resources.
                if (r.hasAssociatedTask()) {
                    System.out.println(" WARNING: Resource is associated with an unfinished task: "
                            + r.getAssociatedTask());
                }

                System.out.println("");
            }

            System.out.println("Cycle detected; isDeadlocked: " + isDeadlocked);
            System.out.println("");
            System.out.println("---------------- isDeadlocked() END ---------------");
        }

        return isDeadlocked;
    }

    private boolean isCycle(Object o, ArrayList<Object> visited) {
        if (!visited.contains(o)) {
            visited.add(o);

            if (o instanceof Task) {
                Task t = (Task) o;

                if (asks.containsKey(o)) {
                    return isCycle(asks.get(o), visited);
                }

                return false;
            }

            if (o instanceof Resource) {
                Resource r = (Resource) o;

                boolean transitivelyHeld = false;
                boolean waitingOnBlockedTask = false;

                if (held.containsKey(o)) {
                    transitivelyHeld = isCycle(held.get(o), visited);
                }

                if (r.hasAssociatedTask()) {
                    waitingOnBlockedTask = isCycle(r.getAssociatedTask(), visited);
                }

                return transitivelyHeld || waitingOnBlockedTask;
            }

            return false;
        } else {
            return true;
        }
    }
}
