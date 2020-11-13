import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Coordinate } from '../models/coordinate';
import { Game } from '../models/game';
import { GameBackendService } from '../service/game-backend.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {

  constructor(private gameBackendService: GameBackendService, private route: ActivatedRoute, private router: Router) { }

  message: string;
  paramsSubscription: Subscription;
  gameId: number;
  game: Game;

  ngOnInit(): void {
    this.gameId = this.route.snapshot.params["gameId"];
    this.paramsSubscription = this.route.params
      .subscribe((params: Params) => {
        this.gameId = params["gameId"];
      });
    this.gameBackendService.getGame(this.gameId).subscribe(game => {
      this.setGameAndMessage(game);
    })
  }

  onClickEventHandler(coordinate: Coordinate) {
    if ("OVER" == this.game.gameStatus) {
      return;
    }
    this.playTurn(coordinate);
  }

  playTurn(coordinate: Coordinate) {
    this.gameBackendService.playTurn(this.game.gameId, coordinate).subscribe(game => {
      this.setGameAndMessage(game);
      if ("OVER" != this.game.gameStatus && "HUMAN_VS_COMPUTER" == game.gameType && game.firstPlayer != game.currentPlayer) {
        setTimeout(() => {
          this.playTurn(null);
        }, 500);
      }
    })
  }

  setGameAndMessage(game: Game) {
    this.game = game;
    let message = "OVER" == this.game.gameStatus ? null == this.game.winnerPlayer ? "It's a draw!" : game.winnerPlayer + " won!" : null;
    if (null != message) {
      setTimeout(() => {
        this.message = message;
      }, 500);
    }
  }

  play() {
    this.router.navigate(['', 'play']);
  }

  thankyou() {
    this.router.navigate(['', 'thankyou']);
  }

  ngOnDestroy() {
    this.paramsSubscription.unsubscribe();
  }

}
