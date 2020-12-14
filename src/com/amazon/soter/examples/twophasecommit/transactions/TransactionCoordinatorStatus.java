package com.amazon.soter.examples.twophasecommit.transactions;

/** Status of a transaction at the coordinator. */
public class TransactionCoordinatorStatus {
    private TransactionPhase phase;
    private TransactionCoordinatorDecision decision = TransactionCoordinatorDecision.UNDECIDED;
    private int numVotesPrepared;
    private int numVotesAborted;
    private int numVotesCommittedOrAborted;
    private int numVotesRequired;
    private String key;
    private String value;

    public TransactionCoordinatorStatus(TransactionPhase phase, int numVotesRequired, String key, String value) {
        this.phase = phase;
        this.numVotesRequired = numVotesRequired;
        this.key = key;
        this.value = value;
    }

    public TransactionCoordinatorDecision getDecision() {
        return decision;
    }

    public String getKey() {
        return key;
    }

    public int getNumVotesPrepared() {
        return numVotesPrepared;
    }

    public void incrementNumVotesPrepared() {
        numVotesPrepared++;
    }

    public int getNumVotesAborted() {
        return numVotesAborted;
    }

    public void incrementNumVotesAborted() {
        numVotesAborted++;
    }

    public int getNumVotesRequired() {
        return numVotesRequired;
    }

    public int getNumVotesCommittedOrAborted() {
        return numVotesCommittedOrAborted;
    }

    public void incrementNumVotesCommittedOrAborted() {
        numVotesCommittedOrAborted++;
    }

    public void setDecision(TransactionCoordinatorDecision decision) {
        this.decision = decision;
    }

    public void setPhase(TransactionPhase phase) {
        this.phase = phase;
    }
}

