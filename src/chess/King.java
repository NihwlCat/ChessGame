package chess;

import boardgame.Board;

public class King extends ChessPiece{
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "K";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        return mat;
    }
}
