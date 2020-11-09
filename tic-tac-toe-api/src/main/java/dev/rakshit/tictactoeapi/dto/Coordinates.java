package dev.rakshit.tictactoeapi.dto;

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
@ApiModel(description = "All details about a coordinate")
public class Coordinates {
    @NotNull
    @ApiModelProperty(notes = "x-coordinate")
    private int x;
    @NotNull
    @ApiModelProperty(notes = "y-coordinate")
    private int y;
}