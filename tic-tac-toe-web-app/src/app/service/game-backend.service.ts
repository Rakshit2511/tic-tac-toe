import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../models/game';
import { StartGame } from '../models/startgame';
import { Coordinate } from 'src/app/models/coordinate';

@Injectable({
  providedIn: 'root'
})
export class GameBackendService {

  url = "http://localhost:8080" + "/game/";

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startGame(startGame: StartGame): Observable<Game> {
    return this.http.post<Game>(this.url + "start", startGame, this.httpOptions);
  }

  playTurn(gameId: number, coordinate: Coordinate): Observable<Game> {
    return this.http.post<Game>(this.url + gameId + "/play", coordinate, this.httpOptions);
  }

  getMoves(gameId: number): Observable<any> {
    return this.http.get<any>(this.url + gameId + "/moves");
  }

  getGame(gameId: number): Observable<Game> {
    return this.http.get<Game>(this.url + gameId);
  }

}
