package dev.rakshit.tictactoeapi.utils;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.exceptions.InvalidTurnException;
import dev.rakshit.tictactoeapi.models.Game;
import dev.rakshit.tictactoeapi.models.GameBoard;
import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import dev.rakshit.tictactoeapi.models.enums.GameStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameUtilTests {

    private static final int SIZE = 3;

    @Test
    public void testAssertTurnInvalidX() {
        GameBoard gameBoard = new GameBoard(SIZE);
        InvalidTurnException exception = assertThrows(InvalidTurnException.class, () -> {
            GameUtil.assertTurn(gameBoard, Coordinates.builder().x(3).y(3).build());
        });
        String expectedMessage = String.format("Invalid x-coordinate; Please enter a valid integer between 0 and %d", SIZE);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAssertTurnInvalidY() {
        GameBoard gameBoard = new GameBoard(SIZE);
        InvalidTurnException exception = assertThrows(InvalidTurnException.class, () -> {
            GameUtil.assertTurn(gameBoard, Coordinates.builder().x(0).y(3).build());
        });
        String expectedMessage = String.format("Invalid y-coordinate; Please enter a valid integer between 0 and %d", SIZE);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAssertTurnSlotNotAvailable() {
        GameBoard gameBoard = new GameBoard(SIZE);
        Coordinates coordinates = Coordinates.builder().x(0).y(0).build();
        gameBoard.set("O", coordinates);
        InvalidTurnException exception = assertThrows(InvalidTurnException.class, () -> {
            GameUtil.assertTurn(gameBoard, coordinates);
        });
        String expectedMessage = "Slot already taken; try another value";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInvertTurnValue() {
        Game game = Game.builder()
                .firstPlayer("Rakshit")
                .secondPlayer("Rachit")
                .currentPlayer("Rakshit")
                .firstPlayerBoardValue(BoardValue.O)
                .build();
        GameUtil.invertTurnValue(game);
        Game expectedGame = Game.builder()
                .firstPlayer("Rakshit")
                .secondPlayer("Rachit")
                .currentPlayer("Rachit")
                .firstPlayerBoardValue(BoardValue.O)
                .build();
        assertThat(game).isEqualTo(expectedGame);
    }

    @Test
    public void testUpdateGameStatusDraw() {
        Game.GameBuilder gameBuilder = Game.builder()
                .size(SIZE)
                .firstPlayer("Rakshit")
                .secondPlayer("Rachit")
                .currentPlayer("Rakshit")
                .firstPlayerBoardValue(BoardValue.O);
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("X", Coordinates.builder().x(0).y(0).build());
        gameBoard.set("X", Coordinates.builder().x(0).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(1).y(1).build());
        gameBoard.set("X", Coordinates.builder().x(1).y(2).build());
        gameBoard.set("X", Coordinates.builder().x(2).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(2).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(2).y(2).build());
        Game game = gameBuilder.gameStatus(GameStatus.IN_PROGRESS).build();
        GameUtil.updateGameStatus(gameBoard, game, Coordinates.builder().x(0).y(2).build());
        Game expectedGame = gameBuilder.gameStatus(GameStatus.OVER).build();
        assertThat(game).isEqualTo(expectedGame);
    }

    @Test
    public void testUpdateGameStatusWinner() {
        Game.GameBuilder gameBuilder = Game.builder()
                .size(SIZE)
                .firstPlayer("Rakshit")
                .secondPlayer("Rachit")
                .currentPlayer("Rakshit")
                .firstPlayerBoardValue(BoardValue.O);
        GameBoard gameBoard = new GameBoard(SIZE);
        gameBoard.set("O", Coordinates.builder().x(0).y(0).build());
        gameBoard.set("O", Coordinates.builder().x(0).y(1).build());
        gameBoard.set("O", Coordinates.builder().x(0).y(2).build());
        Game game = gameBuilder.gameStatus(GameStatus.IN_PROGRESS).build();
        Coordinates coordinates = Coordinates.builder().x(0).y(2).build();
        GameUtil.updateGameStatus(gameBoard, game, coordinates);
        Game expectedGame = gameBuilder.gameStatus(GameStatus.OVER).winnerPlayer("Rakshit").build();
        assertThat(game).isEqualTo(expectedGame);
    }

}