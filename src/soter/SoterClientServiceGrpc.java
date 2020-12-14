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
public final class SoterClientServiceGrpc {

  private SoterClientServiceGrpc() {}

  public static final String SERVICE_NAME = "soter.SoterClientService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getIsAvailableForMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.IsAvailableForRequest,
      soter.Soter.IsAvailableForResponse> METHOD_IS_AVAILABLE_FOR = getIsAvailableForMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.IsAvailableForRequest,
      soter.Soter.IsAvailableForResponse> getIsAvailableForMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.IsAvailableForRequest,
      soter.Soter.IsAvailableForResponse> getIsAvailableForMethod() {
    return getIsAvailableForMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.IsAvailableForRequest,
      soter.Soter.IsAvailableForResponse> getIsAvailableForMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.IsAvailableForRequest, soter.Soter.IsAvailableForResponse> getIsAvailableForMethod;
    if ((getIsAvailableForMethod = SoterClientServiceGrpc.getIsAvailableForMethod) == null) {
      synchronized (SoterClientServiceGrpc.class) {
        if ((getIsAvailableForMethod = SoterClientServiceGrpc.getIsAvailableForMethod) == null) {
          SoterClientServiceGrpc.getIsAvailableForMethod = getIsAvailableForMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.IsAvailableForRequest, soter.Soter.IsAvailableForResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterClientService", "isAvailableFor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.IsAvailableForRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.IsAvailableForResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterClientServiceMethodDescriptorSupplier("isAvailableFor"))
                  .build();
          }
        }
     }
     return getIsAvailableForMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getHasAssociatedTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.HasAssociatedTaskRequest,
      soter.Soter.HasAssociatedTaskResponse> METHOD_HAS_ASSOCIATED_TASK = getHasAssociatedTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.HasAssociatedTaskRequest,
      soter.Soter.HasAssociatedTaskResponse> getHasAssociatedTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.HasAssociatedTaskRequest,
      soter.Soter.HasAssociatedTaskResponse> getHasAssociatedTaskMethod() {
    return getHasAssociatedTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.HasAssociatedTaskRequest,
      soter.Soter.HasAssociatedTaskResponse> getHasAssociatedTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.HasAssociatedTaskRequest, soter.Soter.HasAssociatedTaskResponse> getHasAssociatedTaskMethod;
    if ((getHasAssociatedTaskMethod = SoterClientServiceGrpc.getHasAssociatedTaskMethod) == null) {
      synchronized (SoterClientServiceGrpc.class) {
        if ((getHasAssociatedTaskMethod = SoterClientServiceGrpc.getHasAssociatedTaskMethod) == null) {
          SoterClientServiceGrpc.getHasAssociatedTaskMethod = getHasAssociatedTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.HasAssociatedTaskRequest, soter.Soter.HasAssociatedTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterClientService", "hasAssociatedTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.HasAssociatedTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.HasAssociatedTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterClientServiceMethodDescriptorSupplier("hasAssociatedTask"))
                  .build();
          }
        }
     }
     return getHasAssociatedTaskMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAssociatedTaskMethod()} instead. 
  public static final io.grpc.MethodDescriptor<soter.Soter.GetAssociatedTaskRequest,
      soter.Soter.GetAssociatedTaskResponse> METHOD_GET_ASSOCIATED_TASK = getGetAssociatedTaskMethodHelper();

  private static volatile io.grpc.MethodDescriptor<soter.Soter.GetAssociatedTaskRequest,
      soter.Soter.GetAssociatedTaskResponse> getGetAssociatedTaskMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<soter.Soter.GetAssociatedTaskRequest,
      soter.Soter.GetAssociatedTaskResponse> getGetAssociatedTaskMethod() {
    return getGetAssociatedTaskMethodHelper();
  }

  private static io.grpc.MethodDescriptor<soter.Soter.GetAssociatedTaskRequest,
      soter.Soter.GetAssociatedTaskResponse> getGetAssociatedTaskMethodHelper() {
    io.grpc.MethodDescriptor<soter.Soter.GetAssociatedTaskRequest, soter.Soter.GetAssociatedTaskResponse> getGetAssociatedTaskMethod;
    if ((getGetAssociatedTaskMethod = SoterClientServiceGrpc.getGetAssociatedTaskMethod) == null) {
      synchronized (SoterClientServiceGrpc.class) {
        if ((getGetAssociatedTaskMethod = SoterClientServiceGrpc.getGetAssociatedTaskMethod) == null) {
          SoterClientServiceGrpc.getGetAssociatedTaskMethod = getGetAssociatedTaskMethod = 
              io.grpc.MethodDescriptor.<soter.Soter.GetAssociatedTaskRequest, soter.Soter.GetAssociatedTaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "soter.SoterClientService", "getAssociatedTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.GetAssociatedTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  soter.Soter.GetAssociatedTaskResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SoterClientServiceMethodDescriptorSupplier("getAssociatedTask"))
                  .build();
          }
        }
     }
     return getGetAssociatedTaskMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SoterClientServiceStub newStub(io.grpc.Channel channel) {
    return new SoterClientServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SoterClientServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SoterClientServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SoterClientServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SoterClientServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SoterClientServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void isAvailableFor(soter.Soter.IsAvailableForRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.IsAvailableForResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getIsAvailableForMethodHelper(), responseObserver);
    }

    /**
     */
    public void hasAssociatedTask(soter.Soter.HasAssociatedTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.HasAssociatedTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHasAssociatedTaskMethodHelper(), responseObserver);
    }

    /**
     */
    public void getAssociatedTask(soter.Soter.GetAssociatedTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.GetAssociatedTaskResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssociatedTaskMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getIsAvailableForMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.IsAvailableForRequest,
                soter.Soter.IsAvailableForResponse>(
                  this, METHODID_IS_AVAILABLE_FOR)))
          .addMethod(
            getHasAssociatedTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.HasAssociatedTaskRequest,
                soter.Soter.HasAssociatedTaskResponse>(
                  this, METHODID_HAS_ASSOCIATED_TASK)))
          .addMethod(
            getGetAssociatedTaskMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                soter.Soter.GetAssociatedTaskRequest,
                soter.Soter.GetAssociatedTaskResponse>(
                  this, METHODID_GET_ASSOCIATED_TASK)))
          .build();
    }
  }

  /**
   */
  public static final class SoterClientServiceStub extends io.grpc.stub.AbstractStub<SoterClientServiceStub> {
    private SoterClientServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterClientServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterClientServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterClientServiceStub(channel, callOptions);
    }

    /**
     */
    public void isAvailableFor(soter.Soter.IsAvailableForRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.IsAvailableForResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIsAvailableForMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void hasAssociatedTask(soter.Soter.HasAssociatedTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.HasAssociatedTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHasAssociatedTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssociatedTask(soter.Soter.GetAssociatedTaskRequest request,
        io.grpc.stub.StreamObserver<soter.Soter.GetAssociatedTaskResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssociatedTaskMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SoterClientServiceBlockingStub extends io.grpc.stub.AbstractStub<SoterClientServiceBlockingStub> {
    private SoterClientServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterClientServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterClientServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterClientServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public soter.Soter.IsAvailableForResponse isAvailableFor(soter.Soter.IsAvailableForRequest request) {
      return blockingUnaryCall(
          getChannel(), getIsAvailableForMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.HasAssociatedTaskResponse hasAssociatedTask(soter.Soter.HasAssociatedTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getHasAssociatedTaskMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public soter.Soter.GetAssociatedTaskResponse getAssociatedTask(soter.Soter.GetAssociatedTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAssociatedTaskMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SoterClientServiceFutureStub extends io.grpc.stub.AbstractStub<SoterClientServiceFutureStub> {
    private SoterClientServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SoterClientServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SoterClientServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SoterClientServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.IsAvailableForResponse> isAvailableFor(
        soter.Soter.IsAvailableForRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getIsAvailableForMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.HasAssociatedTaskResponse> hasAssociatedTask(
        soter.Soter.HasAssociatedTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getHasAssociatedTaskMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<soter.Soter.GetAssociatedTaskResponse> getAssociatedTask(
        soter.Soter.GetAssociatedTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssociatedTaskMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_IS_AVAILABLE_FOR = 0;
  private static final int METHODID_HAS_ASSOCIATED_TASK = 1;
  private static final int METHODID_GET_ASSOCIATED_TASK = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SoterClientServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SoterClientServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_IS_AVAILABLE_FOR:
          serviceImpl.isAvailableFor((soter.Soter.IsAvailableForRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.IsAvailableForResponse>) responseObserver);
          break;
        case METHODID_HAS_ASSOCIATED_TASK:
          serviceImpl.hasAssociatedTask((soter.Soter.HasAssociatedTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.HasAssociatedTaskResponse>) responseObserver);
          break;
        case METHODID_GET_ASSOCIATED_TASK:
          serviceImpl.getAssociatedTask((soter.Soter.GetAssociatedTaskRequest) request,
              (io.grpc.stub.StreamObserver<soter.Soter.GetAssociatedTaskResponse>) responseObserver);
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

  private static abstract class SoterClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SoterClientServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return soter.Soter.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SoterClientService");
    }
  }

  private static final class SoterClientServiceFileDescriptorSupplier
      extends SoterClientServiceBaseDescriptorSupplier {
    SoterClientServiceFileDescriptorSupplier() {}
  }

  private static final class SoterClientServiceMethodDescriptorSupplier
      extends SoterClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SoterClientServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SoterClientServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SoterClientServiceFileDescriptorSupplier())
              .addMethod(getIsAvailableForMethodHelper())
              .addMethod(getHasAssociatedTaskMethodHelper())
              .addMethod(getGetAssociatedTaskMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
