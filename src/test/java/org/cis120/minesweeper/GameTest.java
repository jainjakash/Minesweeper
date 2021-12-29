package org.cis120.minesweeper;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    private int stateArrayLength = 4;

    @Test
    public void setBoardTestNonzeroMines() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        testMS.setBoard(10);
        int mines = 0;

        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                if (testMS.getCell(i, j) == 100) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 10);
    }

    @Test
    public void setBoardTestZeroMines() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        testMS.setBoard(0);
        int mines = 0;

        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                if (testMS.getCell(i, j) == 100) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 0);
    }

    @Test
    public void checkMineCountTest() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        testMS.setStateArray(tempStateArray);

        checkMineCountHelper(testMS);

        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                assertEquals(0, tempStateArray[i][j]);
            }
        }

        tempStateArray[1][1] = 100;
        tempStateArray[1][2] = 100;

        checkMineCountHelper(testMS);

        assertEquals(1, tempStateArray[0][0]);
        assertEquals(1, tempStateArray[1][0]);
        assertEquals(1, tempStateArray[2][0]);
        assertEquals(1, tempStateArray[0][3]);
        assertEquals(1, tempStateArray[1][3]);
        assertEquals(1, tempStateArray[2][3]);

        assertEquals(2, tempStateArray[0][1]);
        assertEquals(2, tempStateArray[0][2]);
        assertEquals(2, tempStateArray[2][1]);
        assertEquals(2, tempStateArray[2][2]);
    }

    @Test
    public void checkMineCountTestAllDirections() {
        stateArrayLength = 3;
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 100, 100, 100, },
            { 100, 0, 100 },
            { 100, 100, 100 } };
        testMS.setStateArray(tempStateArray);

        checkMineCountHelper(testMS);

        assertEquals(8, testMS.getCell(1, 1));
    }

    @Test
    public void checkFlagAndUnflagVisibility() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);
        checkMineCountHelper(testMS);

        testMS.playTurn(1, 1, true);
        assertEquals(2, testMS.getCellVisibility(1, 1));
        testMS.playTurn(1, 1, true);
        assertEquals(0, testMS.getCellVisibility(1, 1));
    }

    @Test
    public void checkEmptyExpand() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        testMS.setStateArray(tempStateArray);
        checkMineCountHelper(testMS);
        testMS.playTurn(0, 0, false);

        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                assertEquals(10, tempStateArray[i][j]);
            }
        }
    }

    @Test
    public void checkPlayTurnAndExpand() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);
        checkMineCountHelper(testMS);

        assertEquals(0, testMS.getCellVisibility(0, 0));
        testMS.playTurn(0, 0, false);
        assertEquals(1, tempStateArray[0][0]);
        assertEquals(1, testMS.getCellVisibility(0, 0));

        assertEquals(0, testMS.getCellVisibility(3, 0));
        testMS.playTurn(3, 0, false);
        assertEquals(10, tempStateArray[3][0]);
        assertEquals(10, tempStateArray[3][1]);
        assertEquals(1, tempStateArray[2][0]);
        assertEquals(1, tempStateArray[2][1]);
        assertEquals(2, tempStateArray[2][2]);
        assertEquals(1, tempStateArray[3][2]);
        assertEquals(1, testMS.getVisibilityArray()[3][0]);// testing second method of retrieving
                                                           // visibility
        assertEquals(1, testMS.getVisibilityArray()[3][1]);
        assertEquals(1, testMS.getVisibilityArray()[2][0]);
        assertEquals(1, testMS.getVisibilityArray()[2][1]);
        assertEquals(1, testMS.getVisibilityArray()[2][2]);
        assertEquals(1, testMS.getVisibilityArray()[3][2]);
    }

    @Test
    public void checkPlayTurnOutOfBounds() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);

        assertFalse(testMS.playTurn(-98, -100, false));
        assertFalse(testMS.playTurn(5, 2, false));
        assertTrue(testMS.playTurn(2, 2, false));
    }

    @Test
    public void checkWin() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);

        testMS.playTurn(1, 1, true);
        testMS.playTurn(3, 3, true);
        assertEquals(2, testMS.win());

    }

    @Test
    public void checkLose() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);

        testMS.playTurn(1, 1, false);
        assertEquals(1, testMS.win());
    }

    @Test
    public void checkPlaying() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);

        testMS.playTurn(1, 1, true);
        testMS.playTurn(1, 2, false);
        testMS.playTurn(3, 2, false);
        assertEquals(0, testMS.win());
    }

    @Test
    public void checkReset() {
        Minesweeper testMS = new Minesweeper(stateArrayLength);
        int[][] tempStateArray = { { 0, 0, 0, 0 },
            { 0, 100, 0, 0 },
            { 100, 0, 0, 100 },
            { 0, 0, 0, 100 } };
        testMS.setStateArray(tempStateArray);

        testMS.reset();
        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                assertEquals(0, testMS.getCellVisibility(i, j));
            }
        }

        int mines = 0;

        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                if (testMS.getCell(i, j) == 100) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 10);
    }

    public void checkMineCountHelper(Minesweeper ms) {
        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                ms.checkMineCount(i, j);
            }
        }
    }

    // assertThrows(NoSuchElementException.class, () -> {
    // li.next();
    // });

}
