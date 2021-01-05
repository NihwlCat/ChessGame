package pedro.chessgame;

import boardgame.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Rook;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch cm = new ChessMatch();
        while(true){
            try {
                UI.clearScreen();
                UI.printBoard(cm.getPieces());
                System.out.println();
                System.out.print("ORIGEM: ");
                ChessPosition origem = UI.readChessPosition(sc);
                System.out.println();
                System.out.print("DESTINO: ");
                ChessPosition destino = UI.readChessPosition(sc);

                ChessPiece capturada = cm.performarMovimento(origem, destino);
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
    }
}
