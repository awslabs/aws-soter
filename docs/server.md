# Server Interface

The Soter server provides the following functions through both a gRPC and Thrift interface.

## API

Here's a high-level description of the API.

### Task Lifecycle Events

* `createTask`: Registers the creation of a task with Soter.  Task must be assigned an identifier by the client that is deterministic across executions: therefore, the client should ideally use an atomic counteer that keeps track of the spawn order of threads.  Identifiers for tasks should be a string.
* `blockTask`: Blocks a task immediately after spawning the thread at the client; server will unblock the thread as soon as it's chosen at the next yield.
* `startTask`: Some programming languages allow for the creation of a thread before the thread is started, this call should be made when the thread is actually started.
* `exitTask`: Called when a task is done with it's work and is about to terminate.
* `abnormalExitTask`: Called when a task has quit abnormally.

### Scheduling and State Management Events

* `yieldTask`: Yield control to the scheduler.  This call can provide to Soter updates about resources that are required or no longer required.  This call returns when the thread is scheduled again.
* `updateState`: Update state for the task by providing an update indicating whether or not resources are required.  This function is the same update as `yieldTask`, just, without the yield.

### Data Nondeterminism

* `nondeterministicIntegerChoice`: Request a nondeterministic integer with no upper bound.
* `nondeterministicIntegerWithBoundChoice`:  Request a nondeterministic integer choice from Soter with an upper bound.
* `nondeterministicBooleanChoice`: Request a nondeterministic boolean choice.

## Resource Updates

Resources use strings for identifiers.  These should be deterministic across different 
executions and should be client assigned.  Both ```yieldTask``` and ```updateState``` take updates using the ```SoterUpdateType``` enum, which contains the following three update types: 

* `SoterUpdateType.NoOp`: indicates that no update should be made.
* `SoterUpdateType.ResourceNeeded`: indicates that the current task requires a resource with a provided id to proceed (e.g., acquiring.) If we think of a lock, this call would be made as we yielded control.
* `SoterUpdateType.ResourceNoLongerNeeded`: indicates that the current task no longer requires a resource.
* `SoterUpdateType.ResourceAcquired`: indicates that the current task has acquired a resource.  If we think of a lock, this call would be made as soon as we resumed from a yield where we specified that we required a particular resource.
* `SoterUpdateType.ResourceReleased`: indicates that the current task has released a resource.  If we think of a lock, this call would be made as soon as the lock was released.