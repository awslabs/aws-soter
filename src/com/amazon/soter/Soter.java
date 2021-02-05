// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter;

import com.amazon.soter.checker.TestingEngine;
import com.amazon.soter.checker.search.SearchType;
import com.amazon.soter.checker.strategies.StrategyType;
import com.amazon.soter.checker.targets.ExternalDebugTestingTarget;
import com.amazon.soter.checker.targets.ExternalTestingTarget;
import com.amazon.soter.checker.targets.TestingTarget;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;

/** Entry point for the Soter command line utility for running non-Java tests. */
final class Soter {
    private Soter() {

    }

    public static void main(String[] args) {
        Options options = new Options();

        Option debugOption = new Option("d", "debug", true, "run interactive debugger (e.g. /usr/bin/lldb)");
        debugOption.setRequired(false);
        options.addOption(debugOption);

        Option errorDepthOption = new Option("e", "errorDepth", true, "set the depth at which an error is thrown");
        errorDepthOption.setRequired(false);
        options.addOption(errorDepthOption);

        Option maxDepthOption = new Option("m", "maxDepth", true, "set the maximum exploration depth");
        maxDepthOption.setRequired(false);
        options.addOption(maxDepthOption);

        Option numIterationsOption = new Option("i", "numIterations", true, "specify the number of iterations to run");
        numIterationsOption.setRequired(false);
        options.addOption(numIterationsOption);

        Option helpOption = new Option("h", "help", false, "display this help");
        helpOption.setRequired(false);
        options.addOption(helpOption);

        Option replayOption = new Option("r", "replay", true, "replay failure using the following counterexample file");
        replayOption.setRequired(false);
        options.addOption(replayOption);

        Option searchOption = new Option("s", "search", true, "search type (e.g., dfs, stateless)");
        searchOption.setRequired(false);
        options.addOption(searchOption);

        Option strategyOption = new Option("t", "strategy", true, "strategy type (e.g., roundrobin, random)");
        strategyOption.setRequired(false);
        options.addOption(strategyOption);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            int errorDepth;

            if (cmd.hasOption("errorDepth")) {
                errorDepth = Integer.parseInt(cmd.getOptionValue("errorDepth"));
            } else {
                errorDepth = 10000;
            }

            int maxDepth;

            if (cmd.hasOption("maxDepth")) {
                maxDepth = Integer.parseInt(cmd.getOptionValue("maxDepth"));
            } else {
                maxDepth = errorDepth + 1;
            }

            int numIterations;

            if (cmd.hasOption("numIterations")) {
                numIterations = Integer.parseInt(cmd.getOptionValue("numIterations"));
            } else {
                numIterations = 100;
            }

            if (cmd.hasOption("help")) {
                formatter.printHelp("soter [OPTIONS] [PROGRAM]", options);
                System.exit(1);
            }

            SearchType searchType;

            if (cmd.hasOption("search")) {
                switch (cmd.getOptionValue("search")) {
                    case "dfs":
                        searchType = SearchType.DFS;
                        break;
                    case "stateless":
                        searchType = SearchType.StatelessSearch;
                        break;
                    default:
                        searchType = SearchType.StatelessSearch;
                        break;
                }
            } else {
                searchType = SearchType.StatelessSearch;
            }

            StrategyType strategyType;

            if (cmd.hasOption("strategy")) {
                switch (cmd.getOptionValue("strategy")) {
                    case "random":
                        strategyType = StrategyType.RandomStrategy;
                        break;
                    case "roundrobin":
                        strategyType = StrategyType.RoundRobinStrategy;
                        break;
                    default:
                        strategyType = StrategyType.RandomStrategy;
                        break;
                }
            } else {
                strategyType = StrategyType.RandomStrategy;
            }

            String program = args[args.length - 1];
            TestingTarget testingTarget = new ExternalTestingTarget(program);
            TestingEngine testingEngine;

            if (cmd.hasOption("replay")) {
                searchType = SearchType.ReplaySearch;

                if (cmd.hasOption("debug")) {
                    String debugger = cmd.getOptionValue("debug");
                    testingTarget = new ExternalDebugTestingTarget(program, debugger);
                }

                String counterexampleFile = cmd.getOptionValue("replay");
                numIterations = 1;
                testingEngine = new TestingEngine(searchType,
                        strategyType, maxDepth, errorDepth, numIterations, testingTarget, counterexampleFile);
            } else {
                testingEngine = new TestingEngine(searchType,
                        strategyType, maxDepth, errorDepth, numIterations, testingTarget);
            }

            try {
                long startTime = System.nanoTime();
                testingEngine.run();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;

                System.out.println("");
                System.out.println(searchType + " max Soter iterations: " + numIterations);
                System.out.println(searchType + " Soter duration: " + duration + "ms.");
                System.out.println(searchType + " mean duration per Soter iteration: "
                        + (duration / numIterations) + "ms.");
            } catch (Exception e) {
                System.out.println("Error detected: " + e);
                e.printStackTrace();
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            formatter.printHelp("soter [OPTIONS] [PROGRAM]", options);
            System.exit(1);
        }
    }
}
