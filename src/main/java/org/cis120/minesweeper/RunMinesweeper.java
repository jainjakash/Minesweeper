package org.cis120.minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunMinesweeper implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        final MineBoard board = new MineBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String instructions = "The objective of the game is to " +
                        "successfully locate all of the mines. \n"
                        +
                        "Click on a square to reveal what's behind it. " +
                        "Right click on a square to flag it. \n"
                        +
                        "If you flag all of the mines, you win the game. " +
                        "If you click on any of the mines, you lose the game. \n"
                        +
                        "The number on any given box is indicative of the number " +
                        "of mines that directly surround it. ";

                JOptionPane.showMessageDialog(
                        frame, instructions, "Instructions for Minesweeper", JOptionPane.OK_OPTION
                );
            }
        });

        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.saveBoard();
            }
        });

        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.loadBoard();
            }
        });

        control_panel.add(reset);
        control_panel.add(instructions);
        control_panel.add(save);
        control_panel.add(load);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        board.reset();
    }
}
