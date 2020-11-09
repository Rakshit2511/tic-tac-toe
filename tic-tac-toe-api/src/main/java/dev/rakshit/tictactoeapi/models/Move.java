package dev.rakshit.tictactoeapi.models;

import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "moves")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @Column(name = "board_row", nullable = false)
    private int boardRow;
    @Column(name = "board_column", nullable = false)
    private int boardColumn;
    @Column(name = "board_value")
    @Enumerated(value = EnumType.STRING)
    private BoardValue boardValue;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}