// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.updates;

import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.Update;

/**
 * Update that is used to indicate when a resource is no longer needed by a task.
 */
public class ResourceNoLongerNeededUpdate implements Update {
    private Resource resource;
    public ResourceNoLongerNeededUpdate(Resource r) {
        this.resource = r;
    }
}