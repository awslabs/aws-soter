// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.twophasecommit.messages.participants.cleanupphase;

/** Vote to abort. */
public class AbortedTransaction extends CleanupPhaseTransaction {
    public AbortedTransaction(String id, int clientId) {
        super(id, clientId);
    }
}
