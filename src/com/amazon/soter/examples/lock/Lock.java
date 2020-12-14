package com.amazon.soter.examples.lock;

import com.amazon.soter.java.util.concurrent.ForkJoinPool;
import com.amazon.soter.java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Basic lock acquisition and release example. */
public class Lock {
    private int shared = 0;
    private ReentrantLock lock;

    private final Logger logger = Logger.getLogger(Lock.class.getName());

    /* Exceptions */
    class LockSpecificationException extends RuntimeException {
        LockSpecificationException(String errorMessage) {
            super(errorMessage);
        }
    }

    private class InstrumentedWorker implements Runnable {
        private int threadNumber;

        InstrumentedWorker(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            /* If we are the first worker, run the third thread before taking the lock. */
            if (threadNumber == 1) {
                ForkJoinPool customThreadPool = new ForkJoinPool(1);
                customThreadPool.execute(new InstrumentedWorker(3));
                awaitAll(customThreadPool);
            }

            logger.fine("[THREAD " + this.threadNumber + "]: Starting.");
            lock.lock();
            logger.fine("[THREAD " + this.threadNumber + "]: Acquired lock.");

            try {
                shared += this.threadNumber;
                logger.fine("[THREAD " + this.threadNumber + "]: Incremented shared value to " + shared + ".");
            } finally {
                lock.unlock();
                logger.fine("[THREAD " + this.threadNumber + "]: Released lock.");
            }

            logger.fine("[THREAD " + threadNumber + "]: About to call exit for thread: " + threadNumber);
        }
    }

    public void awaitAll(ForkJoinPool customThreadPool) {
        /* This loop is here because this is await.waitAll() */
        customThreadPool.shutdown();

        while (true) {
            logger.fine("Waiting for termination...");
            try {
                if (customThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() throws LockSpecificationException {
        /* Use our versions of ReentrantLock and ForkJoinPool. */
        lock = new ReentrantLock();
        ForkJoinPool customThreadPool = new ForkJoinPool(2);

        /* Create application threads. */
        logger.fine("Creating thread 1");
        customThreadPool.execute(new InstrumentedWorker(1));

        logger.fine("Creating thread 2");
        customThreadPool.execute(new InstrumentedWorker(2));

        awaitAll(customThreadPool);

        if (shared != 6) {
            throw new LockSpecificationException("Value was " + shared + " not " + 6);
        }
    }

}
