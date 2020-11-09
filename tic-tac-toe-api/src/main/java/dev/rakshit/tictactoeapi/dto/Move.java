package dev.rakshit.tictactoeapi.dto;

import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about Move")
public class Move {
    @ApiModelProperty(notes = "Move id")
    private Long id;
    @ApiModelProperty(notes = "Board row chosen by player")
    private int boardRow;
    @ApiModelProperty(notes = "Board column chosen by player")
    private int boardColumn;
    @ApiModelProperty(notes = "Board value for given row, col. Can be O / X")
    private BoardValue boardValue;
    @ApiModelProperty(notes = "Time at which move was made")
    private LocalDateTime created;

    public Move(dev.rakshit.tictactoeapi.models.Move move) {
        this.id = move.getId();
        this.boardRow = move.getBoardRow();
        this.boardColumn = move.getBoardColumn();
        this.boardValue = move.getBoardValue();
        this.created = move.getCreated();
    }

}