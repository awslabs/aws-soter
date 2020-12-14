# Client Interface

The Soter server expects that a Soter language client listens on a particular port and answers requests.

## Client Methods
 
* ```isAvailableFor```: takes a resource identifier and task identifier.  The client should return whether or not the resource is available for the task based on state stored in the client about who is holding which resources.
* ```hasAssociatedTask```: takes a resource identifier and determines whether or not the resources is associated with a task: for example, a thread is both a resource iand a task, as it executes itself but can also be joined.
* ```getAssociatedTask```: returns the associated task, if present.