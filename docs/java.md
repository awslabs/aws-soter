# Java

Soter was built for checking Java applications, and, is implemented in Java itself.  Here's how to get started with Soter.

## Data Nondeterminism

Suppose you wanted to test the following program with Soter.  

```
import java.util.Random;

public class RandomNumber {
    public void run() {
        Random random = new Random();

        int n1 = random.nextInt(25);
        int n2 = random.nextInt(25);
        int n3 = random.nextInt(25);

        if (n3 == 3 && n2 == 2 && n1 == 1) {
            throw new RuntimeException("Hit this fake bug!");
        }
    }
}
```

This program generates several random numbers and contains
a bug that will only be triggered if the random numbers chosen are exactly `1`, `2`, and `3`: in that precise order.  In
order to test this program, we need to explore different choices of random number combinations through some sort of 
systematic testing.  To do this, Soter needs to control the random number generator: in order to give control of the random
number generator to Soter, simply replace your import statement to use the Soter-controlled random number generator instead
of the default random number generator provided by Java: ```import java.util.Random;``` becomes
```import com.amazon.soter.java.util.Random;```.

```
import com.amazon.soter.java.util.Random;

public class RandomNumber {
    public void run() {
        Random random = new Random();

        int n1 = random.nextInt(25);
        int n2 = random.nextInt(25);
        int n3 = random.nextInt(25);

        if (n3 == 3 && n2 == 2 && n1 == 1) {
            throw new RuntimeException("Hit this fake bug!");
        }
    }
}
```

## Running Examples with Soter and JUnit

There are multiple ways to run tests using Soter.  You'll probably want to use the JUnit annotation most of the time 
until you need more control over Soter's configuration or are testing applications that are not written in Java.

### Using JUnit Annotation

Soter provides a JUnit annotation that can be used to write tests that use Soter quickly and efficiently.  First, create
your JUnit test class.  Then, use the provided ```@RunWith(SoterCheckRunner.class)``` annotation to tell JUnit to run
this test with Soter's runner instead of the default JUnit test runner.  Then, use the ```@SoterCheck``` annotation to specify
the type of test you'd like to run with Soter.  Inside the body of the test method, call the function that you want to have
checked with Soter.

Here's a JUnit test that we can use to run the `RandomNumber` example from above.

```
@RunWith(SoterCheckRunner.class)
public class RandomNumberTest {
    @SoterCheck(search = SearchType.StatelessSearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 10000, expected = RuntimeException.class)
    public void statelessSearchRandomNumberTest() {
        RandomNumber r = new RandomNumber();
        r.run();
    }
}
```

The SoterCheck annotation takes a number of parameters that can be used for configuring Soter.  Here are a few of them:

* `search`: specify the search type to be used. (e.g., `SearchType.StatelessSearch`, `SearchType.DFS`)
* `strategy`: specify the strategy to be used. (e.g., `StrategyType.RandomStrategy`, `StrategyType.RoundRobinStrategy`)
* `maxDepth`: the maximum depth (w.r.t. scheduling choices) allowed before the current path is abandoned.
* `errorDepth`: the maximum depth (w.r.t. scheduling choices) before Soter throws a `DepthBoundExceededException` exception.
* `maxIterations`: the maximum number of iterations Soter should run before terminating, `-1` disables this bound.
* `expected`: if the test method is expected to throw an exception, this can be specified here.  If the method fails to throw
this exception, the test will fail.

### Without JUnit Annotation

It is also possible to test Soter applications without using the JUnit annotation.  This is useful if you want to use
a different testing framework or use Soter to test programs written in other languages (e.g., Rust, Python.)

```
SearchType searchType = SearchType.StatelessSearch;
StrategyType strategyType = StrategyType.RandomStrategy;
TestingTarget testingTarget = new TestingTarget(...);
TestingEngine testingEngine = new TestingEngine(searchType, strategyType, maxDepth, errorDepth, numIterations, testingTarget);
testingEngine.run();
```

There are three possible `TestingTarget`s, depending on what you want to test:

* `ExternalTestingTarget` which specifies the path to an external executable that will be run by Soter; this executable should indicate pass/fail through its exit code
* `JUnitTestingTarget` which specifies the object and method of an existing JUnit test that should be run under Soter
* `LambdaTestingTarget` which specifies a lambda function that contains the code to be executed under Soter

## Replaying Counterexamples

When Soter identifies a counterexample, it will produce a trace file that can be used to reproduce the failure.  If you're 
already using JUnit annotations to run your Soter tests, you can use the annotation to specify that you want to do replay while
also providing the path to the counterexample file.  

Here's an example of a JUnit test that re-runs a failure in the RandomNumber application from above using a saved counterexample.  
In the annotation, the search type has been replaced with ```SearchType.ReplaySearch``` and a new parameter ```previousTraceFile``` is provided that 
contains the path to the recorded counterexample.

