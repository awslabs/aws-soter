// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.twophasecommit.messages.clients;

import com.amazon.soter.examples.twophasecommit.messages.Message;
import com.amazon.soter.examples.twophasecommit.transactions.TransactionCoordinatorDecision;

/** Response to the client after a PUT request. */
public class PutResponse implements Message {
    private String id;
    private TransactionCoordinatorDecision decision;

    public PutResponse(String id, TransactionCoordinatorDecision decision) {
        this.id = id;
        this.decision = decision;
    }

    public String getId() {
        return id;
    }

    public TransactionCoordinatorDecision getDecision() {
        return decision;
    }
}
