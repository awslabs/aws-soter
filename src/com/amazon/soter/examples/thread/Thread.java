// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.thread;

/** Test application for java.lang.Thread. */
public class Thread {
    private class HelloThread extends com.amazon.soter.java.lang.Thread {
        HelloThread() { }

        public void run() {
            try {
                com.amazon.soter.java.lang.Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        HelloThread ht = new HelloThread();
        ht.start();
        try {
            ht.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread m = new Thread();
        m.run();
    }
}
