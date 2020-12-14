package com.amazon.soter.service;

import com.amazon.soter.checker.updates.NoopUpdate;
import com.amazon.soter.checker.updates.ResourceReleasedUpdate;
import com.amazon.soter.checker.updates.ResourceAcquiredUpdate;
import com.amazon.soter.checker.updates.ResourceNeededUpdate;
import com.amazon.soter.checker.updates.ResourceNoLongerNeededUpdate;

import io.grpc.stub.StreamObserver;

import soter.Soter;
import soter.SoterServiceGrpc;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.ExternalResource;
import com.amazon.soter.checker.Update;
import com.amazon.soter.checker.tasks.ExternalTask;
import com.amazon.soter.checker.tasks.Task;

public class SoterServiceGrpcImpl extends SoterServiceGrpc.SoterServiceImplBase {
    @Override
    public void createTask(Soter.CreateTaskRequest request,
            StreamObserver<Soter.CreateTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Task task = new ExternalTask(request.getId());
            Checker.getChecker().createTask(task);
        }

        Soter.CreateTaskResponse response = Soter.CreateTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void startTask(Soter.StartTaskRequest request,
            StreamObserver<Soter.StartTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(request.getId()));
            Checker.getChecker().startExternalTask(task);
        }

        Soter.StartTaskResponse response = Soter.StartTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void yieldTask(Soter.YieldTaskRequest request, StreamObserver<Soter.YieldTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Update update = null;
            String resourceId = request.getResourceId();

            switch(request.getUpdateType()) {
                case NO_OP:
                    update = new NoopUpdate();
                    break;

                case RESOURCE_NEEDED:
                    update = new ResourceNeededUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_NO_LONGER_NEEDED:
                    update = new ResourceNoLongerNeededUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_ACQUIRED:
                    update = new ResourceAcquiredUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_RELEASED:
                    update = new ResourceReleasedUpdate(new ExternalResource(resourceId));
                    break;

                default:
                    throw new RuntimeException("Invalid update type!");
            }

            Checker.getChecker().yieldExternalTask(update);
        }

        Soter.YieldTaskResponse response = Soter.YieldTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateState(Soter.UpdateStateRequest request, StreamObserver<Soter.UpdateStateResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Update update = null;
            String resourceId = request.getResourceId();

            switch(request.getUpdateType()) {
                case NO_OP:
                    update = new NoopUpdate();
                    break;

                case RESOURCE_NEEDED:
                    update = new ResourceNeededUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_NO_LONGER_NEEDED:
                    update = new ResourceNoLongerNeededUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_ACQUIRED:
                    update = new ResourceAcquiredUpdate(new ExternalResource(resourceId));
                    break;

                case RESOURCE_RELEASED:
                    update = new ResourceReleasedUpdate(new ExternalResource(resourceId));
                    break;

                default:
                    throw new RuntimeException("Invalid update type!");
            }

            Checker.getChecker().updateState(update);
        }

        Soter.UpdateStateResponse response = Soter.UpdateStateResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void exitTask(Soter.ExitTaskRequest request, StreamObserver<Soter.ExitTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().exitExternalTask();
        }

        Soter.ExitTaskResponse response = Soter.ExitTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void cancelTask(Soter.CancelTaskRequest request, StreamObserver<Soter.CancelTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(request.getId()));
            Checker.getChecker().cancelExternalTask(task);
        }

        Soter.CancelTaskResponse response = Soter.CancelTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void abnormalExitTask(Soter.AbnormalExitTaskRequest request, StreamObserver<Soter.AbnormalExitTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Checker.getChecker().abnormalExitExternalTask();
        }

        Soter.AbnormalExitTaskResponse response = Soter.AbnormalExitTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void blockTask(Soter.BlockTaskRequest request, StreamObserver<Soter.BlockTaskResponse> responseObserver) {
        if (Checker.isRuntimeControlled()) {
            Task task = Checker.getChecker().lookupByID(Integer.parseInt(request.getId()));
            Checker.getChecker().blockExternalTask(task);
        }

        Soter.BlockTaskResponse response = Soter.BlockTaskResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void nondeterministicBooleanChoice(Soter.NondeterministicBooleanChoiceRequest request, StreamObserver<Soter.NondeterministicBooleanChoiceResponse> responseObserver) {
        Soter.NondeterministicBooleanChoiceResponse response;

        if (Checker.isRuntimeControlled()) {
            boolean result = Checker.getChecker().nextBoolean();
            response = Soter.NondeterministicBooleanChoiceResponse.newBuilder().setResult(result).build();
        } else {
            response = Soter.NondeterministicBooleanChoiceResponse.newBuilder().setResult(true).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void nondeterministicIntegerChoice(Soter.NondeterministicIntegerChoiceRequest request, StreamObserver<Soter.NondeterministicIntegerChoiceResponse> responseObserver) {
        Soter.NondeterministicIntegerChoiceResponse response;

        if (Checker.isRuntimeControlled()) {
            int result = Checker.getChecker().nextInt();
            response = Soter.NondeterministicIntegerChoiceResponse.newBuilder().setResult(result).build();
        } else {
            response = Soter.NondeterministicIntegerChoiceResponse.newBuilder().setResult(0).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void nondeterministicIntegerWithBoundChoice(Soter.NondeterministicIntegerWithBoundChoiceRequest request, StreamObserver<Soter.NondeterministicIntegerWithBoundChoiceResponse> responseObserver) {
        Soter.NondeterministicIntegerWithBoundChoiceResponse response;

        if (Checker.isRuntimeControlled()) {
            int bound = request.getBound();
            int result = Checker.getChecker().nextInt(bound);
            response = Soter.NondeterministicIntegerWithBoundChoiceResponse.newBuilder().setResult(result).build();
        } else {
            response = Soter.NondeterministicIntegerWithBoundChoiceResponse.newBuilder().setResult(0).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}