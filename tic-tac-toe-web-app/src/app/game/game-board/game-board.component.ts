import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Coordinate } from '../../models/coordinate';

@Component({
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.css']
})
export class GameBoardComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input("board") board: [][];

  @Output() onClickEvent = new EventEmitter<Coordinate>();
  onClick(i: number, j: number) {
    this.onClickEvent.emit(new Coordinate(i, j));
  }

}
