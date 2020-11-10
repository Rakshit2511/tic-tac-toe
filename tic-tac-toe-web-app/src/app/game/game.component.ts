import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs';
import { Coordinate } from '../models/coordinate';
import { Game } from '../models/game';
import { StartGame } from '../models/startgame';
import { GameBackendService } from '../service/game-backend.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {

  constructor(private gameBackendService: GameBackendService, private route: ActivatedRoute) { }

  message: string = "";
  paramsSubscription: Subscription;
  gameSubscription: Subscription;
  gameId: number;
  game: Game;

  ngOnInit(): void {
    this.gameId = this.route.snapshot.params["gameId"];
    this.paramsSubscription = this.route.params
      .subscribe((params: Params) => {
        this.gameId = params["gameId"];
      });
    this.gameBackendService.getGame(this.gameId).subscribe(game => {
      console.log(this.gameId);
      console.log(game);
      this.game = game;
    })

    // let startGame: StartGame = {
    //   firstPlayer: "Rakshit",
    //   secondPlayer: "Rachit",
    //   firstPlayerBoardValue: "O",
    //   size: 3
    // }
    // this.gameBackendService.startGame(startGame).subscribe(game => {
    //   this.game = game;
    //   console.log(game);
    // })
  }

  onclick(i: number, j: number) {
    if ("OVER" == this.game.gameStatus) {
      return;
    }
    let coordinate: Coordinate = {
      x: i,
      y: j
    }
    this.gameBackendService.playTurn(this.game.gameId, coordinate).subscribe(game => {
      this.game = game;
      this.message = "OVER" == this.game.gameStatus ? null == this.game.winnerPlayer ? "It's a draw" : game.winnerPlayer + " won!" : "";
    })
  }

  slotError() {
    if ("OVER" == this.game.gameStatus) {
      return;
    }
    this.message = "Slot Not Available!";
  }

  ngOnDestroy() {
    this.paramsSubscription.unsubscribe();
    this.gameSubscription.unsubscribe();
  }

}
