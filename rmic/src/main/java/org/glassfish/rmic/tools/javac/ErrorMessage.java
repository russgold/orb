/*
 * Copyright (c) 1994, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.glassfish.rmic.tools.javac;

/**
 * A sorted list of error messages
 *
 * WARNING: The contents of this source file are not part of any supported API. Code that depends on them does so at its
 * own risk: they are subject to change or removal without notice.
 */
@Deprecated
final class ErrorMessage {
    long where;
    String message;
    ErrorMessage next;

    /**
     * Constructor
     */
    ErrorMessage(long where, String message) {
        this.where = where;
        this.message = message;
    }
}
