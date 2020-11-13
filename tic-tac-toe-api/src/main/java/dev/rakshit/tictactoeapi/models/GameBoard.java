package dev.rakshit.tictactoeapi.models;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.models.enums.BoardValue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
     * @param value       Value to set in matrix.
     * @param coordinates xy-coordinates to set value for.
     */
    public void set(String value, Coordinates coordinates) {
        matrix[coordinates.getX()][coordinates.getY()] = value;
    }

    /**
     * Tells if the value for given xy-coordinates is null or not.
     *
     * @param coordinates xy-coordinates to set value for.
     * @return True if the value for given xy-coordinates is null else false.
     */
    public boolean isValueNull(Coordinates coordinates) {
        return null == matrix[coordinates.getX()][coordinates.getY()];
    }

    /**
     * Tells if board is completely filled or not.
     *
     * @return true if no block on board is empty and false if any of the block is empty.
     */
    public boolean isFull() {
        return !getEmptyCoordinates().findFirst().isPresent();
    }

    /**
     * Gets coordinates player should chose to win the game.
     *
     * @param boardValue boardValue of the player.
     * @return Optional of coordinates player should chose to win the game and Optional.empty()
     * if no such coordinates exists.
     */
    public Optional<Coordinates> getRecommendedCoordinates(BoardValue boardValue) {
        String playerValue = boardValue.name();
        String opponentValue = (BoardValue.O == boardValue ? BoardValue.X : BoardValue.O).name();
        List<Coordinates> emptyCoordinates = getEmptyCoordinates()
                .collect(Collectors.toList());
        return getWinningCoordinate(playerValue, emptyCoordinates, size, 0)
                .map(Optional::of)
                .orElseGet(() -> getWinningCoordinate(opponentValue, emptyCoordinates, size, 0))
                .map(Optional::of)
                .orElseGet(() -> getMaxFillCoordinate(playerValue, emptyCoordinates, size, 1))
                .map(Optional::of)
                .orElseGet(() -> emptyCoordinates.stream().findFirst());
    }

    /**
     * @param value            value to be checked.
     * @param emptyCoordinates list of coordinates of the board that are empty.
     * @param vc               number of filled blocks to check for.
     * @param ec               number of empty blocks accepted.
     * @return Optional of coordinates of the board that can have maximum number of blocks
     * and least number of empty blocks consecutively and Optional.empty() if no such
     * coordinates exists.
     */
    private Optional<Coordinates> getMaxFillCoordinate(String value, List<Coordinates> emptyCoordinates, int vc, int ec) {
        return IntStream.range(ec, vc + 1)
                .mapToObj(i -> getWinningCoordinate(value, emptyCoordinates, vc - i, i))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    /**
     * Gets first coordinates of the board that can have vc number of blocks
     * and ec number of empty blocks consecutively.
     *
     * @param value            value to be checked.
     * @param emptyCoordinates list of coordinates of the board that are empty.
     * @param vc               number of filled blocks to check for.
     * @param ec               number of empty blocks accepted.
     * @return Optional of coordinates of the board that can have vc number of blocks
     * and ec number of empty blocks consecutively and Optional.empty() if no such
     * coordinates exists .
     */
    private Optional<Coordinates> getWinningCoordinate(String value, List<Coordinates> emptyCoordinates, int vc, int ec) {
        return getWinningCoordinates(value, emptyCoordinates, vc, ec).findFirst();
    }

    /**
     * Gets stream of coordinates of the board that can have vc number of blocks
     * and ec number of empty blocks consecutively.
     *
     * @param value            value to be checked.
     * @param emptyCoordinates list of coordinates of the board that are empty.
     * @param vc               number of filled blocks to check for.
     * @param ec               number of empty blocks accepted.
     * @return Stream of coordinates of the board that can have vc number of blocks
     * and ec number of empty blocks consecutively.
     */
    private Stream<Coordinates> getWinningCoordinates(String value, List<Coordinates> emptyCoordinates, int vc, int ec) {
        return emptyCoordinates.stream()
                .filter(coordinates -> isNumberOfFilledBlocksPossible(value, coordinates, vc, ec));
    }

    /**
     * Get all coordinates of the board that are empty.
     *
     * @return Stream of coordinates of the board that are empty.
     */
    private Stream<Coordinates> getEmptyCoordinates() {
        return IntStream.range(0, size)
                .boxed()
                .flatMap(i -> IntStream.range(0, size)
                        .mapToObj(j -> Coordinates.builder().x(i).y(j).build())
                        .filter(this::isValueNull));
    }

    /**
     * Checks if the player won the game or not.
     *
     * @param coordinates xy-coordinates of the move.
     * @return true if the player won the game and false if not.
     */
    public boolean isWin(Coordinates coordinates) {
        return !isValueNull(coordinates) && isNumberOfFilledBlocksPossible(matrix[coordinates.getX()][coordinates.getY()], coordinates, size, 0);
    }

    /**
     * Checks if the board can horizontally, vertically or diagonally contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if the board can horizontally, vertically or diagonally contain n number of filled blocks by the current player else false.
     */
    private boolean isNumberOfFilledBlocksPossible(String value, Coordinates coordinates, int vc, int ec) {
        return checkVertical(value, coordinates, vc, ec) || checkHorizontal(value, coordinates, vc, ec) || checkDiagonals(value, coordinates, vc, ec);
    }

    /**
     * Checks if the vertical column can contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if the vertical column can contain n number of filled blocks by the current player else false.
     */
    private boolean checkVertical(String value, Coordinates coordinates, int vc, int ec) {
        int[] consecutiveValueVsEmptyCountInVertical = getConsecutiveValueVsEmptyCountInVertical(value, coordinates, vc + ec);
        int valCount = consecutiveValueVsEmptyCountInVertical[0];
        int emptyCount = consecutiveValueVsEmptyCountInVertical[1];
        return valCount >= vc && emptyCount >= ec;
    }

    /**
     * Checks if the horizontal row can contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if the horizontal row can contain n number of filled blocks by the current player else false.
     */
    private boolean checkHorizontal(String value, Coordinates coordinates, int vc, int ec) {
        int[] consecutiveValueVsEmptyCountInHorizontal = getConsecutiveValueVsEmptyCountInHorizontal(value, coordinates, vc + ec);
        int valCount = consecutiveValueVsEmptyCountInHorizontal[0];
        int emptyCount = consecutiveValueVsEmptyCountInHorizontal[1];
        return valCount >= vc && emptyCount >= ec;
    }

    /**
     * Checks if any of the diagonals can contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if any of the diagonals can contain n number of filled blocks by the current player else false.
     */
    private boolean checkDiagonals(String value, Coordinates coordinates, int vc, int ec) {
        return checkFirstDiagonal(value, coordinates, vc, ec) || checkSecondDiagonal(value, coordinates, vc, ec);
    }

    /**
     * Checks if 1st diagonal can contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if 1st diagonal can contain n number of filled blocks by the current player else false.
     */
    private boolean checkFirstDiagonal(String value, Coordinates coordinates, int vc, int ec) {
        int[] consecutiveValueVsEmptyCountInFirstDiagonal = getConsecutiveValueVsEmptyCountInFirstDiagonal(value, coordinates, vc + ec);
        int valCount = consecutiveValueVsEmptyCountInFirstDiagonal[0];
        int emptyCount = consecutiveValueVsEmptyCountInFirstDiagonal[1];
        return valCount >= vc && emptyCount >= ec;
    }

    /**
     * Checks if 2nd diagonal can contain n number of filled blocks by the current player.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param vc          number of filled blocks to check for.
     * @param ec          number of empty blocks accepted.
     * @return true if 2nd diagonal can contain n number of filled blocks by the current player else false.
     */
    private boolean checkSecondDiagonal(String value, Coordinates coordinates, int vc, int ec) {
        int[] consecutiveValueVsEmptyCountInSecondDiagonal = getConsecutiveValueVsEmptyCountInSecondDiagonal(value, coordinates, vc + ec);
        int valCount = consecutiveValueVsEmptyCountInSecondDiagonal[0];
        int emptyCount = consecutiveValueVsEmptyCountInSecondDiagonal[1];
        return valCount >= vc && emptyCount >= ec;
    }

    /**
     * Gets consecutive value vs empty count possible in vertical column.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param n           max number of blocks to check for.
     * @return Gets array of consecutive value vs empty count possible in vertical column.
     */
    private int[] getConsecutiveValueVsEmptyCountInVertical(String value, Coordinates coordinates, int n) {
        int minRow = Math.max(coordinates.getX() - n + 1, 0);
        int maxRow = Math.min(coordinates.getX() + n - 1, size - 1);
        int count = 1;
        int emptyCount = 0;
        for (int i = coordinates.getX() - 1; i >= minRow; i--) {
            if (null == matrix[i][coordinates.getY()]) {
                emptyCount++;
            } else if (matrix[i][coordinates.getY()].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = coordinates.getX() + 1; i <= maxRow; i++) {
            if (null == matrix[i][coordinates.getY()]) {
                emptyCount++;
            } else if (matrix[i][coordinates.getY()].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        return new int[]{count, emptyCount};
    }

    /**
     * Gets consecutive value vs empty count possible in horizontal row.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param n           max number of blocks to check for.
     * @return Gets array of consecutive value vs empty count possible in horizontal row.
     */
    private int[] getConsecutiveValueVsEmptyCountInHorizontal(String value, Coordinates coordinates, int n) {
        int minCol = Math.max(coordinates.getY() - n + 1, 0);
        int maxCol = Math.min(coordinates.getY() + n - 1, size - 1);
        int count = 1;
        int emptyCount = 0;
        for (int i = coordinates.getY() - 1; i >= minCol; i--) {
            if (null == matrix[coordinates.getX()][i]) {
                emptyCount++;
            } else if (matrix[coordinates.getX()][i].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = coordinates.getY() + 1; i <= maxCol; i++) {
            if (null == matrix[coordinates.getX()][i]) {
                emptyCount++;
            } else if (matrix[coordinates.getX()][i].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        return new int[]{count, emptyCount};
    }

    /**
     * Gets consecutive value vs empty count possible in first diagonal.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param n           max number of blocks to check for.
     * @return Gets array of consecutive value vs empty count possible in first diagonal.
     */
    private int[] getConsecutiveValueVsEmptyCountInFirstDiagonal(String value, Coordinates coordinates, int n) {
        int minRow = Math.max(coordinates.getX() - n + 1, 0);
        int maxRow = Math.min(coordinates.getX() + n - 1, size - 1);
        int minCol = Math.max(coordinates.getY() - n + 1, 0);
        int maxCol = Math.min(coordinates.getY() + n - 1, size - 1);
        int count = 1;
        int emptyCount = 0;
        for (int i = coordinates.getX() - 1, j = coordinates.getY() - 1; i >= minRow && j >= minCol; i--, j--) {
            if (null == matrix[i][j]) {
                emptyCount++;
            } else if (matrix[i][j].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = coordinates.getX() + 1, j = coordinates.getY() + 1; i <= maxRow && j <= maxCol; i++, j++) {
            if (null == matrix[i][j]) {
                emptyCount++;
            } else if (matrix[i][j].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        return new int[]{count, emptyCount};
    }

    /**
     * Gets consecutive value vs empty count possible in second diagonal.
     *
     * @param value       value to be checked.
     * @param coordinates xy-coordinates of the move.
     * @param n           max number of blocks to check for.
     * @return Gets array of consecutive value vs empty count possible in second diagonal.
     */
    private int[] getConsecutiveValueVsEmptyCountInSecondDiagonal(String value, Coordinates coordinates, int n) {
        int minRow = Math.max(coordinates.getX() - n + 1, 0);
        int maxRow = Math.min(coordinates.getX() + n - 1, size - 1);
        int minCol = Math.max(coordinates.getY() - n + 1, 0);
        int maxCol = Math.min(coordinates.getY() + n - 1, size - 1);
        int count = 1;
        int emptyCount = 0;
        for (int i = coordinates.getX() - 1, j = coordinates.getY() + 1; i >= minRow && j <= maxCol; i--, j++) {
            if (null == matrix[i][j]) {
                emptyCount++;
            } else if (matrix[i][j].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        for (int i = coordinates.getX() + 1, j = coordinates.getY() - 1; i <= maxRow && j >= minCol; i++, j--) {
            if (null == matrix[i][j]) {
                emptyCount++;
            } else if (matrix[i][j].equals(value)) {
                count++;
            } else {
                break;
            }
        }
        return new int[]{count, emptyCount};
    }

}
