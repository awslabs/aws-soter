// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.trace;

import com.amazon.soter.checker.config.Config;
import com.amazon.soter.checker.Checker;
import com.amazon.soter.checker.tasks.Task;
import com.amazon.soter.checker.trace.exceptions.InvalidTraceException;
import com.amazon.soter.checker.trace.steps.BooleanChoice;
import com.amazon.soter.checker.trace.steps.IntegerChoice;
import com.amazon.soter.checker.trace.steps.SchedulingChoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Represents a schedule.
 */
public class ScheduleTrace {
    private static Logger classLogger = Logger.getLogger(ScheduleTrace.class.getName());

    public static ScheduleTrace loadFromFile(String previousTraceFile) {
        ScheduleTrace trace = new ScheduleTrace();

        classLogger.info("Loading previous schedule trace from file " + previousTraceFile);

        try {
            File traceFile = new File(previousTraceFile);
            Scanner reader = new Scanner(traceFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] parsed = data.split(",");
                switch (parsed[0]) {
                    case "task":
                        SchedulingChoice tc = new SchedulingChoice(Integer.parseInt(parsed[1]));
                        trace.decisions.add(tc);
                        break;
                    case "boolean":
                        BooleanChoice rbc = new BooleanChoice(Boolean.parseBoolean(parsed[1]));
                        trace.decisions.add(rbc);
                        break;
                    case "int":
                        IntegerChoice ric = new IntegerChoice(Integer.parseInt(parsed[1]));
                        trace.decisions.add(ric);
                        break;
                    default:
                        throw new InvalidTraceException();
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            classLogger.severe("An error occurred loading the previous trace file.");
            e.printStackTrace();
        }

        return trace;
    }

    private ArrayList<ScheduleStep> decisions = new ArrayList<>();

    private final Logger logger = Logger.getLogger(Checker.class.getName());

    public void addSchedulingDecision(Task t) {
        decisions.add(new SchedulingChoice(t));
    }

    public void addSchedulingDecision(int x) {
        decisions.add(new IntegerChoice(x));
    }

    public void addSchedulingDecision(boolean x) {
        decisions.add(new BooleanChoice(x));
    }

    public void print() {
        logger.info("Schedule just executed: ");
        int i = 0;

        for (ScheduleStep decision : decisions) {
            logger.info(Integer.toString(i) + " " + decision);
            i++;
        }
    }

    public int size() {
        return decisions.size();
    }

    public ScheduleStep get(int index) {
        return decisions.get(index);
    }

    public void dumpToFile() {
        try {
            FileWriter writer = new FileWriter(Config.TRACE_FILE);
            for (ScheduleStep decision : decisions) {
                writer.write(decision.toSerializedTraceFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
