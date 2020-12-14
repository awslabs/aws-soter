package com.amazon.soter.examples.twophasecommit.roles;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.examples.twophasecommit.messages.Message;
import com.amazon.soter.examples.twophasecommit.messages.clients.GetRequest;
import com.amazon.soter.examples.twophasecommit.messages.clients.GetResponse;
import com.amazon.soter.examples.twophasecommit.messages.clients.PutRequest;
import com.amazon.soter.examples.twophasecommit.messages.clients.PutResponse;
import com.amazon.soter.examples.twophasecommit.messages.participants.PrepareTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.TerminateParticipant;
import com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase.AbortedTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase.CleanupPhaseTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase.CommittedTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.commitphase.AbortTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.commitphase.CommitPhaseTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.commitphase.CommitTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.votephase.VoteAbortedPhaseTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.votephase.VotePhaseTransaction;
import com.amazon.soter.examples.twophasecommit.messages.participants.votephase.VotePreparedPhaseTransaction;

import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;

import com.amazon.soter.examples.twophasecommit.transactions.TransactionCoordinatorStatus;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionCoordinatorDecision;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionParticipantStatus;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionParticipantDecision;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionPhase;

import com.amazon.soter.java.util.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/** A single Participant in Two-Phase Commit. */
public class Participant implements Runnable {
    private int id;
    private BoundedBuffer buffer;

    private HashMap<Integer, BoundedBuffer> participants;
    private HashMap<Integer, BoundedBuffer> clients;

    private HashMap<String, String> storage = new HashMap<>();

    private HashMap<String, TransactionCoordinatorStatus> coordinatingTransactions = new HashMap<>();
    private HashMap<String, TransactionParticipantStatus> preparingTransactions = new HashMap<>();

    private Random random = new Random();

    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public Participant(HashMap<Integer, BoundedBuffer> participants, HashMap<Integer, BoundedBuffer> clients,
                       int id, BoundedBuffer bb) {
        this.id = id;
        this.buffer = bb;
        this.participants = participants;
        this.clients = clients;
    }

