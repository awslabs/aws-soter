// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.service;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.ExternalResource;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.tasks.ExternalTask;
import com.amazon.soter.checker.tasks.Task;

import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceNoLongerNeededUpdate;

import com.amazon.soter.thrift.SoterService;
import org.apache.thrift.TException;

/** Thrift server for the Soter service. */
public class SoterThriftHandler implements SoterService.Iface {
    public void createTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Task task = new ExternalTask(id);
            Checker.getChecker().createTask(task);
        }

        return;
    }

    public void cancelTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(id));
            Checker.getChecker().cancelExternalTask(task);
        }

        return;
    }

    public void exitTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().exitExternalTask();
        }

        return;
    }

    public void abnormalExitTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().abnormalExitExternalTask();
        }

        return;
    }

    public void yieldTask(String id, String updateType, String resourceId) throws TException {
        if (Checker.isRuntimeControlled()) {
            Update update = null;

            switch (updateType) {
                case "NoopUpdate":
                    update = new NoopUpdate();
                    break;

                case "ResourceNeededUpdate":
                    update = new ResourceNeededUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceNoLongerNeededUpdate":
                    update = new ResourceNoLongerNeededUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceAcquiredUpdate":
                    update = new ResourceAcquiredUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceReleasedUpdate":
                    update = new ResourceReleasedUpdate(new ExternalResource(resourceId));
                    break;

                default:
                    throw new RuntimeException("Invalid update type!");
            }

            Checker.getChecker().yieldExternalTask(update);
        }

        return;
    }

    public void blockTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(id));
            Checker.getChecker().blockExternalTask(task);
        }

        return;
    }

    public void startTask(String id) throws TException {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(id));
            Checker.getChecker().startExternalTask(task);
        }

        return;
    }

    public void updateState(String id, String updateType, String resourceId) throws TException {
        if (Checker.isRuntimeControlled()) {
            Update update = null;

            switch (updateType) {
                case "NoopUpdate":
                    update = new NoopUpdate();
                    break;

                case "ResourceNeededUpdate":
                    update = new ResourceNeededUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceNoLongerNeededUpdate":
                    update = new ResourceNoLongerNeededUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceAcquiredUpdate":
                    update = new ResourceAcquiredUpdate(new ExternalResource(resourceId));
                    break;

                case "ResourceReleasedUpdate":
                    update = new ResourceReleasedUpdate(new ExternalResource(resourceId));
                    break;

                default:
                    throw new RuntimeException("Invalid update type!");
            }

            Checker.getChecker().updateState(update);
        }

        return;
    }

    public boolean nondeterministicBooleanChoice() throws TException {
        if (Checker.isRuntimeControlled()) {
            boolean result = Checker.getChecker().nextBoolean();
            return result;
        }

        return true;
    }

    public int nondeterministicIntegerChoice() throws TException {
        if (Checker.isRuntimeControlled()) {
            int result = Checker.getChecker().nextInt();
            return result;
        }

        return 0;
    }

    public int nondeterministicIntegerWithBoundChoice(int bound) throws TException {
        if (Checker.isRuntimeControlled()) {
            int result = Checker.getChecker().nextInt(bound);
            return result;
        }

        return 0;
    }
}
