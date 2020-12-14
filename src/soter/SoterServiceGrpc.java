package soter;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.1)",
    comments = "Source: soter.proto")
public final class SoterServiceGrpc {

  private SoterServiceGrpc() {}

  public static final String SERVICE_NAME = "soter.SoterService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.CreateTaskRequest,
      soter.Soter.CreateTaskResponse> METHOD_CREATE_TASK = getCreateTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.CreateTaskRequest,
      soter.Soter.CreateTaskResponse> getCreateTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.CreateTaskRequest,
      soter.Soter.CreateTaskResponse> getCreateTaskMethod() {
    return getCreateTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.CreateTaskRequest,
      soter.Soter.CreateTaskResponse> getCreateTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.CreateTaskRequest, soter.Soter.CreateTaskResponse> getCreateTaskMethod;
    if ((getCreateTaskMethod = SoterServiceGrpc.getCreateTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getCreateTaskMethod = SoterServiceGrpc.getCreateTaskMethod) == null) {
          SoterServiceGrpc.getCreateTaskMethod = getCreateTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.CreateTaskRequest, soter.Soter.CreateTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "createTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.CreateTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.CreateTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("createTask"))
                  .build();
          }
        }
     }
     return getCreateTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getExitTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.ExitTaskRequest,
      soter.Soter.ExitTaskResponse> METHOD_EXIT_TASK = getExitTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.ExitTaskRequest,
      soter.Soter.ExitTaskResponse> getExitTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.ExitTaskRequest,
      soter.Soter.ExitTaskResponse> getExitTaskMethod() {
    return getExitTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.ExitTaskRequest,
      soter.Soter.ExitTaskResponse> getExitTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.ExitTaskRequest, soter.Soter.ExitTaskResponse> getExitTaskMethod;
    if ((getExitTaskMethod = SoterServiceGrpc.getExitTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getExitTaskMethod = SoterServiceGrpc.getExitTaskMethod) == null) {
          SoterServiceGrpc.getExitTaskMethod = getExitTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.ExitTaskRequest, soter.Soter.ExitTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "exitTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.ExitTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.ExitTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("exitTask"))
                  .build();
          }
        }
     }
     return getExitTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCancelTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.CancelTaskRequest,
      soter.Soter.CancelTaskResponse> METHOD_CANCEL_TASK = getCancelTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.CancelTaskRequest,
      soter.Soter.CancelTaskResponse> getCancelTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.CancelTaskRequest,
      soter.Soter.CancelTaskResponse> getCancelTaskMethod() {
    return getCancelTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.CancelTaskRequest,
      soter.Soter.CancelTaskResponse> getCancelTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.CancelTaskRequest, soter.Soter.CancelTaskResponse> getCancelTaskMethod;
    if ((getCancelTaskMethod = SoterServiceGrpc.getCancelTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getCancelTaskMethod = SoterServiceGrpc.getCancelTaskMethod) == null) {
          SoterServiceGrpc.getCancelTaskMethod = getCancelTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.CancelTaskRequest, soter.Soter.CancelTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "cancelTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.CancelTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.CancelTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("cancelTask"))
                  .build();
          }
        }
     }
     return getCancelTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getAbnormalExitTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.AbnormalExitTaskRequest,
      soter.Soter.AbnormalExitTaskResponse> METHOD_ABNORMAL_EXIT_TASK = getAbnormalExitTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.AbnormalExitTaskRequest,
      soter.Soter.AbnormalExitTaskResponse> getAbnormalExitTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.AbnormalExitTaskRequest,
      soter.Soter.AbnormalExitTaskResponse> getAbnormalExitTaskMethod() {
    return getAbnormalExitTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.AbnormalExitTaskRequest,
      soter.Soter.AbnormalExitTaskResponse> getAbnormalExitTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.AbnormalExitTaskRequest, soter.Soter.AbnormalExitTaskResponse> getAbnormalExitTaskMethod;
    if ((getAbnormalExitTaskMethod = SoterServiceGrpc.getAbnormalExitTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getAbnormalExitTaskMethod = SoterServiceGrpc.getAbnormalExitTaskMethod) == null) {
          SoterServiceGrpc.getAbnormalExitTaskMethod = getAbnormalExitTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.AbnormalExitTaskRequest, soter.Soter.AbnormalExitTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "abnormalExitTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.AbnormalExitTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.AbnormalExitTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("abnormalExitTask"))
                  .build();
          }
        }
     }
     return getAbnormalExitTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getYieldTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.YieldTaskRequest,
      soter.Soter.YieldTaskResponse> METHOD_YIELD_TASK = getYieldTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.YieldTaskRequest,
      soter.Soter.YieldTaskResponse> getYieldTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.YieldTaskRequest,
      soter.Soter.YieldTaskResponse> getYieldTaskMethod() {
    return getYieldTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.YieldTaskRequest,
      soter.Soter.YieldTaskResponse> getYieldTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.YieldTaskRequest, soter.Soter.YieldTaskResponse> getYieldTaskMethod;
    if ((getYieldTaskMethod = SoterServiceGrpc.getYieldTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getYieldTaskMethod = SoterServiceGrpc.getYieldTaskMethod) == null) {
          SoterServiceGrpc.getYieldTaskMethod = getYieldTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.YieldTaskRequest, soter.Soter.YieldTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "yieldTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.YieldTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.YieldTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("yieldTask"))
                  .build();
          }
        }
     }
     return getYieldTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getBlockTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.BlockTaskRequest,
      soter.Soter.BlockTaskResponse> METHOD_BLOCK_TASK = getBlockTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.BlockTaskRequest,
      soter.Soter.BlockTaskResponse> getBlockTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.BlockTaskRequest,
      soter.Soter.BlockTaskResponse> getBlockTaskMethod() {
    return getBlockTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.BlockTaskRequest,
      soter.Soter.BlockTaskResponse> getBlockTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.BlockTaskRequest, soter.Soter.BlockTaskResponse> getBlockTaskMethod;
    if ((getBlockTaskMethod = SoterServiceGrpc.getBlockTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getBlockTaskMethod = SoterServiceGrpc.getBlockTaskMethod) == null) {
          SoterServiceGrpc.getBlockTaskMethod = getBlockTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.BlockTaskRequest, soter.Soter.BlockTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "blockTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.BlockTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.BlockTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("blockTask"))
                  .build();
          }
        }
     }
     return getBlockTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getStartTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.StartTaskRequest,
      soter.Soter.StartTaskResponse> METHOD_START_TASK = getStartTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.StartTaskRequest,
      soter.Soter.StartTaskResponse> getStartTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.StartTaskRequest,
      soter.Soter.StartTaskResponse> getStartTaskMethod() {
    return getStartTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.StartTaskRequest,
      soter.Soter.StartTaskResponse> getStartTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.StartTaskRequest, soter.Soter.StartTaskResponse> getStartTaskMethod;
    if ((getStartTaskMethod = SoterServiceGrpc.getStartTaskMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getStartTaskMethod = SoterServiceGrpc.getStartTaskMethod) == null) {
          SoterServiceGrpc.getStartTaskMethod = getStartTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.StartTaskRequest, soter.Soter.StartTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "startTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.StartTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.StartTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("startTask"))
                  .build();
          }
        }
     }
     return getStartTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateStateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.UpdateStateRequest,
      soter.Soter.UpdateStateResponse> METHOD_UPDATE_STATE = getUpdateStateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.UpdateStateRequest,
      soter.Soter.UpdateStateResponse> getUpdateStateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.UpdateStateRequest,
      soter.Soter.UpdateStateResponse> getUpdateStateMethod() {
    return getUpdateStateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.UpdateStateRequest,
      soter.Soter.UpdateStateResponse> getUpdateStateMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.UpdateStateRequest, soter.Soter.UpdateStateResponse> getUpdateStateMethod;
    if ((getUpdateStateMethod = SoterServiceGrpc.getUpdateStateMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getUpdateStateMethod = SoterServiceGrpc.getUpdateStateMethod) == null) {
          SoterServiceGrpc.getUpdateStateMethod = getUpdateStateMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.UpdateStateRequest, soter.Soter.UpdateStateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "updateState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.UpdateStateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.UpdateStateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("updateState"))
                  .build();
          }
        }
     }
     return getUpdateStateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getNondeterministicBooleanChoiceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.NondeterministicBooleanChoiceRequest,
      soter.Soter.NondeterministicBooleanChoiceResponse> METHOD_NONDETERMINISTIC_BOOLEAN_CHOICE = getNondeterministicBooleanChoiceMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.NondeterministicBooleanChoiceRequest,
      soter.Soter.NondeterministicBooleanChoiceResponse> getNondeterministicBooleanChoiceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.NondeterministicBooleanChoiceRequest,
      soter.Soter.NondeterministicBooleanChoiceResponse> getNondeterministicBooleanChoiceMethod() {
    return getNondeterministicBooleanChoiceMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.NondeterministicBooleanChoiceRequest,
      soter.Soter.NondeterministicBooleanChoiceResponse> getNondeterministicBooleanChoiceMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.NondeterministicBooleanChoiceRequest, soter.Soter.NondeterministicBooleanChoiceResponse> getNondeterministicBooleanChoiceMethod;
    if ((getNondeterministicBooleanChoiceMethod = SoterServiceGrpc.getNondeterministicBooleanChoiceMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getNondeterministicBooleanChoiceMethod = SoterServiceGrpc.getNondeterministicBooleanChoiceMethod) == null) {
          SoterServiceGrpc.getNondeterministicBooleanChoiceMethod = getNondeterministicBooleanChoiceMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.NondeterministicBooleanChoiceRequest, soter.Soter.NondeterministicBooleanChoiceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "nondeterministicBooleanChoice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicBooleanChoiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicBooleanChoiceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("nondeterministicBooleanChoice"))
                  .build();
          }
        }
     }
     return getNondeterministicBooleanChoiceMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getNondeterministicIntegerChoiceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerChoiceRequest,
      soter.Soter.NondeterministicIntegerChoiceResponse> METHOD_NONDETERMINISTIC_INTEGER_CHOICE = getNondeterministicIntegerChoiceMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerChoiceRequest,
      soter.Soter.NondeterministicIntegerChoiceResponse> getNondeterministicIntegerChoiceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerChoiceRequest,
      soter.Soter.NondeterministicIntegerChoiceResponse> getNondeterministicIntegerChoiceMethod() {
    return getNondeterministicIntegerChoiceMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerChoiceRequest,
      soter.Soter.NondeterministicIntegerChoiceResponse> getNondeterministicIntegerChoiceMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerChoiceRequest, soter.Soter.NondeterministicIntegerChoiceResponse> getNondeterministicIntegerChoiceMethod;
    if ((getNondeterministicIntegerChoiceMethod = SoterServiceGrpc.getNondeterministicIntegerChoiceMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getNondeterministicIntegerChoiceMethod = SoterServiceGrpc.getNondeterministicIntegerChoiceMethod) == null) {
          SoterServiceGrpc.getNondeterministicIntegerChoiceMethod = getNondeterministicIntegerChoiceMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.NondeterministicIntegerChoiceRequest, soter.Soter.NondeterministicIntegerChoiceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "nondeterministicIntegerChoice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicIntegerChoiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicIntegerChoiceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("nondeterministicIntegerChoice"))
                  .build();
          }
        }
     }
     return getNondeterministicIntegerChoiceMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getNondeterministicIntegerWithBoundChoiceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest,
      soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> METHOD_NONDETERMINISTIC_INTEGER_WITH_BOUND_CHOICE = getNondeterministicIntegerWithBoundChoiceMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest,
      soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> getNondeterministicIntegerWithBoundChoiceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest,
      soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> getNondeterministicIntegerWithBoundChoiceMethod() {
    return getNondeterministicIntegerWithBoundChoiceMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest,
      soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> getNondeterministicIntegerWithBoundChoiceMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest, soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> getNondeterministicIntegerWithBoundChoiceMethod;
    if ((getNondeterministicIntegerWithBoundChoiceMethod = SoterServiceGrpc.getNondeterministicIntegerWithBoundChoiceMethod) == null) {
      synchronized (SoterServiceGrpc.class) {
        if ((getNondeterministicIntegerWithBoundChoiceMethod = SoterServiceGrpc.getNondeterministicIntegerWithBoundChoiceMethod) == null) {
          SoterServiceGrpc.getNondeterministicIntegerWithBoundChoiceMethod = getNondeterministicIntegerWithBoundChoiceMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.NondeterministicIntegerWithBoundChoiceRequest, soter.Soter.NondeterministicIntegerWithBoundChoiceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterService", "nondeterministicIntegerWithBoundChoice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicIntegerWithBoundChoiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.NondeterministicIntegerWithBoundChoiceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterServiceMethodDescriptorSupplier("nondeterministicIntegerWithBoundChoice"))
                  .build();
          }
        }
     }
     return getNondeterministicIntegerWithBoundChoiceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SoterServiceStub newStub(io.grpc.Channel channel) {
    return new SoterServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SoterServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SoterServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SoterServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SoterServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SoterServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createTask(soter.Soter.CreateTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.CreateTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void exitTask(soter.Soter.ExitTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.ExitTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getExitTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void cancelTask(soter.Soter.CancelTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.CancelTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCancelTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void abnormalExitTask(soter.Soter.AbnormalExitTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.AbnormalExitTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAbnormalExitTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void yieldTask(soter.Soter.YieldTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.YieldTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getYieldTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void blockTask(soter.Soter.BlockTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.BlockTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getBlockTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void startTask(soter.Soter.StartTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.StartTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void updateState(soter.Soter.UpdateStateRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.UpdateStateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateStateMethodHelper(), responseObserver);
    }

    /**
     */
    public void nondeterministicBooleanChoice(soter.Soter.NondeterministicBooleanChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicBooleanChoiceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNondeterministicBooleanChoiceMethodHelper(), responseObserver);
    }

    /**
     */
    public void nondeterministicIntegerChoice(soter.Soter.NondeterministicIntegerChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerChoiceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNondeterministicIntegerChoiceMethodHelper(), responseObserver);
    }

    /**
     */
    public void nondeterministicIntegerWithBoundChoice(soter.Soter.NondeterministicIntegerWithBoundChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNondeterministicIntegerWithBoundChoiceMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.CreateTaskRequest,
                soter.Soter.CreateTaskResponse>(
                  this, METHODID_CREATE_TASK)))
          .addMethod(
            getExitTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.ExitTaskRequest,
                soter.Soter.ExitTaskResponse>(
                  this, METHODID_EXIT_TASK)))
          .addMethod(
            getCancelTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.CancelTaskRequest,
                soter.Soter.CancelTaskResponse>(
                  this, METHODID_CANCEL_TASK)))
          .addMethod(
            getAbnormalExitTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.AbnormalExitTaskRequest,
                soter.Soter.AbnormalExitTaskResponse>(
                  this, METHODID_ABNORMAL_EXIT_TASK)))
          .addMethod(
            getYieldTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.YieldTaskRequest,
                soter.Soter.YieldTaskResponse>(
                  this, METHODID_YIELD_TASK)))
          .addMethod(
            getBlockTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.BlockTaskRequest,
                soter.Soter.BlockTaskResponse>(
                  this, METHODID_BLOCK_TASK)))
          .addMethod(
            getStartTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.StartTaskRequest,
                soter.Soter.StartTaskResponse>(
                  this, METHODID_START_TASK)))
          .addMethod(
            getUpdateStateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.UpdateStateRequest,
                soter.Soter.UpdateStateResponse>(
                  this, METHODID_UPDATE_STATE)))
          .addMethod(
            getNondeterministicBooleanChoiceMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.NondeterministicBooleanChoiceRequest,
                soter.Soter.NondeterministicBooleanChoiceResponse>(
                  this, METHODID_NONDETERMINISTIC_BOOLEAN_CHOICE)))
          .addMethod(
            getNondeterministicIntegerChoiceMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.NondeterministicIntegerChoiceRequest,
                soter.Soter.NondeterministicIntegerChoiceResponse>(
                  this, METHODID_NONDETERMINISTIC_INTEGER_CHOICE)))
          .addMethod(
            getNondeterministicIntegerWithBoundChoiceMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.NondeterministicIntegerWithBoundChoiceRequest,
                soter.Soter.NondeterministicIntegerWithBoundChoiceResponse>(
                  this, METHODID_NONDETERMINISTIC_INTEGER_WITH_BOUND_CHOICE)))
          .build();
    }
  }

  /**
   */
  public static final class SoterServiceStub extends io.grpc.stub.AbstractStub<SoterServiceStub> {
    private SoterServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterServiceStub(channel, callOptions);
    }

    /**
     */
    public void createTask(soter.Soter.CreateTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.CreateTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void exitTask(soter.Soter.ExitTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.ExitTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getExitTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cancelTask(soter.Soter.CancelTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.CancelTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCancelTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void abnormalExitTask(soter.Soter.AbnormalExitTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.AbnormalExitTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAbnormalExitTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void yieldTask(soter.Soter.YieldTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.YieldTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getYieldTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void blockTask(soter.Soter.BlockTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.BlockTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBlockTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void startTask(soter.Soter.StartTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.StartTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateState(soter.Soter.UpdateStateRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.UpdateStateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateStateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nondeterministicBooleanChoice(soter.Soter.NondeterministicBooleanChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicBooleanChoiceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNondeterministicBooleanChoiceMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nondeterministicIntegerChoice(soter.Soter.NondeterministicIntegerChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerChoiceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNondeterministicIntegerChoiceMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nondeterministicIntegerWithBoundChoice(soter.Soter.NondeterministicIntegerWithBoundChoiceRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNondeterministicIntegerWithBoundChoiceMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SoterServiceBlockingStub extends io.grpc.stub.AbstractStub<SoterServiceBlockingStub> {
    private SoterServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public soter.Soter.CreateTaskResponse createTask(soter.Soter.CreateTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.ExitTaskResponse exitTask(soter.Soter.ExitTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getExitTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.CancelTaskResponse cancelTask(soter.Soter.CancelTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getCancelTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.AbnormalExitTaskResponse abnormalExitTask(soter.Soter.AbnormalExitTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getAbnormalExitTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.YieldTaskResponse yieldTask(soter.Soter.YieldTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getYieldTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.BlockTaskResponse blockTask(soter.Soter.BlockTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getBlockTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.StartTaskResponse startTask(soter.Soter.StartTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.UpdateStateResponse updateState(soter.Soter.UpdateStateRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateStateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.NondeterministicBooleanChoiceResponse nondeterministicBooleanChoice(soter.Soter.NondeterministicBooleanChoiceRequest request) {
      return blockingUnaryCall(
          getChannel(), getNondeterministicBooleanChoiceMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.NondeterministicIntegerChoiceResponse nondeterministicIntegerChoice(soter.Soter.NondeterministicIntegerChoiceRequest request) {
      return blockingUnaryCall(
          getChannel(), getNondeterministicIntegerChoiceMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.NondeterministicIntegerWithBoundChoiceResponse nondeterministicIntegerWithBoundChoice(soter.Soter.NondeterministicIntegerWithBoundChoiceRequest request) {
      return blockingUnaryCall(
          getChannel(), getNondeterministicIntegerWithBoundChoiceMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SoterServiceFutureStub extends io.grpc.stub.AbstractStub<SoterServiceFutureStub> {
    private SoterServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.CreateTaskResponse> createTask(
        soter.Soter.CreateTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.ExitTaskResponse> exitTask(
        soter.Soter.ExitTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getExitTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.CancelTaskResponse> cancelTask(
        soter.Soter.CancelTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCancelTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.AbnormalExitTaskResponse> abnormalExitTask(
        soter.Soter.AbnormalExitTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAbnormalExitTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.YieldTaskResponse> yieldTask(
        soter.Soter.YieldTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getYieldTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.BlockTaskResponse> blockTask(
        soter.Soter.BlockTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getBlockTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.StartTaskResponse> startTask(
        soter.Soter.StartTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.UpdateStateResponse> updateState(
        soter.Soter.UpdateStateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateStateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.NondeterministicBooleanChoiceResponse> nondeterministicBooleanChoice(
        soter.Soter.NondeterministicBooleanChoiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNondeterministicBooleanChoiceMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.NondeterministicIntegerChoiceResponse> nondeterministicIntegerChoice(
        soter.Soter.NondeterministicIntegerChoiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNondeterministicIntegerChoiceMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.NondeterministicIntegerWithBoundChoiceResponse> nondeterministicIntegerWithBoundChoice(
        soter.Soter.NondeterministicIntegerWithBoundChoiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNondeterministicIntegerWithBoundChoiceMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_TASK = 0;
  private static final int METHODID_EXIT_TASK = 1;
  private static final int METHODID_CANCEL_TASK = 2;
  private static final int METHODID_ABNORMAL_EXIT_TASK = 3;
  private static final int METHODID_YIELD_TASK = 4;
  private static final int METHODID_BLOCK_TASK = 5;
  private static final int METHODID_START_TASK = 6;
  private static final int METHODID_UPDATE_STATE = 7;
  private static final int METHODID_NONDETERMINISTIC_BOOLEAN_CHOICE = 8;
  private static final int METHODID_NONDETERMINISTIC_INTEGER_CHOICE = 9;
  private static final int METHODID_NONDETERMINISTIC_INTEGER_WITH_BOUND_CHOICE = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SoterServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SoterServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_TASK:
          serviceImpl.createTask((soter.Soter.CreateTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.CreateTaskResponse>) responseObserver);
          break;
        case METHODID_EXIT_TASK:
          serviceImpl.exitTask((soter.Soter.ExitTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.ExitTaskResponse>) responseObserver);
          break;
        case METHODID_CANCEL_TASK:
          serviceImpl.cancelTask((soter.Soter.CancelTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.CancelTaskResponse>) responseObserver);
          break;
        case METHODID_ABNORMAL_EXIT_TASK:
          serviceImpl.abnormalExitTask((soter.Soter.AbnormalExitTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.AbnormalExitTaskResponse>) responseObserver);
          break;
        case METHODID_YIELD_TASK:
          serviceImpl.yieldTask((soter.Soter.YieldTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.YieldTaskResponse>) responseObserver);
          break;
        case METHODID_BLOCK_TASK:
          serviceImpl.blockTask((soter.Soter.BlockTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.BlockTaskResponse>) responseObserver);
          break;
        case METHODID_START_TASK:
          serviceImpl.startTask((soter.Soter.StartTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.StartTaskResponse>) responseObserver);
          break;
        case METHODID_UPDATE_STATE:
          serviceImpl.updateState((soter.Soter.UpdateStateRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.UpdateStateResponse>) responseObserver);
          break;
        case METHODID_NONDETERMINISTIC_BOOLEAN_CHOICE:
          serviceImpl.nondeterministicBooleanChoice((soter.Soter.NondeterministicBooleanChoiceRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.NondeterministicBooleanChoiceResponse>) responseObserver);
          break;
        case METHODID_NONDETERMINISTIC_INTEGER_CHOICE:
          serviceImpl.nondeterministicIntegerChoice((soter.Soter.NondeterministicIntegerChoiceRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerChoiceResponse>) responseObserver);
          break;
        case METHODID_NONDETERMINISTIC_INTEGER_WITH_BOUND_CHOICE:
          serviceImpl.nondeterministicIntegerWithBoundChoice((soter.Soter.NondeterministicIntegerWithBoundChoiceRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.NondeterministicIntegerWithBoundChoiceResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SoterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SoterServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return soter.Soter.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SoterService");
    }
  }

  private static final class SoterServiceFileDescriptorSupplier
      extends SoterServiceBaseDescriptorSupplier {
    SoterServiceFileDescriptorSupplier() {}
  }

  private static final class SoterServiceMethodDescriptorSupplier
      extends SoterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SoterServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SoterServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SoterServiceFileDescriptorSupplier())
              .addMethod(getCreateTaskMethodHelper())
              .addMethod(getExitTaskMethodHelper())
              .addMethod(getCancelTaskMethodHelper())
              .addMethod(getAbnormalExitTaskMethodHelper())
              .addMethod(getYieldTaskMethodHelper())
              .addMethod(getBlockTaskMethodHelper())
              .addMethod(getStartTaskMethodHelper())
              .addMethod(getUpdateStateMethodHelper())
              .addMethod(getNondeterministicBooleanChoiceMethodHelper())
              .addMethod(getNondeterministicIntegerChoiceMethodHelper())
              .addMethod(getNondeterministicIntegerWithBoundChoiceMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
