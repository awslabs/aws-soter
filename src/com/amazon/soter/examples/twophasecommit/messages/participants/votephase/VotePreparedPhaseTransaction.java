package com.amazon.soter.examples.twophasecommit.messages.participants.votephase;

/** Vote to prepare. */
public class VotePreparedPhaseTransaction extends VotePhaseTransaction {
    public VotePreparedPhaseTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
