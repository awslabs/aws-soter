package com.amazon.soter.examples.twophasecommit.messages.participants;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Request to prepare a transaction to commit. */
public class PrepareTransaction implements Message {
    private String id;
    private String key;
    private String value;
    private int clientId;

    public PrepareTransaction(String id, String key, String value, int clientId) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getClientId() {
        return clientId;
    }
}
