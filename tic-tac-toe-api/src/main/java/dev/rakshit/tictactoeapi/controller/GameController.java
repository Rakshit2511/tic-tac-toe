package dev.rakshit.tictactoeapi.controller;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.dto.Game;
import dev.rakshit.tictactoeapi.dto.Move;
import dev.rakshit.tictactoeapi.dto.StartGame;
import dev.rakshit.tictactoeapi.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tic-tac-toe/api/v1")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public Game startGame(@RequestBody StartGame startGame) {
        return gameService.startGame(startGame);
    }

    @PostMapping("/{game-id}/play")
    public Game playTurn(@PathVariable("game-id") String gameId,
                         @RequestBody(required = false) Coordinates coordinates) {
        log.info("play turn for gameId : {}, xy-coordinates : {}", gameId, coordinates);
        return gameService.playTurn(gameId, coordinates);
    }

    @GetMapping("/")
    public List<Game> getGames() {
        log.info("get all games");
        return gameService.getGames();
    }

    @GetMapping("/{game-id}")
    public Game getGame(@PathVariable("game-id") String gameId) {
        log.info("get game for gameId : {}", gameId);
        return gameService.getGame(gameId);
    }

    @GetMapping("/{gameId}/moves")
    public List<Move> getMovesByGameId(@PathVariable("game-id") String gameId) {
        log.info("get all moves for gameId : {}", gameId);
        return gameService.getMovesByGameId(gameId);
    }

}