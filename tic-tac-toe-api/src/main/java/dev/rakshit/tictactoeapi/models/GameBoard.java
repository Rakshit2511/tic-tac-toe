package dev.rakshit.tictactoeapi.models;

import java.util.Objects;

public class GameBoard {

    private final int size;
    private final String[][] matrix;

    /**
     * Creates a game board for given size.
     *
     * @param size size of the game board.
     */
    public GameBoard(int size) {
        this.size = size;
        this.matrix = new String[size][size];
    }

    /**
     * Gets matrix of the game board.
     *
     * @return matrix of the game board.
     */
    public String[][] getMatrix() {
        return matrix.clone();
    }

    /**
     * Gets size of the game board.
     *
     * @return size of the game board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets value for xy-coordinates in matrix.
     *
     * @param value Value to set in matrix.
     * @param x     x-coordinate to set value for.
     * @param y     y-coordinate to set value for.
     */
    public void set(String value, int x, int y) {
        matrix[x][y] = value;
    }

    /**
     * Tells if the value for given xy-coordinates is null or not.
     *
     * @param x x-coordinate to set value for.
     * @param y y-coordinate to set value for.
     * @return True if the value for given xy-coordinates is null else false.
     */
    public boolean isValueNull(int x, int y) {
        return null == matrix[x][y];
    }

    /**
     * Tells if board is completely filled or not.
     *
     * @return true if no block on board is empty and false if any of the block is empty.
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValueNull(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the player won the game or not.
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if the player won the game and false if not.
     */
    public boolean isWin(int x, int y) {
        return checkVertical(x, y) || checkHorizontal(x, y) || checkDiagonals(x, y);
    }

    /**
     * Checks if the vertical column is completely filled by the current player
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if the vertical column is completely filled by the current player else false.
     */
    private boolean checkVertical(int x, int y) {
        String val = matrix[x][y];
        if(null == val) return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(val, matrix[i][y])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the horizontal row is completely filled by the current player
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if the horizontal row is completely filled by the current player else false.
     */
    private boolean checkHorizontal(int x, int y) {
        String val = matrix[x][y];
        if(null == val) return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(val, matrix[x][i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any of the diagonal is completely filled by the current player
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if any of the diagonal is completely filled by the current player else false.
     */
    private boolean checkDiagonals(int x, int y) {
        return checkFirstDiagonal(x, y) || checkSecondDiagonal(x, y);
    }

    /**
     * Checks if 1st diagonal is completely filled by the current player
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if 1st diagonal is completely filled by the current player else false.
     */
    private boolean checkFirstDiagonal(int x, int y) {
        String val = matrix[x][y];
        if(null == val) return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(val, matrix[i][i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if 2nd diagonal is completely filled by the current player
     *
     * @param x x-coordinate of the move.
     * @param y y-coordinate of the move.
     * @return true if 2nd diagonal is completely filled by the current player else false.
     */
    private boolean checkSecondDiagonal(int x, int y) {
        String val = matrix[x][y];
        if(null == val) return false;
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(val, matrix[i][size - i - 1])) {
                return false;
            }
        }
        return true;
    }

}
