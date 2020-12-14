# Search Types

Soter supports several different search strategies.

* `SearchType.DFS`: Perform a depth first search exploring all possible nondeterministic choices: both scheduling and data.
* `SearchType.ReplaySearch`: Using a counterexample trace, replay an execution by making the same set of recorded decisions every iteration.
* `SearchType.StatelessSearch`: Randomly select scheduling and data nondeterminism choices every iteration.