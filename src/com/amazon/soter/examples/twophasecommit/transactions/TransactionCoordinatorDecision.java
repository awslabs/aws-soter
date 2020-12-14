package com.amazon.soter.examples.twophasecommit.transactions;

/** Types of decisions that the coordinator is allowed to make. */
public enum TransactionCoordinatorDecision {

    /** Transaction is still undecided and is in progress. */
    UNDECIDED,

    /** Coordinator has decided to commit the transaction. */
    COMMIT,

    /** Coordinator has decided to abort the transaction. */
    ABORT
}
