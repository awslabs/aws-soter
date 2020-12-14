package com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Abstract class representing a transaction message during the cleanup phase. */
public class CleanupPhaseTransaction implements Message {
    private String id;
    private int clientId;

    public CleanupPhaseTransaction(String id, int clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }
}
