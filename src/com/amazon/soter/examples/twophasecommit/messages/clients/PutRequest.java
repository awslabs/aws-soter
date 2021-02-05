// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.examples.twophasecommit.messages.clients;

import com.amazon.soter.examples.twophasecommit.messages.Message;

/** Client message to perform a PUT transaction. */
public class PutRequest implements Message {
    private String key;
    private String value;
    private String id;
    private int clientId;

    public PutRequest(String id, String key, String value, int clientId) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.clientId = clientId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }
}
