package com.amazon.soter.examples.twophasecommit.roles;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.examples.twophasecommit.exceptions.ClientException;
import com.amazon.soter.examples.twophasecommit.exceptions.ClientInvalidReadException;
import com.amazon.soter.examples.twophasecommit.messages.clients.GetRequest;
import com.amazon.soter.examples.twophasecommit.messages.clients.GetResponse;
import com.amazon.soter.examples.twophasecommit.messages.clients.PutRequest;
import com.amazon.soter.examples.twophasecommit.messages.clients.PutResponse;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionCoordinatorDecision;
import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;
import com.amazon.soter.examples.twophasecommit.messages.Message;
import com.amazon.soter.java.util.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/** A single client in Two-Phase Commit. */
public class Client implements Runnable {
    private int id;
    private BoundedBuffer buffer;
    private HashMap<Integer, BoundedBuffer> participants;
    private String transactionId;
    private String key;
    private String value;
    private Random random = new Random();

    private boolean outcome = false;
    private boolean putFinished = false;

    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public Client(HashMap<Integer, BoundedBuffer> participants,
                  HashMap<Integer, BoundedBuffer> clients,
                  int id,
                  BoundedBuffer bb,
                  String transactionId,
                  String key,
                  String value) {
        this.id = id;
        this.buffer = bb;
        this.participants = participants;
        this.transactionId = transactionId;
        this.key = key;
        this.value = value;
    }

    public void run() {
        /* Submit transaction to system. */
        logger.fine("Client " + id + " submitting transaction " + transactionId);

        /* Submit a put transaction to the system, which will randomly commit or abort. */
        try {
            participants.get(0).put(new PutRequest(transactionId, key, value, id));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* Wait for transaction to commit and record the outcome. */
        try {
            Message message = (Message) buffer.take();
            handleMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            run();
        }

        /* Submit a get transaction to the system, which will either return a correct value or not. */
        /* Do this on all nodes. */
        for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
            try {
                participants.get(participant.getKey()).put(new GetRequest(key, id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Wait for transaction to commit and record the outcome. */
            try {
                Message message = (Message) buffer.take();
                handleMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                run();
            }
        }
    }

    public void send(int participant, Message message) {
        logger.fine("Client " + id + " sending message to participant " + participant + " : " + message);

        BoundedBuffer bb = participants.get(participant);
        try {
            bb.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) throws ClientException {
        logger.fine("Client " + id + " received message: " + message);

        if (message instanceof PutResponse) {
            PutResponse pr = (PutResponse) message;
            if (pr.getDecision() == TransactionCoordinatorDecision.COMMIT) {
                outcome = true;
                putFinished = true;
            } else if (pr.getDecision() == TransactionCoordinatorDecision.ABORT) {
                outcome = false;
                putFinished = true;
            }
        } else if (message instanceof GetResponse) {
            GetResponse gr = (GetResponse) message;

            if (outcome) {
                if (gr.getValue() == value) {
                    logger.fine("Client was able to successfully read value!");
                } else {
                    logger.severe("Client tried reading successful PUT with value: "  + value
                            + " but instead read value: " + gr.getValue() + " from participant: "
                                + gr.getParticipantId());
                    throw new ClientInvalidReadException();
                }
            } else {
                if (gr.getValue() == value) {
                    logger.severe("Client read a value that was aborted, read value: " + gr.getValue()
                            + " when value we aborted write for was: " + value + " from participant: "
                                + gr.getParticipantId());
                    throw new ClientInvalidReadException();
                } else {
                    logger.fine("Client couldn't read value that is aborted transaction.");
                }
            }
        } else {
            throw new ClientException();
        }
    }
}
