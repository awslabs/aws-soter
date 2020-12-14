package com.amazon.soter.examples.twophasecommit.messages.participants.commitphase;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Abstract class describing a message in the commit phase. */
public class CommitPhaseTransaction implements Message {
    private String id;
    private int clientId;

    public CommitPhaseTransaction(String id, int clientId) {
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
