// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.service;

import com.amazon.soter.thrift.SoterService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class SoterServerThrift {

    public static SoterThriftHandler handler;

    public static SoterService.Processor processor;

    public static void run() {
        try {
            handler = new SoterThriftHandler();
            processor = new SoterService.Processor(handler);

            Runnable simpleServer = new Runnable() {
                public void run() {
                    simpleServer(processor);
                }
            };

            new Thread(simpleServer).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void simpleServer(SoterService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}