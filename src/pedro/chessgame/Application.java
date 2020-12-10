package pedro.chessgame;

import chess.ChessMatch;
import chess.Rook;

public class Application {
    public static void main(String[] args) {
        ChessMatch cm = new ChessMatch();
        UI.printBoard(cm.getPieces());
    }
}
