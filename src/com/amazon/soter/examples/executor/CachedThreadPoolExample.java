// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.executor;

import com.amazon.soter.java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;

/** Cached thread pool example (e.g., executor). */
public class CachedThreadPoolExample extends BasicExample {
    @Override
    public void run() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        /* Create application threads. */
        logger.fine("Creating thread 1");
        cachedThreadPool.execute(new ExampleWorker(GOOD_MORNING, getSharedRegister()));

        logger.fine("Creating thread 2");
        cachedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 3");
        cachedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 4");
        cachedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Creating thread 5");
        cachedThreadPool.execute(new ExampleWorker(HELLO_WORLD, getSharedRegister()));

        logger.fine("Threads created, about to wait");

        awaitAll(cachedThreadPool);

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
        CachedThreadPoolExample example = new CachedThreadPoolExample();
        example.run();
    }
}
