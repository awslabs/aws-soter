# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import soter_pb2 as soter__pb2


class SoterServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.createTask = channel.unary_unary(
                '/soter.SoterService/createTask',
                request_serializer=soter__pb2.CreateTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.CreateTaskResponse.FromString,
                )
        self.exitTask = channel.unary_unary(
                '/soter.SoterService/exitTask',
                request_serializer=soter__pb2.ExitTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.ExitTaskResponse.FromString,
                )
        self.cancelTask = channel.unary_unary(
                '/soter.SoterService/cancelTask',
                request_serializer=soter__pb2.CancelTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.CancelTaskResponse.FromString,
                )
        self.abnormalExitTask = channel.unary_unary(
                '/soter.SoterService/abnormalExitTask',
                request_serializer=soter__pb2.AbnormalExitTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.AbnormalExitTaskResponse.FromString,
                )
        self.yieldTask = channel.unary_unary(
                '/soter.SoterService/yieldTask',
                request_serializer=soter__pb2.YieldTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.YieldTaskResponse.FromString,
                )
        self.blockTask = channel.unary_unary(
                '/soter.SoterService/blockTask',
                request_serializer=soter__pb2.BlockTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.BlockTaskResponse.FromString,
                )
        self.startTask = channel.unary_unary(
                '/soter.SoterService/startTask',
                request_serializer=soter__pb2.StartTaskRequest.SerializeToString,
                response_deserializer=soter__pb2.StartTaskResponse.FromString,
                )
        self.updateState = channel.unary_unary(
                '/soter.SoterService/updateState',
                request_serializer=soter__pb2.UpdateStateRequest.SerializeToString,
                response_deserializer=soter__pb2.UpdateStateResponse.FromString,
                )
        self.nondeterministicBooleanChoice = channel.unary_unary(
                '/soter.SoterService/nondeterministicBooleanChoice',
                request_serializer=soter__pb2.NondeterministicBooleanChoiceRequest.SerializeToString,
                response_deserializer=soter__pb2.NondeterministicBooleanChoiceResponse.FromString,
                )
        self.nondeterministicIntegerChoice = channel.unary_unary(
                '/soter.SoterService/nondeterministicIntegerChoice',
                request_serializer=soter__pb2.NondeterministicIntegerChoiceRequest.SerializeToString,
                response_deserializer=soter__pb2.NondeterministicIntegerChoiceResponse.FromString,
                )
        self.nondeterministicIntegerWithBoundChoice = channel.unary_unary(
                '/soter.SoterService/nondeterministicIntegerWithBoundChoice',
                request_serializer=soter__pb2.NondeterministicIntegerWithBoundChoiceRequest.SerializeToString,
                response_deserializer=soter__pb2.NondeterministicIntegerWithBoundChoiceResponse.FromString,
                )


class SoterServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def createTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def exitTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def cancelTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def abnormalExitTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def yieldTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def blockTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def startTask(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def updateState(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def nondeterministicBooleanChoice(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def nondeterministicIntegerChoice(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def nondeterministicIntegerWithBoundChoice(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_SoterServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'createTask': grpc.unary_unary_rpc_method_handler(
                    servicer.createTask,
                    request_deserializer=soter__pb2.CreateTaskRequest.FromString,
                    response_serializer=soter__pb2.CreateTaskResponse.SerializeToString,
            ),
            'exitTask': grpc.unary_unary_rpc_method_handler(
                    servicer.exitTask,
                    request_deserializer=soter__pb2.ExitTaskRequest.FromString,
                    response_serializer=soter__pb2.ExitTaskResponse.SerializeToString,
            ),
            'cancelTask': grpc.unary_unary_rpc_method_handler(
                    servicer.cancelTask,
                    request_deserializer=soter__pb2.CancelTaskRequest.FromString,
                    response_serializer=soter__pb2.CancelTaskResponse.SerializeToString,
            ),
            'abnormalExitTask': grpc.unary_unary_rpc_method_handler(
                    servicer.abnormalExitTask,
                    request_deserializer=soter__pb2.AbnormalExitTaskRequest.FromString,
                    response_serializer=soter__pb2.AbnormalExitTaskResponse.SerializeToString,
            ),
            'yieldTask': grpc.unary_unary_rpc_method_handler(
                    servicer.yieldTask,
                    request_deserializer=soter__pb2.YieldTaskRequest.FromString,
                    response_serializer=soter__pb2.YieldTaskResponse.SerializeToString,
            ),
            'blockTask': grpc.unary_unary_rpc_method_handler(
                    servicer.blockTask,
                    request_deserializer=soter__pb2.BlockTaskRequest.FromString,
                    response_serializer=soter__pb2.BlockTaskResponse.SerializeToString,
            ),
            'startTask': grpc.unary_unary_rpc_method_handler(
                    servicer.startTask,
                    request_deserializer=soter__pb2.StartTaskRequest.FromString,
                    response_serializer=soter__pb2.StartTaskResponse.SerializeToString,
            ),
            'updateState': grpc.unary_unary_rpc_method_handler(
                    servicer.updateState,
                    request_deserializer=soter__pb2.UpdateStateRequest.FromString,
                    response_serializer=soter__pb2.UpdateStateResponse.SerializeToString,
            ),
            'nondeterministicBooleanChoice': grpc.unary_unary_rpc_method_handler(
                    servicer.nondeterministicBooleanChoice,
                    request_deserializer=soter__pb2.NondeterministicBooleanChoiceRequest.FromString,
                    response_serializer=soter__pb2.NondeterministicBooleanChoiceResponse.SerializeToString,
            ),
            'nondeterministicIntegerChoice': grpc.unary_unary_rpc_method_handler(
                    servicer.nondeterministicIntegerChoice,
                    request_deserializer=soter__pb2.NondeterministicIntegerChoiceRequest.FromString,
                    response_serializer=soter__pb2.NondeterministicIntegerChoiceResponse.SerializeToString,
            ),
            'nondeterministicIntegerWithBoundChoice': grpc.unary_unary_rpc_method_handler(
                    servicer.nondeterministicIntegerWithBoundChoice,
                    request_deserializer=soter__pb2.NondeterministicIntegerWithBoundChoiceRequest.FromString,
                    response_serializer=soter__pb2.NondeterministicIntegerWithBoundChoiceResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'soter.SoterService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class SoterService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def createTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/createTask',
            soter__pb2.CreateTaskRequest.SerializeToString,
            soter__pb2.CreateTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def exitTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/exitTask',
            soter__pb2.ExitTaskRequest.SerializeToString,
            soter__pb2.ExitTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def cancelTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/cancelTask',
            soter__pb2.CancelTaskRequest.SerializeToString,
            soter__pb2.CancelTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def abnormalExitTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/abnormalExitTask',
            soter__pb2.AbnormalExitTaskRequest.SerializeToString,
            soter__pb2.AbnormalExitTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def yieldTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/yieldTask',
            soter__pb2.YieldTaskRequest.SerializeToString,
            soter__pb2.YieldTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def blockTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/blockTask',
            soter__pb2.BlockTaskRequest.SerializeToString,
            soter__pb2.BlockTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def startTask(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/startTask',
            soter__pb2.StartTaskRequest.SerializeToString,
            soter__pb2.StartTaskResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def updateState(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/updateState',
            soter__pb2.UpdateStateRequest.SerializeToString,
            soter__pb2.UpdateStateResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def nondeterministicBooleanChoice(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/nondeterministicBooleanChoice',
            soter__pb2.NondeterministicBooleanChoiceRequest.SerializeToString,
            soter__pb2.NondeterministicBooleanChoiceResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def nondeterministicIntegerChoice(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/nondeterministicIntegerChoice',
            soter__pb2.NondeterministicIntegerChoiceRequest.SerializeToString,
            soter__pb2.NondeterministicIntegerChoiceResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def nondeterministicIntegerWithBoundChoice(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterService/nondeterministicIntegerWithBoundChoice',
            soter__pb2.NondeterministicIntegerWithBoundChoiceRequest.SerializeToString,
            soter__pb2.NondeterministicIntegerWithBoundChoiceResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)


class SoterClientServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.isAvailableFor = channel.unary_unary(
                '/soter.SoterClientService/isAvailableFor',
                request_serializer=soter__pb2.IsAvailableForRequest.SerializeToString,
                response_deserializer=soter__pb2.IsAvailableForResponse.FromString,
                )


class SoterClientServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def isAvailableFor(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_SoterClientServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'isAvailableFor': grpc.unary_unary_rpc_method_handler(
                    servicer.isAvailableFor,
                    request_deserializer=soter__pb2.IsAvailableForRequest.FromString,
                    response_serializer=soter__pb2.IsAvailableForResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'soter.SoterClientService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class SoterClientService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def isAvailableFor(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/soter.SoterClientService/isAvailableFor',
            soter__pb2.IsAvailableForRequest.SerializeToString,
            soter__pb2.IsAvailableForResponse.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)
