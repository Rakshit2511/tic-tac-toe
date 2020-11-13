import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GameComponent } from './game/game.component';
import { MainComponent } from './main/main.component';

const routes: Routes = [
  { path: 'game/:gameId', component: GameComponent },
  { path: 'play', component: MainComponent },
  { path: 'thankyou', component: MainComponent },
  { path: '**', redirectTo: 'play' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
