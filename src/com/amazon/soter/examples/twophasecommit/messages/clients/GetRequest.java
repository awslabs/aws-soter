package com.amazon.soter.examples.twophasecommit.messages.clients;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** GET requests issued by the client in Two-Phase Commit. */
public class GetRequest implements Message {
    private String key;
    private int clientId;

    public GetRequest(String key, int clientId) {
        this.key = key;
        this.clientId = clientId;
    }

    public String getKey() {
        return key;
    }

    public int getClientId() {
        return clientId;
    }
}
