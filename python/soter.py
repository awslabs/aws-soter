import sys
import grpc
import time
import random
import threading

from concurrent import futures

import soter_pb2
import soter_pb2_grpc

class SoterRandom:
    def __init__(self):
        True

    def randint(self, id, lower, upper):
        global soter
        val = soter.nondeterministic_integer_with_bound_choice(id, upper - lower) # TODO NEEDS BOUND
        return val + lower

    def randbool(self, id):
        global soter
        return soter.nondeterministic_boolean_choice(id)

class SoterClientServiceServicer(soter_pb2_grpc.SoterClientServiceServicer):
    def isAvailableFor(self, request, context):
        global soter
        soter.log("Received isAvailableFor request for taskId: " + request.taskId + " and resourceId: " + request.resourceId)

        for thread in soter.get_threads():
            if thread.get_id() == int(request.resourceId):
                soter.log(" => thread " + str(thread.get_id()) + " finished? " + str(thread.is_finished()))
                return soter_pb2.IsAvailableForResponse(result=thread.is_finished())

        soter.log(" => looking for resource " + request.resourceId + "but wasn't found.")
        return soter_pb2.IsAvailableForResponse(result=False)

class Soter:
    def __init__(self):
        # Set global instance.
        global soter
        soter = self

        # Initialize the log file; we close after each write just to force a flush to 
        # enable log tailing when running the test.
        f = open("python_output", "w")
        f.write("-----------------------" + "\n")
        f.close()

        self.log("Soter client initializing.")

        # TODO: env variable
        self.runtime_controlled = True
        # self.runtime_controlled = False
        self.channel = grpc.insecure_channel('localhost:50051')
        self.stub = soter_pb2_grpc.SoterServiceStub(self.channel)
        self.next_id = 1

        self.threads = []

        self.server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        soter_pb2_grpc.add_SoterClientServiceServicer_to_server(
            SoterClientServiceServicer(), self.server
        )
        self.server.add_insecure_port('[::]:60051')

        def start_server():
            self.log("Server starting...")
            self.server.start()
            self.server.wait_for_termination()
        self.server_thread = threading.Thread(target=start_server, args=())
        self.server_thread.start()

    def stop(self):
        self.log("Stopping server.")
        self.server.stop(10)

    def log(self, message):
        f = open("python_output", "a")
        f.write(message + "\n")
        print(message)
        f.close()
        True

    def get_threads(self):
        return self.threads

    def add_thread(self, thread):
        self.threads.append(thread)

    def wait_for_completion(self, taskType, id, future):
        while future.done() != True:
            # self.log("-> Waiting for " + taskType + " to finish for task " + str(id))
            True
        return future.result() # Need to invoke because if the call failed due to timeout
                               # done will be true, but exeception won't be thrown until the 
                               # result() method is invoked.

    def create_task(self):
        if self.runtime_controlled:
            self.log("Create task for task " + str(self.next_id) + " starting.")
            start_time = time.time()

            create_task_request = soter_pb2.CreateTaskRequest(id=str(self.next_id))
            future = self.stub.createTask.future(create_task_request)
            self.wait_for_completion("createTask", self.next_id, future)

            end_time = time.time()
            diff_time = (end_time - start_time) 
            self.log("Create task took: " + str(diff_time))

            self.log("Create task for task " + str(self.next_id) + " finished.")

        assigned_id = self.next_id
        self.next_id = self.next_id + 1
        return assigned_id

    def start_task(self, id):
        if self.runtime_controlled:
            self.log("Start task for task " + str(id) + " starting.")
            start_task_request = soter_pb2.StartTaskRequest(id=str(id))
            future = self.stub.startTask.future(start_task_request)
            self.wait_for_completion("startTask", id, future)
            self.log("Start task for task " + str(id) + " finished.")

    def block_task(self, id):
        if self.runtime_controlled:
            self.log("Block task for task " + str(id) + " starting.")
            block_task_request = soter_pb2.BlockTaskRequest(id=str(id))
            future = self.stub.blockTask.future(block_task_request)
            self.wait_for_completion("blockTask", id, future)
            self.log("Block task for task " + str(id) + " finished.")
            
    def update_state(self, id, type=soter_pb2.UpdateType.NO_OP, resource_id=None):
        if self.runtime_controlled:
            soter.log("Update state " + str(id) + " starting with update type " + str(type) + " and resource identifier: " + str(resource_id))
            update_state_request = soter_pb2.UpdateStateRequest(id=str(id), updateType=type, resourceId=str(resource_id))
            future = self.stub.updateState.future(update_state_request)
            self.wait_for_completion("updateState", id, future)
            soter.log("Update state " + str(id) + " ending with update type " + str(type) + " and resource identifier: " + str(resource_id))

    def yield_task(self, id, type=soter_pb2.UpdateType.NO_OP, resource_id=None):
        if self.runtime_controlled:
            start_time = time.time()
            soter.log("Yield task " + str(id) + " starting with update type " + str(type) + " and resource identifier: " + str(resource_id))
            yield_task_request = soter_pb2.YieldTaskRequest(id=str(id), updateType=type, resourceId=str(resource_id))
            future = self.stub.yieldTask.future(yield_task_request)
            self.wait_for_completion("yieldTask", id, future)
            soter.log("Yield task " + str(id) + " ending with update type " + str(type) + " and resource identifier: " + str(resource_id))
            end_time = time.time()
            diff_time = (end_time - start_time) 
            self.log("Yield task operation took: " + str(diff_time))

    def exit_task(self, id):
        if self.runtime_controlled:
            self.log("Exit task for task " + str(id) + " starting.")
            exit_task_request = soter_pb2.ExitTaskRequest(id=str(id))
            future = self.stub.exitTask.future(exit_task_request)
            self.wait_for_completion("exitTask", id, future)
            self.log("Exit task for task " + str(id) + " finished.")

    def abnormal_exit_task(self, id):
        if self.runtime_controlled:
            self.log("Abnormal exit task for task " + str(id) + " starting.")
            abnormal_exit_task_request = soter_pb2.AbnormalExitTaskRequest(id=str(id))
            future = self.stub.abnormalExitTask.future(abnormal_exit_task_request)
            self.wait_for_completion("abnormalExitTask", id, future)
            self.log("Abnormal exit task for task " + str(id) + " finished.")

    def nondeterministic_boolean_choice(self, id):
        if self.runtime_controlled:
            self.log("Nondeterministic boolean choice for task " + str(id) + " starting.")
            nondeterministic_boolean_choice_request = soter_pb2.NondeterministicBooleanChoiceRequest()
            future = self.stub.nondeterministicBooleanChoice.future(nondeterministic_boolean_choice_request)
            result = self.wait_for_completion("nondeterministicBooleanChoice", id, future)
            self.log("Nondeterministic boolean choice for task " + str(id) + " finished.")
            return result.result
        else:
            random_bit = random.getrandbits(1)
            random_boolean = bool(random_bit)
            return random_boolean

    def nondeterministic_integer_choice(self, id):
        if self.runtime_controlled:
            self.log("Nondeterministic integer choice for task " + str(id) + " starting.")
            nondeterministic_integer_choice_request = soter_pb2.NondeterministicIntegerChoiceRequest()
            future = self.stub.nondeterministicIntegerChoice.future(nondeterministic_integer_choice_request)
            result = self.wait_for_completion("nondeterministicIntegerChoice", id, future)
            self.log("Nondeterministic integer choice for task " + str(id) + " finished.")
            return result.result
        else:
            random_int = random.randint(0, 9223372036854775807)
            return random_int

    def nondeterministic_integer_with_bound_choice(self, id, bound):
        if self.runtime_controlled:
            self.log("Nondeterministic integer with bound choice for task " + str(id) + " starting.")
            nondeterministic_integer_with_bound_choice_request = soter_pb2.NondeterministicIntegerWithBoundChoiceRequest(bound=bound)
            future = self.stub.nondeterministicIntegerWithBoundChoice.future(nondeterministic_integer_with_bound_choice_request)
            result = self.wait_for_completion("nondeterministicIntegerWithBoundChoice", id, future)
            self.log("Nondeterministic integer with bound choice for task " + str(id) + " finished.")
            return result.result
        else:
            random_int = random.randint(0, bound)
            return random_int

