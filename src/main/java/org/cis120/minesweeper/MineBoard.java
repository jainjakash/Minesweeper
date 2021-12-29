package org.cis120.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class MineBoard extends JPanel {

    public static final int BOARD_LENGTH = 600;
    public static final int BOX_LENGTH = 50;
    private Minesweeper ms;
    private JLabel status;

    public MineBoard(JLabel statusInit) {
        ms = new Minesweeper(BOARD_LENGTH / BOX_LENGTH);
        ms.setBoard(10); // set 10 mines
        status = statusInit;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean rightClick = false;
                if (e.getButton() == MouseEvent.BUTTON1 && e.isControlDown()) {
                    rightClick = true;
                }
                Point p = e.getPoint();
                ms.playTurn(p.y / BOX_LENGTH, p.x / BOX_LENGTH, rightClick);// determine position of
                                                                            // click
                // updates the model given the coordinates of the mouseclick
                updateStatus(); // update the status(win, lose or playing)
                repaint(); // repaints the game board
            }
        });
    }

    public void reset() {
        ms.reset();
        status.setText("You are playing!");
        repaint();
        requestFocusInWindow();
    }

    private void updateStatus() {
        int win = ms.win();
        if (win == 2) {
            status.setText("You've won!");
        } else if (win == 1) {
            status.setText("You've lost!");
        } else if (win == 0) {
            status.setText("You're playing!");
        }
    }

    public void saveBoard() {
        int[][] stateArray = ms.getStateArray();
        int[][] visibilityArray = ms.getVisibilityArray();

        try {
            Writer w = new FileWriter("src/main/java/org/cis120/minesweeper/gameState.txt", false);
            for (int i = 0; i < stateArray.length; i++) {
                for (int j = 0; j < stateArray.length; j++) {
                    w.write("" + stateArray[i][j]);
                    w.write("\n");
                }
            }
            w.close();

            Writer w1 = new FileWriter(
                    "src/main/java/org/cis120/minesweeper/gameVisibility.txt", false
            );
            for (int i = 0; i < visibilityArray.length; i++) {
                for (int j = 0; j < visibilityArray.length; j++) {
                    w1.write("" + visibilityArray[i][j]);
                    w1.write("\n");
                }
            }
            w1.close();
        } catch (FileNotFoundException e) {
            status.setText("File Not Found");
            JOptionPane.showMessageDialog(
                    this, "The file to read and write from cannot be found", "File Not Found",
                    JOptionPane.OK_OPTION
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadBoard() {
        int[][] stateArray = new int[BOARD_LENGTH / BOX_LENGTH][BOARD_LENGTH / BOX_LENGTH];
        int[][] visibilityArray = new int[BOARD_LENGTH / BOX_LENGTH][BOARD_LENGTH / BOX_LENGTH];

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/java/org/cis120/minesweeper/gameState.txt")
            );
            for (int i = 0; i < stateArray.length; i++) {
                for (int j = 0; j < stateArray.length; j++) {
                    stateArray[i][j] = Integer.parseInt(reader.readLine());
                }
            }

            BufferedReader reader1 = new BufferedReader(
                    new FileReader("src/main/java/org/cis120/minesweeper/gameVisibility.txt")
            );
            for (int i = 0; i < visibilityArray.length; i++) {
                for (int j = 0; j < visibilityArray.length; j++) {
                    visibilityArray[i][j] = Integer.parseInt(reader1.readLine());
                }
            }

        } catch (FileNotFoundException e) {
            status.setText("File Not Found");
            JOptionPane.showMessageDialog(
                    this, "The file to read and write from cannot be found", "File Not Found",
                    JOptionPane.OK_OPTION
            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ms.setStateArray(stateArray);
            ms.setVisibilityArray(visibilityArray);
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int vertLine = BOX_LENGTH;
        int horLine = 0;
        while (vertLine <= BOARD_LENGTH) {
            g.drawLine(vertLine, 0, vertLine, BOARD_LENGTH);
            vertLine += BOX_LENGTH;
        }

        while (horLine <= BOARD_LENGTH) {
            g.drawLine(0, horLine, BOARD_LENGTH, horLine);
            horLine += BOX_LENGTH;
        }

        for (int i = 0; i < BOARD_LENGTH / BOX_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH / BOX_LENGTH; j++) {
                if (ms.getCellVisibility(i, j) == 1) {
                    int state = ms.getCell(i, j);
                    if (state == 100) {
                        g.drawString("Mine", BOX_LENGTH * j, BOX_LENGTH * i + BOX_LENGTH);
                    } else if (state != 10 && state != 0) {
                        g.drawString("" + state, BOX_LENGTH * j, BOX_LENGTH * i + BOX_LENGTH);
                    } else {
                        g.drawString("X", BOX_LENGTH * j, BOX_LENGTH * i + BOX_LENGTH);
                    }
                } else if (ms.getCellVisibility(i, j) == 2) {
                    g.drawString("flag", BOX_LENGTH * j, BOX_LENGTH * i + BOX_LENGTH);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_LENGTH, BOARD_LENGTH);
    }

}
