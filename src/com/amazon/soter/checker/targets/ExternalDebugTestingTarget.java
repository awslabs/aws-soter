// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

import com.amazon.soter.checker.exceptions.ExternalProcessFailureException;

import java.io.IOException;
import java.util.Map;

/** External testing target where we will launch an interactive debugger when executing the test. */
public class ExternalDebugTestingTarget implements TestingTarget {
    private String command;
    private String debugger;

    public ExternalDebugTestingTarget(String command, String debugger) {
        this.command = command;
        this.debugger = debugger;
    }

    public TargetExecutionType getExecutionType() {
        return TargetExecutionType.External;
    }

    public void run(Object o) throws Throwable {
        try {
            ProcessBuilder builder = new ProcessBuilder(debugger, command);
            Map<String, String> environ = builder.environment();
            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            final Process process = builder.start();
            process.waitFor();
            int exitValue = process.exitValue();
            System.out.println("We are here now; exitValue: " + exitValue);

            if (exitValue != 0) {
                throw new ExternalProcessFailureException();
            }
        } catch (IOException e) {
            System.out.println("Hit an IO exception!");
            e.printStackTrace();
            throw e;
        }
    }
}
