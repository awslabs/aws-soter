// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

namespace java com.amazon.soter.thrift

service SoterService {
  void createTask(1: string id)
  void exitTask(1: string id)
  void abnormalExitTask(1: string id)
  void yieldTask(1: string id, 2: string updateType, 3: string resourceId)
  void blockTask(1: string id)
  void startTask(1: string id)
  void cancelTask(1: string id)
  void updateState(1: string id, 2: string updateType, 3: string resourceId)
  bool nondeterministicBooleanChoice()
  i32 nondeterministicIntegerChoice()
  i32 nondeterministicIntegerWithBoundChoice(1: i32 bound)
}

service SoterClientService {
    bool isAvailableFor(1: string taskId, 2: string resourceId)
    bool hasAssociatedTask(1: string resourceId)
    string getAssociatedTask(1: string resourceId)
}