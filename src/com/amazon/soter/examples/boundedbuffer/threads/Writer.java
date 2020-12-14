package com.amazon.soter.examples.boundedbuffer.threads;

import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

/** Writer thread for the bounded buffer. */
public class Writer implements Runnable {
    private BoundedBuffer buffer;
    private int iterations;

    public Writer(BoundedBuffer buffer, int iterations) {
        this.iterations = iterations;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                buffer.put("hello " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