    public void run() {
        try {
            Message message = (Message) buffer.take();
            handleMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendToParticipant(int participant, Message message) {
        logger.fine("Participant " + id + " sending message to participant " + participant + " : " + message);

        BoundedBuffer bb = participants.get(participant);
        try {
            bb.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(int client, Message message) {
        logger.fine("Participant " + id + " sending message to client " + client + " : " + message);

        BoundedBuffer bb = clients.get(client);
        try {
            bb.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) {
        logger.fine("Participant " + id + " received message: " + message);

        if (message instanceof PutRequest) {
            PutRequest pt = (PutRequest) message;

            /* Add transaction to the log, move to the preparing phase. */
            TransactionCoordinatorStatus transactionCoordinatorStatus = new TransactionCoordinatorStatus(
                    TransactionPhase.PREPARING, participants.size() - 1, pt.getKey(), pt.getValue());
            coordinatingTransactions.put(pt.getId(), transactionCoordinatorStatus);

            /* Write to storage. */
            storage.put(pt.getKey(), pt.getValue());

            /* Send prepare to all participants */
            for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
                if (participant.getKey() != id) {
                    sendToParticipant(participant.getKey(), new PrepareTransaction(pt.getId(), pt.getKey(),
                            pt.getValue(), pt.getClientId()));
                }
            }

        } else if (message instanceof GetRequest) {
            GetRequest gt = (GetRequest) message;

            /* Create response with the content of the local object. */
            GetResponse response = new GetResponse(storage.get(gt.getKey()), String.valueOf(id));

            /* Send response to client. */
            sendToClient(gt.getClientId(), response);
        } else if (message instanceof PrepareTransaction) {
            PrepareTransaction pt = (PrepareTransaction) message;

            /* Store the local decision before responding. */
            if (random.nextInt(2) == 0) {
                /* Write to storage. */
                storage.put(pt.getKey(), pt.getValue());

                /* Mark transaction as prepared locally. */
                preparingTransactions.put(pt.getId(), new TransactionParticipantStatus(
                        TransactionParticipantDecision.PREPARED, pt.getKey(), pt.getValue()));

                /* Notify coordinator that we have prepared. */
                sendToParticipant(0, new VotePreparedPhaseTransaction(pt.getId(), pt.getClientId()));
            } else {
                /* Mark transaction as aborted locally. */
                preparingTransactions.put(pt.getId(), new TransactionParticipantStatus(
                        TransactionParticipantDecision.ABORTED, pt.getKey(), pt.getValue()));

                /* Notify coordinator that we have aborted. */
                sendToParticipant(0, new VoteAbortedPhaseTransaction(pt.getId(), pt.getClientId()));
            }

        } else if (message instanceof VotePhaseTransaction) {
            VotePhaseTransaction vt = (VotePhaseTransaction) message;
            TransactionCoordinatorStatus transactionCoordinatorStatus = coordinatingTransactions.get(vt.getId());

            if (message instanceof VotePreparedPhaseTransaction) {
                /* Update transaction state. */
                transactionCoordinatorStatus.incrementNumVotesPrepared();
            } else if (message instanceof VoteAbortedPhaseTransaction) {
                /* Update transaction state. */
                transactionCoordinatorStatus.incrementNumVotesAborted();
            }

            if (transactionCoordinatorStatus.getNumVotesRequired()
                    == (transactionCoordinatorStatus.getNumVotesPrepared()
                        + transactionCoordinatorStatus.getNumVotesAborted())) {
                /* We've got all of the votes we need. */

                if (transactionCoordinatorStatus.getNumVotesPrepared()
                        == transactionCoordinatorStatus.getNumVotesRequired()) {
                    /* All prepares, move to commit; coordinator can unilaterally abort. */

                    if (random.nextInt(2) == 0) {
                        logger.fine("Participant " + id + " moving to the commit phase.");

                        /* Advance the decision to commit, move to the committing phase. */
                        transactionCoordinatorStatus.setPhase(TransactionPhase.COMMITTING);
                        transactionCoordinatorStatus.setDecision(TransactionCoordinatorDecision.COMMIT);
                        coordinatingTransactions.put(vt.getId(), transactionCoordinatorStatus);

                        /* Move to the commit phase. */
                        for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
                            if (participant.getKey() != id) {
                                sendToParticipant(participant.getKey(),
                                        new CommitTransaction(vt.getId(), vt.getClientId()));
                            }
                        }
                    } else {
                        logger.fine("Participant " + id + " moving to the abort phase.");

                        /* Advance the decision to commit, move to the committing phase. */
                        transactionCoordinatorStatus.setPhase(TransactionPhase.ABORTING);
                        transactionCoordinatorStatus.setDecision(TransactionCoordinatorDecision.ABORT);
                        coordinatingTransactions.put(vt.getId(), transactionCoordinatorStatus);

                        /* Remove value from storage. */
                        storage.remove(transactionCoordinatorStatus.getKey());

                        /* Move to the commit phase. */
                        for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
                            if (participant.getKey() != id) {
                                sendToParticipant(participant.getKey(),
                                        new AbortTransaction(vt.getId(), vt.getClientId()));
                            }
                        }
                    }
                } else {
                    /* At least one abort, abort. */
                    logger.fine("Participant " + id + " moving to the abort phase.");

                    /* Advance the decision to commit, move to the committing phase. */
                    transactionCoordinatorStatus.setPhase(TransactionPhase.ABORTING);
                    transactionCoordinatorStatus.setDecision(TransactionCoordinatorDecision.ABORT);
                    coordinatingTransactions.put(vt.getId(), transactionCoordinatorStatus);

                    /* Remove value from storage. */
                    storage.remove(transactionCoordinatorStatus.getKey());

                    /* Move to the commit phase. */
                    for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
                        if (participant.getKey() != id) {
                            sendToParticipant(participant.getKey(),
                                    new AbortTransaction(vt.getId(), vt.getClientId()));
                        }
                    }
                }
            } else {
                /* We're still waiting on votes. */
                logger.fine("Participant " + id + " still waiting for prepare responses.");

                /* Keep waiting. */
                coordinatingTransactions.put(vt.getId(), transactionCoordinatorStatus);
            }

        } else if (message instanceof CommitPhaseTransaction) {
            CommitPhaseTransaction ct = (CommitPhaseTransaction) message;
            TransactionParticipantStatus transactionParticipantStatus = preparingTransactions.get(ct.getId());

            /* Once the coordinator sends a commit message, participants can forget about the transaction. */
            preparingTransactions.remove(ct.getId());

            /* Send reply to the coordinator. */
            if (message instanceof AbortTransaction) {
                /* Remove value from storage. */
                storage.remove(transactionParticipantStatus.getKey());

                sendToParticipant(0, new AbortedTransaction(ct.getId(), ct.getClientId()));
            } else if (message instanceof CommitTransaction) {
                sendToParticipant(0, new CommittedTransaction(ct.getId(), ct.getClientId()));
            }
        } else if (message instanceof CleanupPhaseTransaction) {
            CleanupPhaseTransaction ct = (CleanupPhaseTransaction) message;
            TransactionCoordinatorStatus transactionCoordinatorStatus = coordinatingTransactions.get(ct.getId());
            transactionCoordinatorStatus.incrementNumVotesCommittedOrAborted();

            /* We'll either have all aborts or all commits, depending on coordinator's decision. */
            if (transactionCoordinatorStatus.getNumVotesRequired()
                    == transactionCoordinatorStatus.getNumVotesCommittedOrAborted()) {
                logger.fine("Participant " + id + " moving to the committed phase.");

                /* Notify client. */
                if (transactionCoordinatorStatus.getDecision() == TransactionCoordinatorDecision.ABORT) {
                    sendToClient(ct.getClientId(), new PutResponse(ct.getId(), TransactionCoordinatorDecision.ABORT));
                } else if (transactionCoordinatorStatus.getDecision() == TransactionCoordinatorDecision.COMMIT) {
                    sendToClient(ct.getClientId(), new PutResponse(ct.getId(), TransactionCoordinatorDecision.COMMIT));
                }

                /* Once all participants respond to either the commit or abort,
                   the coordinator can forget about the transaction. */
                coordinatingTransactions.remove(ct.getId());
            } else {
                logger.fine("Participant " + id + " still waiting for commit responses.");

                /* Keep waiting. */
                coordinatingTransactions.put(ct.getId(), transactionCoordinatorStatus);
            }
        } else if (message instanceof TerminateParticipant) {
            return;
        }

        run();
    }
}
