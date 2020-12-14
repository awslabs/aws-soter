// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker;

import com.amazon.soter.checker.tasks.Task;

import soter.Soter.IsAvailableForRequest;
import soter.Soter.IsAvailableForResponse;
import soter.Soter.HasAssociatedTaskRequest;
import soter.Soter.HasAssociatedTaskResponse;
import soter.Soter.GetAssociatedTaskRequest;
import soter.Soter.GetAssociatedTaskResponse;
import soter.SoterClientServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/** Models an external resource from another VM and supports communication with that VM to determine if available. */
public class ExternalResource implements Resource {
    private static final String TARGET = "localhost:60051";

    private static final ManagedChannel CHANNEL
            = ManagedChannelBuilder.forTarget(TARGET).usePlaintext().build();

    private static final SoterClientServiceGrpc.SoterClientServiceBlockingStub BLOCKING_STUB
            = SoterClientServiceGrpc.newBlockingStub(CHANNEL);

    private String resourceId;

    public ExternalResource(String resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isAvailableFor(Task t) {
        IsAvailableForRequest request = IsAvailableForRequest.newBuilder()
            .setTaskId(Long.toString(t.getRawId()))
            .setResourceId(resourceId)
            .build();
        IsAvailableForResponse response;

        try {
            response = BLOCKING_STUB.isAvailableFor(request);
            boolean result = response.getResult();
            System.out.println("Got result: " + result);
            return result;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("The remote call failed with exception: " + e);
        }
    }

    public boolean hasAssociatedTask() {
        HasAssociatedTaskRequest request = HasAssociatedTaskRequest.newBuilder()
                .setResourceId(resourceId)
                .build();
        HasAssociatedTaskResponse response;

        try {
            response = BLOCKING_STUB.hasAssociatedTask(request);
            boolean result = response.getResult();
            System.out.println("Got result: " + result);
            return result;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("The remote call failed with exception: " + e);
        }
    }

    public Task getAssociatedTask() {
        GetAssociatedTaskRequest request = GetAssociatedTaskRequest.newBuilder()
                .setResourceId(resourceId)
                .build();
        GetAssociatedTaskResponse response;

        try {
            response = BLOCKING_STUB.getAssociatedTask(request);
            String taskId = response.getTaskId();
            int parsedTaskId = Integer.parseInt(taskId);
            if (parsedTaskId != -1) {
                Task task = Checker.getChecker().lookupByID(Integer.parseInt(taskId));
                System.out.println("Got task id: " + task);
                return task;
            } else {
                return null;
            }
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("The remote call failed with exception: " + e);
        }
    }
}
