// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.twophasecommit.messages.clients;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Response to the client from a GET request. */
public class GetResponse implements Message {
    private String value;
    private String participantId;

    public GetResponse(String value, String participantId) {
        this.value = value;
        this.participantId = participantId;
    }

    public String getValue() {
        return value;
    }

    public String getParticipantId() {
        return participantId;
    }
}
