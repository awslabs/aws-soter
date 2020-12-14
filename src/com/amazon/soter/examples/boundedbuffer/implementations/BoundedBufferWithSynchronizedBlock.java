package com.amazon.soter.examples.boundedbuffer.implementations;

import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

/** Implementation of the bounded buffer using the synchronized keyword. */
public class BoundedBufferWithSynchronizedBlock implements BoundedBuffer {
    private Object[] buffer;

    private int putAt;
    private int takeAt;
    private int occupied;

    public BoundedBufferWithSynchronizedBlock(int size) {
        this.buffer = new Object[size];
    }

    public synchronized void put(Object x) throws InterruptedException {
        while (occupied == buffer.length) {
            wait();
        }
        notify();
        ++occupied;
        putAt %= buffer.length;
        buffer[putAt++] = x;
    }

    public synchronized Object take() throws InterruptedException {
        while (occupied == 0) {
            wait();
        }
        notify();
        --occupied;
        takeAt %= buffer.length;
        return buffer[takeAt++];
    }
}
