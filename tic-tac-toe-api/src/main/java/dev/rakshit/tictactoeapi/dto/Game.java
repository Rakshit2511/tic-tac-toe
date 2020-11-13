package dev.rakshit.tictactoeapi.dto;

import dev.rakshit.tictactoeapi.models.GameBoard;
import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import dev.rakshit.tictactoeapi.models.enums.GameStatus;
import dev.rakshit.tictactoeapi.models.enums.GameType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about the game")
public class Game {
    @ApiModelProperty(notes = "Game id for a game")
    private Long gameId;
    @ApiModelProperty(notes = "First player for a game")
    private String firstPlayer;
    @ApiModelProperty(notes = "Second player for a game")
    private String secondPlayer;
    @ApiModelProperty(notes = "Board value given to first player. Can be O / X")
    private BoardValue firstPlayerBoardValue;
    @ApiModelProperty(notes = "Player who has to make a move")
    private String currentPlayer;
    @ApiModelProperty(notes = "Player who won the game")
    private String winnerPlayer;
    @ApiModelProperty(notes = "Size of matrix for a game")
    private Integer size;
    @ApiModelProperty(notes = "Game status. Can be IN_PROGRESS / OVER")
    private GameStatus gameStatus;
    @ApiModelProperty(notes = "Game type. Can be HUMAN_VS_HUMAN, / HUMAN_VS_COMPUTER")
    private GameType gameType;
    @ApiModelProperty(notes = "board")
    private String[][] board;
    @ApiModelProperty(notes = "Last Updated")
    private LocalDateTime lastUpdated;

    public Game(dev.rakshit.tictactoeapi.models.Game game, GameBoard gameBoard) {
        this.gameId = game.getGameId();
        this.firstPlayer = game.getFirstPlayer();
        this.secondPlayer = game.getSecondPlayer();
        this.firstPlayerBoardValue = game.getFirstPlayerBoardValue();
        this.currentPlayer = game.getCurrentPlayer();
        this.winnerPlayer = game.getWinnerPlayer();
        this.size = game.getSize();
        this.board = gameBoard.getMatrix();
        this.gameStatus = game.getGameStatus();
        this.gameType = game.getGameType();
        this.lastUpdated = game.getLastUpdated();
    }

}
