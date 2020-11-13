import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../models/game';
import { Move } from '../models/move';
import { StartGame } from '../models/startgame';
import { Coordinate } from 'src/app/models/coordinate';

@Injectable({
  providedIn: 'root'
})
export class GameBackendService {

  private baseUrl = 'http://localhost:8080/tic-tac-toe/api/v1/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startGame(startGame: StartGame): Observable<Game> {
    return this.http.post<Game>(this.baseUrl + "start", startGame, this.httpOptions);
  }

  playTurn(gameId: number, coordinate: Coordinate): Observable<Game> {
    return this.http.post<Game>(this.baseUrl + gameId + "/play", coordinate, this.httpOptions);
  }

  getMoves(gameId: number): Observable<Move> {
    return this.http.get<Move>(this.baseUrl + gameId + "/moves");
  }

  getGame(gameId: number): Observable<Game> {
    return this.http.get<Game>(this.baseUrl + gameId);
  }

}
