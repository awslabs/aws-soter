// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.search;

/** The type of search to perform. */
public enum SearchType {

    /** Perform a stateless search. */
    StatelessSearch,

    /** Perform a depth first search. */
    DFS,

    /** Replay a previous search. */
    ReplaySearch
}
