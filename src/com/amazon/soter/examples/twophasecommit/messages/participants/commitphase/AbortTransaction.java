package com.amazon.soter.examples.twophasecommit.messages.participants.commitphase;

/** Vote to abort. */
public class AbortTransaction extends CommitPhaseTransaction {
    public AbortTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
