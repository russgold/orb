/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.corba.ee.impl.naming.cosnaming;

import java.io.*;
import org.omg.CosNaming.NameComponent;

public class NamingUtils {
    // Do not instantiate this class
    private NamingUtils() {
    };

    /**
     * Debug flag which must be true for debug streams to be created and dprint output to be generated.
     */
    public static boolean debug = false;

    /**
     * Prints the message to the debug stream if debugging is enabled.
     *
     * @param msg the debug message to print.
     */
    public static void dprint(String msg) {
        if (debug && debugStream != null)
            debugStream.println(msg);
    }

    /**
     * Prints the message to the error stream (System.err is default).
     *
     * @param msg the error message to print.
     */
    public static void errprint(String msg) {
        if (errStream != null)
            errStream.println(msg);
        else
            System.err.println(msg);
    }

    /**
     * Prints the stacktrace of the supplied exception to the error stream.
     *
     * @param e any Java exception.
     */
    public static void printException(java.lang.Exception e) {
        if (errStream != null)
            e.printStackTrace(errStream);
        else
            e.printStackTrace();
    }

    /**
     * Create a debug print stream to the supplied log file.
     *
     * @param logFile the file to which debug output will go.
     * @exception IOException thrown if the file cannot be opened for output.
     */
    public static void makeDebugStream(File logFile) throws java.io.IOException {
        // Create an outputstream for debugging
        java.io.OutputStream logOStream = new java.io.FileOutputStream(logFile);
        java.io.DataOutputStream logDStream = new java.io.DataOutputStream(logOStream);
        debugStream = new java.io.PrintStream(logDStream);

        // Emit first message
        debugStream.println("Debug Stream Enabled.");
    }

    /**
     * Create a error print stream to the supplied file.
     *
     * @param logFile the file to which error messages will go.
     * @exception IOException thrown if the file cannot be opened for output.
     */
    public static void makeErrStream(File errFile) throws java.io.IOException {
        if (debug) {
            // Create an outputstream for errors
            java.io.OutputStream errOStream = new java.io.FileOutputStream(errFile);
            java.io.DataOutputStream errDStream = new java.io.DataOutputStream(errOStream);
            errStream = new java.io.PrintStream(errDStream);
            dprint("Error stream setup completed.");
        }
    }

    /**
     * A utility method that takes Array of NameComponent and converts into a directory structured name in the format of
     * /id1.kind1/id2.kind2.. This is used mainly for Logging.
     */
    static String getDirectoryStructuredName(NameComponent[] name) {
        StringBuffer directoryStructuredName = new StringBuffer("/");
        for (int i = 0; i < name.length; i++) {
            directoryStructuredName.append(name[i].id + "." + name[i].kind);
        }
        return directoryStructuredName.toString();
    }

    /**
     * The debug printstream.
     */
    public static java.io.PrintStream debugStream;

    /**
     * The error printstream.
     */
    public static java.io.PrintStream errStream;
}
