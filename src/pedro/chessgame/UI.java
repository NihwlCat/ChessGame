package pedro.chessgame;

import boardgame.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc){
        try{
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new ChessPosition(coluna,linha);
        } catch(RuntimeException e){
            throw new InputMismatchException("Erro ao inserir posição. Valores válidos estão no range de A1 até H8");
        }


    }

    public static void printBoard(ChessPiece[][] cp, boolean[][] pm ){
        for(int i=0;i<cp.length;i++){
            System.out.print(8-i + " ");
            for(int j=0;j<cp.length;j++){
                printPiece(cp[i][j], pm[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");


    }

    public static void printMatch(ChessMatch cm, List<ChessPiece> cap){
        printBoard(cm.getPieces());
        System.out.println();
        printPecasCapturadas(cap);
        System.out.println();
        System.out.println("Turno: " + cm.getTurn());
        if(!cm.getChequeMate()) {
            System.out.println("Esperando jogada... " + cm.getJogAtual());
            if (cm.getCheque()) {
                System.out.println("CHEQUE!");
            }
        } else {
            System.out.println("CHEQUEMATE!");
            System.out.println("VENCEDOR: " + cm.getJogAtual());
        }
    }


    public static void printBoard(ChessPiece[][] cp){
        for(int i=0;i<cp.length;i++){
            System.out.print(8-i + " ");
            for(int j=0;j<cp.length;j++){
                printPiece(cp[i][j],false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    // BoilerPlate para cores no console

    public static void printPiece(ChessPiece cp, boolean background){
        if(background){
            System.out.print(ANSI_CYAN_BACKGROUND);
        }
        if (cp == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (cp.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + cp + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + cp + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printPecasCapturadas(List<ChessPiece> capturadas){
        // Predicato na função lambda para filtrar elementos a serem inseridos em brancas.
        List<ChessPiece> brancas = capturadas.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> pretas = capturadas.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("CAPTURADAS: ");
        System.out.print("BRANCAS: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString((brancas.toArray())));
        System.out.print(ANSI_RESET);

        System.out.print("PRETAS: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString((pretas.toArray())));
        System.out.print(ANSI_RESET);
    }


        /*if (cp == null){
            System.out.print("-");
        } else {
            System.out.print(cp);
        }
        System.out.print(" ");
    }*/
}