class SoterThread:
    # Start the thread, block it immediately.  Start order ensures
    # deterministic ordering of the process identifiers, blocking immediately
    # prevents the thread from executing until the first yield is invoked.
    def __init__(self, **kwargs):
        global soter
        self.id = soter.create_task()
        self.kwargs = kwargs
        self.finished = False

        target = self.kwargs['target']
        args = self.kwargs['args']

        def thread_function():
            start_time = time.time()

            soter.log("Wrapped thread for function: " + str(self.id) + " executing.")
            soter.block_task(self.id)

            soter.log("Unblocked task: " + str(self.id))
            target(*args)

            # We have to mark ourself as finished before exiting, because 
            # exiting can trigger a request to find out if we are done
            # for someone waiting at the checker.
            self.finished = True

            soter.log("Finished task: " + str(self.id))
            soter.exit_task(self.id)

            end_time = time.time()
            diff_time = (end_time - start_time) 
            soter.log("Thread took: " + str(diff_time))

        self.thread = threading.Thread(target=thread_function, args=())
        soter.add_thread(self)
        self.thread.start()

    def is_finished(self):
        return self.finished

    def get_id(self):
        return self.id

    def start(self):
        soter.log("Starting task: " + str(self.id))
        soter.start_task(self.id)
        soter.log("Yield being invoked by thread: " + str(self.id))
        soter.yield_task("0") # TODO: remove me.

    def join(self):
        soter.log("Entering join for thread: " + str(self.id))

        soter.log("Yielding task 0 because we need resource: " + str(self.id))
        soter.yield_task("0", soter_pb2.UpdateType.RESOURCE_NEEDED, self.id)
        soter.log("Yield finished for task 0.")

        soter.log("Joining thread, this should either complete the thread or return immediately as the thread is already finished.")
        self.thread.join()

        soter.log("Update state issuing for task 0 because we no longer need resource: " + str(self.id))
        soter.update_state("0", soter_pb2.UpdateType.RESOURCE_NO_LONGER_NEEDED, self.id)
        soter.log("Update state has completed.")

    def isAlive(self):
        return self.thread.isAlive()
