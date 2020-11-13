package dev.rakshit.tictactoeapi.utils;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.exceptions.InvalidTurnException;
import dev.rakshit.tictactoeapi.models.Game;
import dev.rakshit.tictactoeapi.models.GameBoard;
import dev.rakshit.tictactoeapi.models.Move;
import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import dev.rakshit.tictactoeapi.models.enums.GameStatus;
import dev.rakshit.tictactoeapi.models.enums.GameType;

import java.time.LocalDateTime;
import java.util.Optional;

public class GameUtil {

    /**
     * Private constructor
     */
    private GameUtil() {
    }

    /**
     * Validates if coordinates are valid or not.
     *
     * @param gameBoard   GameBoard in which the coordinates are to be inserted.
     * @param coordinates Coordinates to be validated.
     */
    public static void assertTurn(GameBoard gameBoard, Coordinates coordinates) {
        int size = gameBoard.getSize();
        int x = coordinates.getX();
        if (!(x >= 0 && x < size)) {
            throw new InvalidTurnException(String.format("Invalid x-coordinate; Please enter a valid integer between 0 and %d", size));
        }
        int y = coordinates.getY();
        if (!(y >= 0 && y < size)) {
            throw new InvalidTurnException(String.format("Invalid y-coordinate; Please enter a valid integer between 0 and %d", size));
        }
        if (!gameBoard.isValueNull(coordinates)) {
            throw new InvalidTurnException("Slot already taken; try another value");
        }
    }

    /**
     * Gets Board value for the current player.
     *
     * @param game game for which the board value is to be taken.
     * @return board value for the current player.
     */
    private static BoardValue getTurnBoardValue(Game game) {
        return (game.getFirstPlayer().equals(game.getCurrentPlayer()) ? game.getFirstPlayerBoardValue() :
                BoardValue.O == game.getFirstPlayerBoardValue() ? BoardValue.X : BoardValue.O);
    }

    /**
     * Gets move for a game using coordinates.
     *
     * @param game        game for which move is to be created.
     * @param coordinates coordinates of the move.
     * @return Move for a game using coordinates.
     */
    public static dev.rakshit.tictactoeapi.models.Move getMove(Game game, Coordinates coordinates) {
        return Move.builder()
                .game(game)
                .boardRow(coordinates.getX())
                .boardColumn(coordinates.getY())
                .boardValue(getTurnBoardValue(game))
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * Adds move to board.
     *
     * @param gameBoard Game board on which the move is to be added.
     * @param move      Move to be added on game board.
     */
    public static void addMoveToBoard(GameBoard gameBoard, Move move) {
        gameBoard.set(move.getBoardValue().name(), Coordinates.builder().x(move.getBoardRow()).y(move.getBoardColumn()).build());
    }

    /**
     * Inverts current player of a game.
     *
     * @param game game for which current player is to be changed.
     */
    public static void invertTurnValue(Game game) {
        game.setCurrentPlayer(game.getFirstPlayer().equals(game.getCurrentPlayer()) ? game.getSecondPlayer() : game.getFirstPlayer());
    }

    /**
     * Updates game status and winner.
     *
     * @param gameBoard   game board of the game.
     * @param game        game.
     * @param coordinates coordinates of the move.
     */
    public static void updateGameStatus(GameBoard gameBoard, Game game, Coordinates coordinates) {
        if (gameBoard.isWin(coordinates)) {
            game.setWinnerPlayer(game.getCurrentPlayer());
            game.setGameStatus(GameStatus.OVER);
        }
        if (gameBoard.isFull()) {
            game.setGameStatus(GameStatus.OVER);
        }
    }

    /**
     * Gets coordinates player should chose to win the game.
     *
     * @param gameBoard game board of the game.
     * @param game      game.
     * @return Optional of coordinates player should chose to win the game and Optional.empty()
     * if no such coordinates exists.
     */
    public static Optional<Coordinates> getRecommendedCoordinates(GameBoard gameBoard, Game game) {
        return gameBoard.getRecommendedCoordinates(getTurnBoardValue(game));
    }

    /**
     * Tells it it's computer's turn or not.
     *
     * @param game game.
     * @return true if it's computer's turn false otherwise
     */
    public static boolean isComputersTurn(Game game) {
        return GameType.HUMAN_VS_COMPUTER == game.getGameType() &&
                !game.getFirstPlayer().equals(game.getCurrentPlayer());
    }
}