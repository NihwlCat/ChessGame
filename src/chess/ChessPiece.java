package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
    private Color color;
    private int contagemMov;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    public Color getColor(){
        return color;
    }

    public void plusMove (){
        contagemMov++;
    }
    public void minusMove(){
        contagemMov--;
    }

    public int getContagemMov(){
        return contagemMov;
    }

    protected boolean haUmaPecaInimiga(Position p){
        ChessPiece cp = (ChessPiece) getBoard().piece(p); //Downcast para indicar que a peça referente é a peça do tipo ChessPiece
        return cp != null && cp.getColor() != color;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(pos); // Atenção!
    }

}
