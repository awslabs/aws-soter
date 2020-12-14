package com.amazon.soter.examples.twophasecommit.messages.participants.votephase;

/** Vote to abort. */
public class VoteAbortedPhaseTransaction extends VotePhaseTransaction {
    public VoteAbortedPhaseTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
