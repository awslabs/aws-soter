// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.targets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazon.soter.checker.exceptions.ExternalProcessFailureException;

/** External testing target for non-Java Soter tests. */
public class ExternalTestingTarget implements TestingTarget {
    private String command;

    public ExternalTestingTarget(String command) {
        this.command = command;
    }

    public TargetExecutionType getExecutionType() {
        return TargetExecutionType.External;
    }

    public void run(Object o) throws Throwable {
        try {
            Runtime run  = Runtime.getRuntime();
            Process proc = run.exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.out.println("Waiting for command to finish...");
            proc.waitFor();
            int exitValue = proc.exitValue();
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
