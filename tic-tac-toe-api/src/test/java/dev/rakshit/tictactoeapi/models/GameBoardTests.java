package dev.rakshit.tictactoeapi.models;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTests {

    private static final int SIZE = 3;

    @Test
    public void testIsFull() {
        GameBoard gameBoard = new GameBoard(SIZE);
        IntStream.range(0, SIZE).forEach(i -> IntStream.range(0, SIZE).forEach(j -> gameBoard.set("O", i, j)));
        assertTrue(gameBoard.isFull());
    }

    @Test
    public void testIsNotFull() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isFull());
    }

    @Test
    public void testIsWinVerticalTrue() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", 0, 0);
        gameBoard.set("O", 0, 1);
        gameBoard.set("O", 0, 2);
        assertTrue(gameBoard.isWin(0, 2));
    }

    @Test
    public void testIsWinVerticalFalse() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(0, 0));
    }

    @Test
    public void testIsWinHorizontalTrue() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", 0, 0);
        gameBoard.set("O", 1, 0);
        gameBoard.set("O", 2, 0);
        assertTrue(gameBoard.isWin(2, 0));
    }

    @Test
    public void testIsWinHorizontalFalse() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(0, 0));
    }

    @Test
    public void testIsWinDiagonal1True() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", 0, 0);
        gameBoard.set("O", 1, 1);
        gameBoard.set("O", 2, 2);
        assertTrue(gameBoard.isWin(2, 2));
    }

    @Test
    public void testIsWinDiagonal1False() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(0, 0));
    }

    @Test
    public void testIsWinDiagonal2True() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", 0, 2);
        gameBoard.set("O", 1, 1);
        gameBoard.set("O", 2, 0);
        assertTrue(gameBoard.isWin(2, 0));
    }

    @Test
    public void testIsWinDiagonal2False() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(0, 0));
    }

}
