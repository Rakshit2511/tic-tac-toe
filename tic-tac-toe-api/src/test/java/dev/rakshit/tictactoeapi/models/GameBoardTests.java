package dev.rakshit.tictactoeapi.models;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTests {

    private static final int SIZE = 3;

    @Test
    public void testIsFull() {
        GameBoard gameBoard = new GameBoard(SIZE);
        IntStream.range(0, SIZE).forEach(i -> IntStream.range(0, SIZE).forEach(j -> gameBoard.set("O", Coordinates.builder().x(i).y(j).build())));
        assertTrue(gameBoard.isFull());
    }

    @Test
    public void testIsNotFull() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isFull());
    }

    @Test
    public void testIsWinHorizontalTrue() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(0).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        assertTrue(gameBoard.isWin(Coordinates.builder().x(0).y(2).build()));
    }

    @Test
    public void testIsWinHorizontalFalse() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(Coordinates.builder().x(0).y(0).build()));
    }

    @Test
    public void testIsWinVerticalTrue() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(0).build());
        gameBoard.set("O",Coordinates.builder().x(2).y(0).build());
        assertTrue(gameBoard.isWin(Coordinates.builder().x(2).y(0).build()));
    }

    @Test
    public void testIsWinVerticalFalse() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(Coordinates.builder().x(0).y(0).build()));
    }

    @Test
    public void testIsWinDiagonal1True() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O",Coordinates.builder().x(0).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(2).y(2).build());
        assertTrue(gameBoard.isWin(Coordinates.builder().x(2).y(2).build()));
    }

    @Test
    public void testIsWinDiagonal1False() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(Coordinates.builder().x(0).y(0).build()));
    }

    @Test
    public void testIsWinDiagonal2True() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(2).y(0).build());
        assertTrue(gameBoard.isWin(Coordinates.builder().x(2).y(0).build()));
    }

    @Test
    public void testIsWinDiagonal2False() {
        GameBoard gameBoard = new GameBoard(SIZE);
        assertFalse(gameBoard.isWin(Coordinates.builder().x(0).y(0).build()));
    }

    @Test
    public void testGetRecommendedCoordinatesWin() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(1).build());
        assertThat(gameBoard.getRecommendedCoordinates(BoardValue.O).get())
                .isEqualTo(Coordinates.builder().x(2).y(0).build());
    }

    @Test
    public void testGetRecommendedCoordinatesPreventOpponentWin() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(1).build());
        assertThat(gameBoard.getRecommendedCoordinates(BoardValue.X).get())
                .isEqualTo(Coordinates.builder().x(2).y(0).build());
    }

    @Test
    public void testGetMaxFillCoordinate() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        assertThat(gameBoard.getRecommendedCoordinates(BoardValue.O).get())
                .isEqualTo(Coordinates.builder().x(0).y(0).build());
    }

    @Test
    public void testGetMaxFillCoordinateOpponent() {
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        assertThat(gameBoard.getRecommendedCoordinates(BoardValue.X).get())
                .isEqualTo(Coordinates.builder().x(0).y(0).build());
    }

}
