package com.amazon.soter.examples.twophasecommit.transactions;

/** Status of a transaction at a participant. */
public class TransactionParticipantStatus {
    private TransactionParticipantDecision decision;
    private String key;
    private String value;

    public TransactionParticipantStatus(TransactionParticipantDecision decision, String key, String value) {
        this.decision = decision;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public TransactionParticipantDecision getDecision() {
        return decision;
    }
}

