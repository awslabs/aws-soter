// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.thread;

/** Soter implementation of the CountDownLatch. */
public class CountDownLatch {
    private com.amazon.soter.java.util.concurrent.CountDownLatch latch;

    private class HelloThread extends com.amazon.soter.java.lang.Thread {
        HelloThread() { }

        public void run() {
            try {
                com.amazon.soter.java.lang.Thread.sleep(10);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        latch = new com.amazon.soter.java.util.concurrent.CountDownLatch(2);

        HelloThread ht1 = new HelloThread();
        ht1.start();

        HelloThread ht2 = new HelloThread();
        ht2.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch m = new CountDownLatch();
        m.run();
    }
}
