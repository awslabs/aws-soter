package com.amazon.soter.examples.twophasecommit;

import com.amazon.soter.checker.Checker;
import com.amazon.soter.examples.boundedbuffer.implementations.BoundedBufferWithLocksAndConditions;
import com.amazon.soter.examples.twophasecommit.messages.Message;
import com.amazon.soter.examples.twophasecommit.messages.participants.TerminateParticipant;
import com.amazon.soter.examples.twophasecommit.roles.Client;
import com.amazon.soter.examples.twophasecommit.roles.Participant;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;
import com.amazon.soter.examples.boundedbuffer.BoundedBuffer;
import com.amazon.soter.java.util.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Entry point for the Two-Phase Commit example. */
public class Main {
    private int numParticipants = 5;
    private int numClients = 1;

    private Random random = new Random();

    private HashMap<Integer, BoundedBuffer> participants = new HashMap<Integer, BoundedBuffer>();
    private HashMap<Integer, BoundedBuffer> clients = new HashMap<Integer, BoundedBuffer>();

    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public void awaitAll(ForkJoinPool customThreadPool) {
        customThreadPool.shutdown();

        while (true) {
            try {
                if (customThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(int participant, Message message) {
        logger.fine("Notifying participant " + participant + " to terminate.");

        BoundedBuffer bb = participants.get(participant);
        try {
            bb.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ForkJoinPool clientThreadPool = new ForkJoinPool(numClients);
        ForkJoinPool participantThreadPool = new ForkJoinPool(numParticipants);

        /* Participants. */

        for (int i = 0; i < numParticipants; i++) {
            /* Create mailbox for the process. */
            BoundedBuffer bb = new BoundedBufferWithLocksAndConditions(1);

            /* Keep track of participant identifier to mailbox mapping. */
            participants.put(i, bb);

            /* Start participant with id and mailbox. */
            participantThreadPool.execute(new Participant(participants, clients, i, bb));
        }

        /* Client. */

        for (int i = 0; i < numClients; i++) {
            /* Generate a unique write from the client. */
            String transactionId = "transaction-" + random.nextInt(100);
            String key = "key-" + Integer.toString(i);
            String value = "value-" + Integer.toString(i);

            /* Create mailbox for the process. */
            BoundedBuffer bb = new BoundedBufferWithLocksAndConditions(1);

            /* Keep track of participant identifier to mailbox mapping. */
            clients.put(i, bb);

            /* Start participant with id and mailbox. */
            clientThreadPool.execute(new Client(participants, clients, i, bb, transactionId, key, value));
        }

        /* Wait for termination of clients committing transactions. */
        awaitAll(clientThreadPool);

        /* Tell participants to finish. */
        for (Map.Entry<Integer, BoundedBuffer> participant : participants.entrySet()) {
            send(participant.getKey(), new TerminateParticipant());
        }

        /* Wait for termination of participants. */
        awaitAll(participantThreadPool);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}
