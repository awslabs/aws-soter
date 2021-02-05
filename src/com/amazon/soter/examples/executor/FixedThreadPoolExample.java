// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.executor;

import com.amazon.soter.java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;

/** Use fixed thread pool (e.g., executor). */
public class FixedThreadPoolExample extends BasicExample {
    @Override
    public void run() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        /* Create application threads. */
        logger.fine("Creating thread 1");
        fixedThreadPool.execute(new ExampleWorker(GOOD_MORNING, getSharedRegister()));

        logger.fine("Creating thread 2");
        fixedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 3");
        fixedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 4");
        fixedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 5");
        fixedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Threads created, about to wait");

        awaitAll(fixedThreadPool);

        logger.fine("Done waiting.");

        logger.fine("Value: " + getSharedRegister());

        try {
            throw new RuntimeException();
        } catch (Exception e) {
            // Nothing.
        }

        assertResult();
    }

    public static void main(String[] args) {
        FixedThreadPoolExample example = new FixedThreadPoolExample();
        example.run();
    }
}
