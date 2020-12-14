package com.amazon.soter.examples.executor;

/** Shared register used in the executor example. */
public class Register {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
