package com.amazon.soter.examples.twophasecommit.transactions;

/** Transaction state for the Two-Phase Commit example. */
public enum TransactionPhase {

    /** Transaction is preparing to commit. */
    PREPARING,

    /** Transaction is committing. */
    COMMITTING,

    /** Transaction is aborting. */
    ABORTING
}
