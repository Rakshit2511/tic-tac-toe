import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params, UrlSegment } from '@angular/router';
import { Subscription } from 'rxjs';
import { StartGame } from 'src/app/models/startgame';
import { GameBackendService } from 'src/app/service/game-backend.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  startGameForm: FormGroup;
  pageType: string;
  urlSubscription: Subscription;

  constructor(private gameBackendService: GameBackendService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.pageType = this.route.snapshot.params["pageType"];
    this.urlSubscription = this.route.url.subscribe((url: UrlSegment[]) => {
      this.pageType = url[0].path;
    });
    this.setForm();
  }

  onSubmit(): void {
    const startGame: StartGame = new StartGame(this.startGameForm.value.firstPlayer, this.startGameForm.value.secondPlayer,
      this.startGameForm.value.firstPlayerBoardValue, this.startGameForm.value.size, this.startGameForm.value.gameType);
    this.gameBackendService.startGame(startGame).subscribe(game => {
      this.router.navigate(['', 'game', game.gameId]);
    })
  }

  play(): void {
    this.router.navigate(['', 'play']);
  }

  setForm(): void {
    this.startGameForm = new FormGroup({
      'firstPlayer': new FormControl(null, Validators.required),
      'secondPlayer': new FormControl("Computer", Validators.required),
      'firstPlayerBoardValue': new FormControl("O", Validators.required),
      'size': new FormControl(null, Validators.required),
      'gameType': new FormControl("HUMAN_VS_COMPUTER", Validators.required)
    });
    this.startGameForm.get('gameType').valueChanges.subscribe(val => {
      this.startGameForm.patchValue({
        secondPlayer: 'HUMAN_VS_COMPUTER' === val ? 'Computer' : null
      });
    });
  }

  ngOnDestroy() {
    this.urlSubscription.unsubscribe();
  }

}
