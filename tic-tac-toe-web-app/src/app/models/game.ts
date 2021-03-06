export interface Game {
    gameId: number;
    firstPlayer: string;
    secondPlayer: string;
    firstPlayerBoardValue: string;
    currentPlayer: string;
    winnerPlayer: string;
    size: number;
    gameStatus: string;
    gameType: string;
    board: string[][];
    lastUpdated: string
}