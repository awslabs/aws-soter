package com.amazon.soter.examples.lockacquisition;

import com.amazon.soter.java.util.Random;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;
import com.amazon.soter.java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Example that reverses lock acquisition order in order to provoke a livelock. */
public class LockAcquisition {
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();
    private ForkJoinPool customThreadPool = new ForkJoinPool(2);
    private final Logger logger = Logger.getLogger(LockAcquisition.class.getName());

    private class Worker implements Runnable {
        private int threadNumber;
        private Random random = new Random();

        Worker(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            if (random.nextBoolean()) {
                logger.fine("Thread " + threadNumber + " acquiring lock 1.");
                lock1.lock();
                logger.fine("Thread " + threadNumber + " acquiring lock 2.");
                lock2.lock();

                logger.fine("Thread " + threadNumber + " releasing lock 1.");
                lock1.unlock();
                logger.fine("Thread " + threadNumber + " releasing lock 2.");
                lock2.unlock();
            } else {
                logger.fine("Thread " + threadNumber + " acquiring lock 2.");
                lock2.lock();
                logger.fine("Thread " + threadNumber + " acquiring lock 1.");
                lock1.lock();

                logger.fine("Thread " + threadNumber + " releasing lock 2.");
                lock2.unlock();
                logger.fine("Thread " + threadNumber + " releasing lock 1.");
                lock1.unlock();
            }
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
        customThreadPool.execute(new Worker(1));
        customThreadPool.execute(new Worker(2));
        awaitAll(customThreadPool);
    }

    public static void main(String[] args) {
        LockAcquisition la = new LockAcquisition();
        la.run();
    }
}
