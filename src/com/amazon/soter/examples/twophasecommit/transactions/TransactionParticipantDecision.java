package com.amazon.soter.examples.twophasecommit.transactions;

/** Types of decisions that can be made by the participant. */
public enum TransactionParticipantDecision {

    /** Transaction has prepared. */
    PREPARED,

    /** Participant cannot commit transaction and will vote to abort. */
    ABORTED
}
