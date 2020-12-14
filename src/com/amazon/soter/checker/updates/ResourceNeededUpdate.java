// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.soter.checker.updates;

import com.amazon.soter.checker.Resource;
import com.amazon.soter.checker.Update;

/**
 * Update that is used to indicate that a resource is needed by a task.
 */
public class ResourceNeededUpdate implements Update {
    private Resource resource;
    public ResourceNeededUpdate(Resource r) {
        this.resource = r;
    }
    public Resource getResource() {
        return this.resource;
    }
}
