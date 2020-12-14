package com.amazon.soter.examples.executor;

/** Example application for use with thread pool executors. */
public class ExampleWorker implements Runnable {
    private String ours;
    private Register sharedRegister;

    public ExampleWorker(String value, Register sharedRegister) {
        this.ours = value;
        this.sharedRegister = sharedRegister;
    }

    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.sharedRegister.setValue(this.ours);
    }
}
