package com.amazon.soter.examples.twophasecommit.messages.participants.commitphase;

/** Vote to commit. */
public class CommitTransaction extends CommitPhaseTransaction {
    public CommitTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
