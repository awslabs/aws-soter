// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.lockacquisition;

import com.amazon.soter.java.util.Random;
import com.amazon.soter.java.util.concurrent.locks.ReentrantLock;

import java.util.logging.Logger;

/** Example application that reverses lock acquisition order in order to provoke a deadlock. */
public class LockAcquisitionDeadlock {
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    private final Logger logger = Logger.getLogger(LockAcquisitionDeadlock.class.getName());

    private class Worker extends com.amazon.soter.java.lang.Thread {
        private int threadNumber;
        private boolean choice = false;
        private Random random = new Random();

        Worker(int threadNumber) {
            this.threadNumber = threadNumber;
            this.choice = random.nextBoolean();
            System.out.println("Thread " + threadNumber + " random choice is: " + choice);
        }

        public void run() {
            if (choice) {
                System.out.println("Thread " + threadNumber + " acquiring lock 1: " + lock1);
                lock1.lock();
                System.out.println("Thread " + threadNumber + " ACQUIRED lock 1: " + lock1);

                System.out.println("Thread " + threadNumber + " acquiring lock 2: " + lock2);
                lock2.lock();
                System.out.println("Thread " + threadNumber + " ACQUIRED lock 2: " + lock2);

                System.out.println("Thread " + threadNumber + " releasing lock 2: " + lock2);
                lock2.unlock();
                System.out.println("Thread " + threadNumber + " RELEASED lock 2: " + lock2);

                System.out.println("Thread " + threadNumber + " releasing lock 1: " + lock1);
                lock1.unlock();
                System.out.println("Thread " + threadNumber + " RELEASED lock 1: " + lock1);
            } else {
                System.out.println("Thread " + threadNumber + " acquiring lock 2: " + lock2);
                lock2.lock();
                System.out.println("Thread " + threadNumber + " ACQUIRED lock 2: " + lock2);

                System.out.println("Thread " + threadNumber + " acquiring lock 1: " + lock1);
                lock1.lock();
                System.out.println("Thread " + threadNumber + " ACQUIRED lock 1: " + lock1);

                System.out.println("Thread " + threadNumber + " releasing lock 1: " + lock1);
                lock1.unlock();
                System.out.println("Thread " + threadNumber + " RELEASED lock 1: " + lock1);

                System.out.println("Thread " + threadNumber + " releasing lock 2: " + lock2);
                lock2.unlock();
                System.out.println("Thread " + threadNumber + " RELEASED lock 2: " + lock2);
            }
        }
    }

    public void run() {
        Worker w1 = new Worker(1);
        Worker w2 = new Worker(2);

        w1.start();
        w2.start();

        try {
            System.out.println("Main thread waiting on thread 2.");
            w2.join();
            System.out.println("Main thread finished waiting on thread 2.");

            System.out.println("Main thread waiting on thread 1.");
            w1.join();
            System.out.println("Main thread finished waiting on thread 1.");
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
            e.printStackTrace();
        }

        System.out.println("I got here?");
    }

    public static void main(String[] args) {
        LockAcquisitionDeadlock la = new LockAcquisitionDeadlock();
        la.run();
    }
}