```
@SoterCheck(search = SearchType.ReplaySearch, strategy = StrategyType.RandomStrategy, maxDepth = 10000, errorDepth = 1000, maxIterations = 1, expected = RuntimeException.class, previousTraceFile = "counterexamples/randomNumberTest.trace")
public void replayRandomNumberTest() {
    RandomNumber r = new RandomNumber();
    r.run();
}
```

## Scheduling Nondeterminism

We can also use Soter to check for bugs that may arise as a result of scheduling nondeterminism.  

Let's look at this example program.  In this program excerpt, five threads try to change the value of a shared register.  Depending
on thread execution order, the value may be `GoodMorning` or it may be `HelloWorld`.  If the value doesn't end up as `HelloWorld`, we 
want to throw an exception and crash the program. 

```
public class HelloWorld {
    private String value;

    private final String HelloWorld = "Hello World!";
    private final String GoodMorning = "Good Morning!";

    private class Worker implements Runnable {
        private String ours;

        public Worker(String value) {
            this.ours = value;
        }

        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            value = this.ours;
        }
    }

    public void run() {
        ForkJoinPool customThreadPool = new ForkJoinPool(5);

        customThreadPool.execute(new Worker(GoodMorning));
        customThreadPool.execute(new Worker(HelloWorld));
        customThreadPool.execute(new Worker(HelloWorld));
        customThreadPool.execute(new Worker(HelloWorld));
        customThreadPool.execute(new Worker(HelloWorld));

        awaitAll(customThreadPool);

        if (value == null || !value.equals(HelloWorld)) {
            throw new HelloWorldSpecificationException("Value was " + value + " not " + HelloWorld);
        }
    }
}
```

To test this program with Soter, we only need to swap the thread pool implementation to a Soter-controlled thread pool. 
To do this, we simple prefix `com.amazon.soter` to the import statement, changing ```import java.util.concurrent.ForkJoinPool;
``` to ```import com.amazon.soter.java.util.concurrent.ForkJoinPool;```  Using the Soter-controlled thread pool will allow 
Soter to control the thread execution order and explore scheduling nondeterminism while testing.

We have implemented several versions of Soter-enabled concurrency libraries, but not everything is covered.  

Here's a list of what we have done so far:

* `java.lang`
    * `Runnable`
    * `Thread`
* `java.util`
    * `Random`
    * `concurrent`
        * `locks`
            * `Condition`
            * `ReentrantLock`
        * `Callable`
        * `Executors`
        * `ForkJoinPool`
        * `Future` 

## Unsupported Features

There are some concurrency features that Soter does not support.  Here's what we have encountered so far.

### `synchronized` keyword

The `synchronized` keyword in Java can be used on methods of an object to ensure synchronized access (e.g., monitors.)  The 
implicit lock that is used to control access to these methods are the instance of the object itself and the lock used
is a standard mutual exclusion.  Because this keyword's functionality is provided by the runtime and not a library, 
Soter does not have the ability to control scheduling and lock access when using this keyword.  Therefore, in order to use
Soter on application code that uses the `synchronized` keyword, the application must be rewritten to use an explicit lock.  

Let's look at an example.  Here is the example of a bounded buffer that uses the `synchronized` keyword and conditions 
(e.g., `wait` and `notify`) to signal threads to execute based on the conditions of the lock.

```
public class BoundedBufferWithSynchronizedBlock implements BoundedBuffer {
    private Object[] buffer;

    private int putAt;
    private int takeAt;
    private int occupied;

    public BoundedBufferWithSynchronizedBlock(int size) {
        this.buffer = new Object[size];
    }

    public synchronized void put(Object x) throws InterruptedException {
        while (occupied == buffer.length)
            wait();
        notify();
        ++occupied;
        putAt %= buffer.length;
        buffer[putAt++] = x;
    }

    public synchronized Object take() throws InterruptedException {
        while( occupied == 0 )
            wait();
        notify();
        --occupied;
        takeAt %= buffer.length;
        return buffer[takeAt++];
    }
}
```

To rewrite this example, we first need to add an explicit lock to the code and replace the use of the `synchronized` keyword
with calls to the `lock()` and `unlock()` methods.  Following that, we also need to use a condition variable to signal threads 
when the lock conditions change: we do this using the ```await()``` and ```signal()```/```signalAll()``` methods.

Our modified implementation of the bounded buffer to remove the use of the `synchronized` keyword looks like so:

```
public class BoundedBufferWithLocksAndConditionsBroken implements BoundedBuffer {
    private Lock lock;
    private Condition cond;

    public BoundedBufferWithLocksAndConditionsBroken(int size) {
        this.lock = new ReentrantLock();
        this.cond = this.lock.newCondition();
        this.items = new Object[size];
    }

    final Object[] items;
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                cond.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                cond.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            cond.signalAll();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
```

### ```CountDownLatch.getCount()```

Typically, the ```getCount()``` method on Java's `CountDownLatch` should only be used for debugging; whereas, the ```await()``` 
method should be used to await on the condition being true.  Code that calls ```getCount()``` in a loop until a condition is true
instead of the blocking ```await()``` method will need to explicitly yield control to Soter in order for the checker to properly
schedule the threads responsible for the condition becoming true.  