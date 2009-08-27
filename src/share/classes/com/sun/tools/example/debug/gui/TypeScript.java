/*
 * Copyright 1998-1999 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.example.debug.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class TypeScript extends JPanel {

    private JTextArea history;
    private JTextField entry;

    private JLabel promptLabel;

    private JScrollBar historyVScrollBar;
    private JScrollBar historyHScrollBar;

    private boolean echoInput = false;
    private boolean nlPending = false;

    private static String newline = System.getProperty("line.separator");

    public TypeScript(String prompt) {
        this(prompt, true);
    }

    public TypeScript(String prompt, boolean echoInput) {
        this.echoInput = echoInput;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setBorder(new EmptyBorder(5, 5, 5, 5));

        history = new JTextArea(0, 0);
        history.setEditable(false);
        JScrollPane scroller = new JScrollPane(history);
        historyVScrollBar = scroller.getVerticalScrollBar();
        historyHScrollBar = scroller.getHorizontalScrollBar();

        add(scroller);

        JPanel cmdLine = new JPanel();
        cmdLine.setLayout(new BoxLayout(cmdLine, BoxLayout.X_AXIS));
        //cmdLine.setBorder(new EmptyBorder(5, 5, 0, 0));

        promptLabel = new JLabel(prompt + " ");
        cmdLine.add(promptLabel);
        entry = new JTextField();
//### Swing bug workaround.
entry.setMaximumSize(new Dimension(1000, 20));
        cmdLine.add(entry);
        add(cmdLine);
    }

    /******
    public void setFont(Font f) {
        entry.setFont(f);
        history.setFont(f);
    }
    ******/

    public void setPrompt(String prompt) {
        promptLabel.setText(prompt + " ");
    }

    public void append(String text) {
        history.append(text);
        historyVScrollBar.setValue(historyVScrollBar.getMaximum());
        historyHScrollBar.setValue(historyHScrollBar.getMinimum());
    }

    public void newline() {
        history.append(newline);
        historyVScrollBar.setValue(historyVScrollBar.getMaximum());
        historyHScrollBar.setValue(historyHScrollBar.getMinimum());
    }

    public void flush() {}

    public void addActionListener(ActionListener a) {
        entry.addActionListener(a);
    }

    public void removeActionListener(ActionListener a) {
        entry.removeActionListener(a);
    }

    public String readln() {
        String text = entry.getText();
        entry.setText("");
        if (echoInput) {
            history.append(">>>");
            history.append(text);
            history.append(newline);
            historyVScrollBar.setValue(historyVScrollBar.getMaximum());
            historyHScrollBar.setValue(historyHScrollBar.getMinimum());
        }
        return text;
    }
}
