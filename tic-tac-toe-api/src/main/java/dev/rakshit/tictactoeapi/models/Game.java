package dev.rakshit.tictactoeapi.models;

import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import dev.rakshit.tictactoeapi.models.enums.GameStatus;
import dev.rakshit.tictactoeapi.models.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "game")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue
    private Long gameId;
    @Column(name = "first_player_name", nullable = false)
    private String firstPlayer;
    @Column(name = "second_player_name", nullable = false)
    private String secondPlayer;
    @Column(name = "first_player_board_value", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BoardValue firstPlayerBoardValue;
    @Column(name = "current_player", nullable = false)
    private String currentPlayer;
    @Column(name = "winner_player_name")
    private String winnerPlayer;
    @Column(name = "size", nullable = false)
    private Integer size;
    @Column(name = "game_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;
    @Column(name = "game_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameType gameType;
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
