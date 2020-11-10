import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { StartGame } from 'src/app/models/startgame';
import { GameBackendService } from 'src/app/service/game-backend.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  startGameForm: FormGroup;

  constructor(private gameBackendService: GameBackendService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.setForm();
  }

  onSubmit(): void {
    const startGame: StartGame = new StartGame(this.startGameForm.value.firstPlayer, this.startGameForm.value.secondPlayer,
      this.startGameForm.value.firstPlayerBoardValue, this.startGameForm.value.size, this.startGameForm.value.gameType);
    console.log(startGame);
    this.gameBackendService.startGame(startGame).subscribe(game => {
      this.router.navigate(['', 'game', game.gameId]);
    })
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

}
