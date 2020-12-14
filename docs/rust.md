# Rust

Soter can check Rust programs.

## Scheduling Nondeterminism

Let's look at the following Rust program taken from the [Rustnomicon](https://doc.rust-lang.org/nomicon/races.html) that demonstrates how, while Rust eliminates data races, race conditions are still possible.

```
fn race_condition_1() -> () {
    use std::sync::atomic::{AtomicUsize, Ordering};
    use std::sync::Arc;

    let data = vec![1, 2, 3, 4];
    // Arc so that the memory the AtomicUsize is stored in still exists for
    // the other thread to increment, even if we completely finish executing
    // before it. Rust won't compile the program without it, because of the
    // lifetime requirements of thread::spawn!
    let idx = Arc::new(AtomicUsize::new(0));
    let other_idx = idx.clone();

    // `move` captures other_idx by-value, moving it into this thread
    thread::spawn(move || {
        // It's ok to mutate idx because this value
        // is an atomic, so it can't cause a Data Race.
        other_idx.fetch_add(10, Ordering::SeqCst);
    });

    // Index with the value loaded from the atomic. This is safe because we
    // read the atomic memory only once, and then pass a copy of that value
    // to the Vec's indexing implementation. This indexing will be correctly
    // bounds checked, and there's no chance of the value getting changed
    // in the middle. However our program may panic if the thread we spawned
    // managed to increment before this ran. A race condition because correct
    // program execution (panicking is rarely correct) depends on order of
    // thread execution.
    let index = idx.load(Ordering::SeqCst);
    println!("index: {}", index);
    let data = data[index];
    println!("{}", data);
}
```

This program can execute a number of different ways, but there are a set of executions
that will cause this program to panic.  First, this program maintains an atomic reference counter wrapping an an atomic integer.  A thread is spawned to increment this shared integer by 10.  This integer is then used to index into an array on the main thread.  As Rust has bounds checking at runtime, if the increment occurs before the integer is read, the program will panic because the array is only of length 4, and 10 is outside of that and detected by the bounds checker.

First, we need to modify this program to run `async` and initialize Soter.  We can do that 
by making a main function like so.  The main function needs to be asynchronous as the Soter client running in Rust needs to listen for requests from the Soter server: the Soter server will periodically ask the client questions, like whether or not certain resources are available when determining which thread to schedule next.

```
#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let soter_server = soter::server::start().await;

    race_condition_1();
   
    soter::server::stop(soter_server).await;

    Ok(())
}
```

Next, we use Soter's ```thread::spawn``` instead of the one from the standard library ```std::thread::spawn.```

```
// `move` captures other_idx by-value, moving it into this thread
soter::thread::spawn(move || {
    // It's ok to mutate idx because this value
    // is an atomic, so it can't cause a Data Race.
    other_idx.fetch_add(10, Ordering::SeqCst);
});
```

Now, this enables Soter to control thread execution and identify the bug within a few iterations.

## Data Nondeterminism

Soter also supports testing with data nondeterminism.  Let's consider the following program, which has a 
fake bug that's triggered when a randomly generated number is a particular value.

```
use rand::Rng;

fn main() -> ()) {
    let soter_server = soter::server::start().await;

    let secret_number : i32 = rand::thread_rng().gen_range(1, 50);

    if secret_number == 23 {
        std::process::exit(1);
    }

    ()
}
```

First, let's convert the application to a Tokio asynchronous application.  Like we showed above, this
let's us run the client-side Soter server that's used to communicate between Rust and Java.  Once we 
convert the function, we then add the boilerplate code to both start and shutdown the Soter client-side
server at the end of the execution.

```
#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let soter_server = soter::server::start().await;

    let secret_number : i32 = rand::thread_rng().gen_range(1, 50);

    if secret_number == 23 {
        std::process::exit(1);
    }
   
    soter::server::stop(soter_server).await;

    Ok(())
}
```

Finally, prefix calls to ```rand``` library functions with ```soter```.  

At the moment, the only functions that are supported are ```random()``` to get a random 
boolean and ```gen_range``` to generate a random integer within a given range.

```
let secret_number : i32 = soter::rand::thread_rng().gen_range(1, 50);
```

## Running Soter on a Rust Program

Once you've enabled your application for Soter, you can run it on your Rust program like so:

```
$(brazil-bootstrap)/bin/soter $PATH_TO_RUST_PROGRAM
```

Once you've identified a failure, a ```counterexample.trace``` file will be produced containing the schedule 
that exhibits the failure.  You can then reproduce this failure using the following command with an interactive 
debugger, if necessary.

```
$(brazil-bootstrap)/bin/soter -r counterexample.trace $PATH_TO_RUST_PROGRAM
```

To see all of the possible options for the Soter tool, you can do the following:

```
$(brazil-bootstrap)/bin/soter -h

usage: soter [OPTIONS] [PROGRAM]
 -d,--debug <arg>           run interactive debugger (e.g. /usr/bin/lldb)
 -e,--errorDepth <arg>      set the depth at which an error is thrown
 -h,--help                  display this help
 -i,--numIterations <arg>   specify the number of iterations to run
 -m,--maxDepth <arg>        set the depth that which no further
                            exploration will continue
 -r,--replay <arg>          replay failure using the following
                            counterexample file
 -s,--search <arg>          search type (e.g., dfs, stateless)
 -t,--strategy <arg>        strategy type (e.g., roundrobin, random)
```

## Debugging

To enable interactive debugging, use the ```-d``` switch and specify the path to the debugger you want to launch. 

For example, to debug your Rust program with ```lldb```, do the following:

```
$(brazil-bootstrap)/bin/soter -d /usr/bin/lldb -r counterexample.trace rust/soter/target/debug/rand 

(lldb) target create "rust/soter/target/debug/rand"
Current executable set to '/Volumes/workplace/new-soter/src/Soter/rust/soter/target/debug/rand' (x86_64).
(lldb) r
Process 69434 launched: '/Volumes/workplace/new-soter/src/Soter/rust/soter/target/debug/rand' (x86_64)
nondeterministicIntegerWithBoundChoice = Ok(Response { metadata: MetadataMap { headers: {"content-type": "application/grpc", "grpc-encoding": "identity", "grpc-accept-encoding": "gzip", "grpc-status": "0"} }, message: NondeterministicIntegerWithBoundChoiceResponse { result: 22 } })
Process 69434 exited with status = 1 (0x00000001) 
(lldb) 
```