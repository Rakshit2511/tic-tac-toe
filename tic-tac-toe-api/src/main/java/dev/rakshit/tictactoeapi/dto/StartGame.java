package dev.rakshit.tictactoeapi.dto;

import dev.rakshit.tictactoeapi.models.enums.BoardValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about start game")
public class StartGame {
    @NotNull
    @ApiModelProperty(notes = "First player for a game")
    private String firstPlayer;
    @NotNull
    @ApiModelProperty(notes = "Second player for a game")
    private String secondPlayer;
    @NotNull
    @ApiModelProperty(notes = "Board value given to first player. Can be O / X")
    private BoardValue firstPlayerBoardValue;
    @NotNull
    @ApiModelProperty(notes = "Size of matrix for a game")
    private int size;
}