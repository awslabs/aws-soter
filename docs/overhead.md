# Communication Overhead

Let's talk about overhead.

For now, with the Python client using gRPC, overhead is about 12ms over loopback for Soter (in Java) to talk to a Soter client in Python.  This seems to be about 12x compared to what a Soter client would be in C++ or Java (estimated at 1-2ms) due to the nature of Python.  As we haven't developed a Soter client in those languages yet, we assume these numbers -- reported by the developers of the client libraries -- are correct.  Thrift supports a shared-memory mechaism for performing RPC -- but, we haven't used this yet and documentation is sparse; however, it seems to be implemenented if the performance is necessary.  For Rust, we've implemented the gRPC interface, but have let to benchmark the round-trip time.

For an example of how to use Soter to drive an external test in another language, you can refer to ```ExternalTest``` which launches an external program using multi-threaded Java.  This client program is expected to do two things:  first, connect to the Soter server running in Java and open it's own server that the Java server can communicate back with.  Soter (in Java), as the test runner, will issues RPC's back to the client process -- the one under test -- to request information on whether threads are schedulable using an ```isResourceAvailable``` API; if the client system is suing locks, for instance, the cleint needs to track whether or not a lock is held to be able to inform Soter whether or not the thread is schedulable.  Clients are responsible for implementing the Soter interface -- as detailed in the protocol buffer or thrift specifications -- on creating tasks, creating resources, scheduling tasks, blocking tasks, etc.

For now, the Java API communicates in-process in the same JVM; for details on how different langauges implement the Soter API, we refer you to the definitons of the Thrift and Protocol Buffers APIs in the ```configuration``` directory for further information.  The Python implementation lives in ```python``` and the WIP Rust implementation lives in the ```rust``` directory.