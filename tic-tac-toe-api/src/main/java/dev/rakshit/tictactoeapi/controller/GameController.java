package dev.rakshit.tictactoeapi.controller;

import dev.rakshit.tictactoeapi.dto.Coordinates;
import dev.rakshit.tictactoeapi.dto.Game;
import dev.rakshit.tictactoeapi.dto.Move;
import dev.rakshit.tictactoeapi.dto.StartGame;
import dev.rakshit.tictactoeapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public Game startGame(@RequestBody StartGame startGame) {
        return gameService.startGame(startGame);
    }

    @PostMapping("/{game-id}/play")
    public Game playTurn(@PathVariable("game-id") String gameId, @RequestBody Coordinates coordinates) {
        return gameService.playTurn(gameId, coordinates);
    }

    @GetMapping("/{game-id}")
    public Game getGame(@PathVariable("game-id") String gameId) {
        return gameService.getGame(gameId);
    }

    @GetMapping("/{gameId}/moves")
    public List<Move> getMovesByGameId(@PathVariable("game-id") String gameId) {
        return gameService.getMovesByGameId(gameId);
    }

}