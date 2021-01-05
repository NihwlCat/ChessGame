package chess;

import boardgame.ChessException;
import boardgame.Position;

public class ChessPosition {
    private char coluna;
    private int linha;

    public ChessPosition(char coluna, int linha){
        if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new ChessException("Range válido é de A1 até H8!");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    protected Position toPosition(){
        return new Position(8 - linha, coluna - 'a');

    }

    protected static ChessPosition fromPosition(Position p){
        return new ChessPosition((char)('a' - p.getColuna()), 8 - p.getLinha()); // Casting para indicar que a operação de subtração da coluna é para um char.

    }

    @Override
    public String toString(){
        return "" + coluna + linha;
    }

}
