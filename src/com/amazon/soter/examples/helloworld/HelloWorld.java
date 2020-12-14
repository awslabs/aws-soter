package com.amazon.soter.examples.helloworld;

import com.amazon.soter.java.util.concurrent.ForkJoinPool;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Racy version of Hello World which will either print out Hello World or Good Morning depending on thread order. */
public class HelloWorld {
    private String value;

    private static final String HELLO_WORLD = "Hello World!";
    private static final String GOOD_MORNING = "Good Morning!";

    private final Logger logger = Logger.getLogger(HelloWorld.class.getName());

    /* Exceptions */
    class HelloWorldSpecificationException extends RuntimeException {
        HelloWorldSpecificationException(String errorMessage) {
            super(errorMessage);
        }
    }

    private class Worker implements Runnable {
        private String ours;

        Worker(String value) {
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

    public void awaitAll(ForkJoinPool customThreadPool) {
        /* This loop is here because this is await.waitAll() */
        customThreadPool.shutdown();

        while (true) {
            try {
                if (customThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        ForkJoinPool customThreadPool = new ForkJoinPool(5);

        /* Create application threads. */
        logger.fine("Creating thread 1");
        customThreadPool.execute(new Worker(GOOD_MORNING));

        logger.fine("Creating thread 2");
        customThreadPool.execute(new Worker(HELLO_WORLD));

        logger.fine("Creating thread 3");
        customThreadPool.execute(new Worker(HELLO_WORLD));

        logger.fine("Creating thread 4");
        customThreadPool.execute(new Worker(HELLO_WORLD));

        logger.fine("Creating thread 5");
        customThreadPool.execute(new Worker(HELLO_WORLD));

        logger.fine("Threads created, about to wait");

        awaitAll(customThreadPool);

        logger.fine("Done waiting.");

        logger.fine("Value: " + value);

        try {
            throw new RuntimeException();
        } catch (Exception e) {
            // Nothing.
        }

        if (value == null || !value.equals(HELLO_WORLD)) {
            logger.severe("Value was " + value + " not " + HELLO_WORLD);
            throw new HelloWorldSpecificationException("Value was " + value + " not " + HELLO_WORLD);
        }
    }
}
