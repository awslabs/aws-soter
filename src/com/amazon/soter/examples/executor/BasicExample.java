// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Base case for executor examples. */
public abstract class BasicExample {
    private Register sharedRegister = new Register();

    public Register getSharedRegister() {
        return sharedRegister;
    }

    protected static final String HELLO_WORLD = "Hello World!";
    protected static final String GOOD_MORNING = "Good Morning!";

    protected final Logger logger = Logger.getLogger(BasicExample.class.getName());

    class SpecificationException extends RuntimeException {
        SpecificationException(String errorMessage) {
            super(errorMessage);
        }
    }

    public void awaitAll(ExecutorService threadPool) {
        /* This loop is here because this is await.waitAll() */
        threadPool.shutdown();

        while (true) {
            try {
                if (threadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void assertResult() {
        if (sharedRegister.getValue() == null || !sharedRegister.getValue().equals(HELLO_WORLD)) {
            logger.severe("Value was " + sharedRegister.getValue() + " not " + HELLO_WORLD);
            throw new SpecificationException("Value was " + sharedRegister.getValue() + " not " + HELLO_WORLD);
        }
    }

    public abstract void run();
}
