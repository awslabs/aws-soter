package com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase;

/** Vote to commit. */
public class CommittedTransaction extends CleanupPhaseTransaction {
    public CommittedTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
