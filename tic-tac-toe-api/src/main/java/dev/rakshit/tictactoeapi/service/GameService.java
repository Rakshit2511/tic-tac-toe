package dev.rakshit.tictactoeapi.service;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.dto.Game;
import dev.rakshit.tictactoeapi.dto.Move;
import dev.rakshit.tictactoeapi.dto.StartGame;
import dev.rakshit.tictactoeapi.exceptions.NotFoundException;
import dev.rakshit.tictactoeapi.models.GameBoard;
import dev.rakshit.tictactoeapi.models.enums.GameStatus;
import dev.rakshit.tictactoeapi.repositories.GameRepository;
import dev.rakshit.tictactoeapi.repositories.MoveRepository;
import dev.rakshit.tictactoeapi.utils.GameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static dev.rakshit.tictactoeapi.utils.GameUtil.*;

@Service
public class GameService {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private GameRepository gameRepository;

    public Game startGame(StartGame startGame) {
        dev.rakshit.tictactoeapi.models.Game game = dev.rakshit.tictactoeapi.models.Game.builder()
                .firstPlayer(startGame.getFirstPlayer())
                .secondPlayer(startGame.getSecondPlayer())
                .firstPlayerBoardValue(startGame.getFirstPlayerBoardValue())
                .currentPlayer(startGame.getFirstPlayer())
                .gameStatus(GameStatus.IN_PROGRESS)
                .size(startGame.getSize())
                .lastUpdated(LocalDateTime.now())
                .build();
        return new Game(game, getGameBoard(gameRepository.save(game)));
    }

    public Game playTurn(String gameId, Coordinates coordinates) {
        dev.rakshit.tictactoeapi.models.Game game = getGameByGameId(gameId);
        GameBoard gameBoard = getGameBoard(game);
        assertTurn(gameBoard, coordinates);
        addMoveToBoard(gameBoard, getMove(game, coordinates));
        updateGameStatus(gameBoard, game, coordinates);
        invertTurnValue(game);
        game.setLastUpdated(LocalDateTime.now());
        return new Game(gameRepository.save(game), gameBoard);
    }

    public Game getGame(String gameId) {
        dev.rakshit.tictactoeapi.models.Game game = getGameByGameId(gameId);
        return new Game(game, getGameBoard(game));
    }

    public List<Move> getMovesByGameId(String gameId) {
        return moveRepository.findAllByGame(getGameByGameId(gameId))
                .stream()
                .map(Move::new)
                .collect(Collectors.toList());
    }

    private GameBoard getGameBoard(dev.rakshit.tictactoeapi.models.Game game) {
        GameBoard gameBoard = new GameBoard(game.getSize());
        moveRepository.findAllByGame(game).forEach(move -> GameUtil.addMoveToBoard(gameBoard, move));
        return gameBoard;
    }

    private void addMoveToBoard(GameBoard gameBoard, dev.rakshit.tictactoeapi.models.Move move) {
        GameUtil.addMoveToBoard(gameBoard, moveRepository.save(move));
    }

    private dev.rakshit.tictactoeapi.models.Game getGameByGameId(String gameId) {
        return gameRepository.findById(Long.parseLong(gameId))
                .orElseThrow(() -> new NotFoundException("GAME NOT FOUND"));
    }

}