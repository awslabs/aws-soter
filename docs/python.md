# Python

Soter can check Python programs.

However, it's crucial to understand how Python concurrency works
to understand how Soter checking works for Python programs.

## Data Nondeterminism

Soter exposes three ways for handling random values, one form of data nondeterminism.  Soter exposes three different
random APIs: one for generating a random integer without a upper bound, one for generating a random integer with an upper
bound and one for generating a random boolean.  

However, Python's random number provides a completely different set of functions for generating random values.  First, 
Python provides a ```random()``` function that returns a random floating point value in the range ```0 <= 1```.  Python also
provides a ```randint(lower, upper)``` function that returns an integer within an inclusive lower bound and exclusive 
upper bound.  Finally, there's no way to get a random boolean in Python, so developers are forced to use ```getrandbits(1)```
and cast the resulting value to boolean.  

As it currently stands, ```SoterRandom``` provides a new function ```randbool``` for generating random boolean values
and an API-comptabile ```randint``` function for generating random integers within a range which uses the random with upper bound functionality provided by Soter.

Here's an example of a Python program that uses random values with Soter.

```
random = SoterRandom()

if __name__ == "__main__":
    lower = 1
    upper = 10

    x = random.randint(lower, upper)
    print("x =", x)

    y = random.randint(lower, upper)
    print("y =", y)

    if x == y:
        sys.exit("This is a crash.")
```

## Scheduling Nondeterminism

Python uses a Global Interpreter Lock (GIL) that prevents more than a single thread of execution from 
running at a given point in time.  This introduces a number of complexities in checking Python programs.  First, a basic understanding of how Python concurrency works.  When multiple threads of execution are running in Python, without the use of built-in concurrency primitives (e.g., locks) Python will automatically perform context switches when either of the first of the following events occurs: blocking IO, or whether 100 instructions have been executed in the VM for the currently executing thread.  This introduces a number of complexities when it comes to systematic checking with Soter.

Consider the following program, which has a data race and will be used to illustrate many of the challenges:

```
def thread_function(id):
    # Read global.
    global counter
    tmp = counter

    # Write global
    counter = tmp + 1

threads = []

for x in range(1, 4):
    x = Thread(target=thread_function, args=(x,))
    threads.append(x)

for x in threads:
    x.start()

for x in threads:
    x.join()
```

This program, under multiple executions will produce different results for the register `counter`.  
There are three threads, each executing one after another, but because of scheduling nondeterminism, 
will produce either 1, 2 or 3 as the final value for `counter`.  The problem here is a data race: 
each thread accesses the global `counter`, reads the value and performs an increment operation on 
`counter`.  Depending on the nondeterminism in the scheduler, the result can either be 1, 2, or 3 
resulting from the lack of synchronization when accessing the shared counter to perform the increment 
operation.  However, if you were to run this program on your computer a number of times, you would not 
see this issue.

To adapt this program to work with Soter, we must take the first step of switching the `Thread` library 
to use the Soter-enabled thread library `SoterThread`.  This library communicates with Soter over TCP to 
register threads and schedule them accordingly.  

However, this is only the first step.

```
def thread_function(id):
    # Read global.
    global counter
    tmp = counter

    # Write global
    counter = tmp + 1

threads = []

for x in range(1, 4):
    x = SoterThread(target=thread_function, args=(x,))
    threads.append(x)

for x in threads:
    x.start()

for x in threads:
    x.join()
```

This now enables Soter to track the currently executing threads of the program.  However, Soter will only switch executing threads at `yield` points.  In Python, this is especially difficult, because these context switches, when running without Soter, can happen anywhere -- anywhere where the 100 instruction count is exceeded or external IO is perfomed.  For simplicity, let's assume that we know precisely where to place a yield that will cause our test to fail.

```
def thread_function(id):
    # Read global.
    global counter
    tmp = counter

    soter.yield_task()

    # Write global
    counter = tmp + 1

threads = []

for x in range(1, 4):
    x = SoterThread(target=thread_function, args=(x,))
    threads.append(x)

for x in threads:
    x.start()

for x in threads:
    x.join()
```

By inserting a `yield` point precisely after the `counter` register value is read and before the 
value is written, we can easily identify the error in no more than two random executions with Soter: 
this interleaving will trigger the register to be written with a value that's less than the desired 
value -- 3 -- because two read operations interleave with the write operations on the same register.  

However, how does one know where to automatically insert these `yield` points? 

After some investigation, I thought that one possible solution is that we could use an AST transformation 
to automatically insert these yield points after every single command in the program that we are testing: 
however, this isn't sufficent for a few reasons that we will outline below.

First, yield points would not only have to be inserted in the program under test but also in all of the 
supporting libraries that are being used by the program we are testing as these context switches can 
happen anywhere -- this is based on the concurrency rules we outlined at the start of this section.   
But, this isn't the only reason this is challenging.

Let's consider a more idiomatic Python version of our original program:

```
def thread_function(id):
    # Read global.
    global counter
    counter = counter + 1

threads = []

for x in range(1, 4):
    x = SoterThread(target=thread_function, args=(x,))
    threads.append(x)

for x in threads:
    x.start()

for x in threads:
    x.join()
```

In the more idiomatic version of our program, where we use the `+=` operator or `x = x + 1` 
statements -- yes, this bug can still occur! 

Why is this?  Well, Python can perform a context switch in between the expression on the RHS 
of the assignment, but prior to the assignment to the register on the LHS of the statement 
`counter = counter + 1`.  This unfortunately implies that VM support is required to properly 
instrument context switching with a `yield` to Soter in order to capture all possible concurrency 
anomalies, as these operations are not capturable in user-space without rewriting the AST to remove 
the use of these types of expressions that remove the use of intermediate variables.  *Read a different 
way: we need to ensure that Soter yield points are inserted between every application-level instruction 
-- the unit of divisibility w.r.t the Python scheduler -- that might introduce a concurrency anomaly 
to properly capure all possible execution anomalies.*

Therefore, if you're going to be testing Python today, you'll need to manually insert yield points 
wherever you want to look for concurrent anomalies and that any errors you find will be an 
underapproximation of errors that might actually occur in real Python programs -- that is, as long as 
Soter lives in Python's user-space.

Needless to say, with the yield point we added to the program manually during this exercise, we 
*are able* to identify the concurrency bug that does exist in this program.  This might be an indication 
regarding why the lack of Python model checking tools exist -- still, we were able to find bugs with 
manual instrumentation.

## Running Soter on a Python Program

Once you've enabled your application for Soter, you can run it on your Python program like so:

```
$(brazil-bootstrap)/bin/soter $PATH_TO_PYTHON_PROGRAM
```

Once you've identified a failure, a ```counterexample.trace``` file will be produced containing the schedule 
that exhibits the failure.  You can then reproduce this failure using the following command with an interactive 
debugger, if necessary.

```
$(brazil-bootstrap)/bin/soter -r counterexample.trace $PATH_TO_PYTHON_PROGRAM
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