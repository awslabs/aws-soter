package com.amazon.soter.examples.twophasecommit.messages.participants.votephase;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Abstract class used to represent a vote for a particular phase. */
public class VotePhaseTransaction implements Message {
    private String id;
    private int clientId;

    public VotePhaseTransaction(String id, int clientId) {
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
