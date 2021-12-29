package org.cis120.minesweeper;

public class Minesweeper {

    int[][] stateArray;
    int[][] visibilityArray;
    private int stateArrayLength;
    boolean gameOver = false;

    public Minesweeper(int stateArrayLength) {
        this.stateArrayLength = stateArrayLength;
        this.stateArray = new int[stateArrayLength][stateArrayLength];
        this.visibilityArray = new int[stateArrayLength][stateArrayLength];
    }

    public void setBoard(int mineCount) {
        while (mineCount > 0) {
            int tempRow = (int) (Math.random() * stateArray.length);
            int tempCol = (int) (Math.random() * stateArray.length);
            if (stateArray[tempRow][tempCol] == 0) {
                stateArray[tempRow][tempCol] = 100;
                mineCount--;
            }
        }

        for (int row = 0; row < stateArray.length; row++) {
            for (int col = 0; col < stateArray[0].length; col++) {
                checkMineCount(row, col);
            }
        }
    }

    public int[][] getStateArray() {
        return stateArray;
    }

    public int[][] getVisibilityArray() {
        return visibilityArray;
    }

    public void setStateArray(int[][] stateArray) {
        this.stateArray = stateArray;
    }

    public void setVisibilityArray(int[][] visibilityArray) {
        this.visibilityArray = visibilityArray;
    }

    public void emptyExpansion(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                try {
                    if (stateArray[i][j] != 100) {
                        visibilityArray[i][j] = 1;
                    }
                    if (stateArray[i][j] == 0) {
                        stateArray[i][j] = 10;
                        emptyExpansion(i, j);
                    }
                } catch (ArrayIndexOutOfBoundsException aoi) {
                }
            }
        }
    }

    public boolean playTurn(int row, int col, boolean rightClick) {
        if (gameOver || row >= stateArrayLength || col >= stateArrayLength || row < 0 || col < 0) {
            return false;
        }
        if (rightClick) {
            if (visibilityArray[row][col] == 2) {
                visibilityArray[row][col] = 0;
            } else {
                visibilityArray[row][col] = 2;
            }
        } else {
            if (stateArray[row][col] == 100) {
                visibilityArray[row][col] = 1;
            }
            if (stateArray[row][col] == 0) {
                emptyExpansion(row, col);
            }
            visibilityArray[row][col] = 1;// visible
        }

        return true;
    }

    public int win() {
        int win = 2; // default is win
        for (int i = 0; i < stateArrayLength; i++) {
            for (int j = 0; j < stateArrayLength; j++) {
                if (stateArray[i][j] == 100 && visibilityArray[i][j] == 1) {
                    win = 1; // lose
                    i = stateArrayLength + 1;
                    j = stateArrayLength + 1;
                    gameOver = true;
                } else if (stateArray[i][j] == 100 && visibilityArray[i][j] == 0) {
                    win = 0; // still playing
                }
            }
        }
        if (win == 2) {
            gameOver = true;
        }
        return win;
    }

    public int getCell(int r, int c) {
        return stateArray[r][c];
    }

    public int getCellVisibility(int r, int c) {
        return visibilityArray[r][c];
    }

    public void reset() {
        stateArray = new int[stateArrayLength][stateArrayLength];
        visibilityArray = new int[stateArrayLength][stateArrayLength];
        setBoard(10);
        gameOver = false;
    }

    public void checkMineCount(int row, int col) {
        if (stateArray[row][col] != 100) {
            int neighboringMines = 0;
            if (row > 0 && stateArray[row - 1][col] == 100) {
                neighboringMines++;
            }
            if (row < (stateArray.length - 1) && stateArray[row + 1][col] == 100) {
                neighboringMines++;
            }

            if (col > 0 && stateArray[row][col - 1] == 100) {
                neighboringMines++;
            }
            if (col < (stateArray[0].length - 1) && stateArray[row][col + 1] == 100) {
                neighboringMines++;
            }

            if (row > 0 && col > 0 && stateArray[row - 1][col - 1] == 100) {
                neighboringMines++;
            }
            if (row < (stateArray.length - 1) && col < (stateArray[0].length - 1)
                    && stateArray[row + 1][col + 1] == 100) {
                neighboringMines++;
            }

            if (row > 0 && col < (stateArray[0].length - 1)
                    && stateArray[row - 1][col + 1] == 100) {
                neighboringMines++;
            }
            if (row < (stateArray.length - 1) && col > 0
                    && stateArray[row + 1][col - 1] == 100) {
                neighboringMines++;
            }

            stateArray[row][col] = neighboringMines;
        }
    }

    public void main(String[] args) {
    }
}
